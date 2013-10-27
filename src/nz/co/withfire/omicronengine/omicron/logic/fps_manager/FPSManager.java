/******************************************************************\
| Manages the frames per seconds. Tries to find the best fps rate. |
|                                                                  |
| @author David Saxon                                             |
\******************************************************************/

package nz.co.withfire.omicronengine.omicron.logic.fps_manager;

import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import android.os.SystemClock;

public class FPSManager {

    //VARIABLES
    //the minimum frame length
    private int MIN_FRAME_LENGTH = 16;
    //the standard frame length to base time scale of
    private static final int STD_FRAME_LENGTH = 16;
    //the maximum frame length
    private final int MAX_FRAME_LENGTH = 66;
    //the amount of extra updates that can be done
    private final int EXTRA_UPDATES_LIMIT = 4;
    
    //the scale amount
    private static float timeScale = 1.0f;
    //the current fps
    private static float fps = 0.0f;
    
    //the time the last frame started
    private long lastFrameStart;
    //the time accumulated since the last frame
    private int accumTime = 0;
    //the number of milliseconds in a frame
    private int frameLength = 33;
    
    //is true if  should be zeroed
    private boolean zero = false;
    
    //CONSTRUCTOR
    public FPSManager() {
        
        lastFrameStart = SystemClock.uptimeMillis();
        timeScale = (float) (frameLength) / (float) (STD_FRAME_LENGTH);
    }
    
    //PUBLIC METHODS
    /**Updates the fps manager
    @return the number of frames that should be executed*/
    public int update() {
        
        if (zero) {
            
            zero = false;
            accumTime = 0;
            lastFrameStart = SystemClock.uptimeMillis();
        }
        
        //get the current time
        long currentTime = SystemClock.uptimeMillis();
        
        //get the time that has run since the last update
        int frameTime = (int) (currentTime - lastFrameStart);
        lastFrameStart = currentTime;
        accumTime += frameTime;
        
        //limit extra updates
        if (accumTime > frameLength * EXTRA_UPDATES_LIMIT) {
            
            accumTime = frameLength * EXTRA_UPDATES_LIMIT;
        }
        
        //find the amount of frames that should be executed
        int numFrames = accumTime / frameLength;
        accumTime -= numFrames * frameLength;
        
        //try to find a better fps
        int change = numFrames - 1;
        
        frameLength += change;
        
        //make sure to cap the frameLength
        if (frameLength < MIN_FRAME_LENGTH) {
            
            frameLength = MIN_FRAME_LENGTH;
        }
        else if (frameLength > MAX_FRAME_LENGTH) {
            
            frameLength = MAX_FRAME_LENGTH;
        }
        
        timeScale = (float) (frameLength) / (float) (STD_FRAME_LENGTH);
        fps = (float) (ValuesUtil.MS_IN_SEC) / (float) (frameLength);
        
        return numFrames;
    }
    
    /**Sets the start of the next frame*/
    public void frameStart() {
        
        lastFrameStart = SystemClock.uptimeMillis();;
    }
    
    /**Resets the start frame time and zeros the accum time
    NOTE: is not done instantly but performed on the then next update*/
    public void zero() {
        
        zero = true;
    }
    
    /**@return the length of a standard frame*/
    public static int getStdFrameLength() {
    	
    	return STD_FRAME_LENGTH;
    }
    
    /**@return the scale amount*/
    public static float getTimeScale() {
        
        return timeScale;
    }
    
    /**@return the current fps*/
    public static float getFPS() {
    	
    	return fps;
    }
}