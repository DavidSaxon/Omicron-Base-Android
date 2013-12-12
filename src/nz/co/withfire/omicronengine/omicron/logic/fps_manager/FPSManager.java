/******************************************************************\
| Manages the frames per seconds. Tries to find the best fps rate. |
|                                                                  |
| @author David Saxon                                             |
\******************************************************************/

package nz.co.withfire.omicronengine.omicron.logic.fps_manager;

import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import android.os.SystemClock;

public class FPSManager {

    //VARIABLES
    //the standard number of ms in a frame to base time scale of
    private static final float STD_FRAME_LENGTH = 16.6666666f;
    //the clamps of delta time
    private static final float DELTA_CLAMP_LOWER = 0.1f;
    private static final float DELTA_CLAMP_UPPER = 60.0f;

    //the scale amount
    private static float timeScale = 1.0f;
    //the current fps
    private static float fps = 0.0f;

    //the last time we update
    private long lastUpdateTime;

    //CONSTRUCTOR
    public FPSManager() {

        lastUpdateTime = SystemClock.uptimeMillis();
    }

    //PUBLIC METHODS
    /**Updates the fps manager
    @return the number of frames that should be executed*/
    public void update() {

        //get the current time
        long currentTime = SystemClock.uptimeMillis();;
        //find delta time
        float deltaTime = (float) (currentTime - lastUpdateTime);
        //update the last update time
        lastUpdateTime = currentTime;

        //clamp delta time
        deltaTime = MathUtil.clamp(deltaTime,
            DELTA_CLAMP_LOWER, DELTA_CLAMP_UPPER);

        //get the time scale
        timeScale = deltaTime / STD_FRAME_LENGTH;

        //get the fps
        fps = ValuesUtil.MS_IN_SEC / deltaTime;
    }

    /**Zeros the last update time to now*/
    public void zero() {

        lastUpdateTime = SystemClock.uptimeMillis();
    }

    /**@return the scale amount*/
    public static float getTimeScale() {

        return timeScale;
    }

    /**@return the current fps*/
    public static float getFPS() {

        return fps;
    }
    
    /**@return the standard framerate*/
    public static float getStdFrameRate() {
        
        return ValuesUtil.MS_IN_SEC / STD_FRAME_LENGTH;
    }
}