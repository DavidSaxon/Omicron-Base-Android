/****************************************************\
| Important values that should be changed as needed. |
|                                                     |
| @author David Saxon                                 |
\****************************************************/

package nz.co.withfire.omicron_engine.override;

import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;

public class Values {
    
    //VARIABLES
    //the program name (for debug)
    public static final String TAG = "Omicron";

    //the natural dimensions of the screen (in OpenGL space)
    public static final Vector2 NATURAL_SCREEN_SIZE  =
        new Vector2(1.77777777f, 1.0f);

    //the number of possible point lights at one time
    public static final int MAX_POINT_LIGHTS = 1;
    //the number of possible directional lights at one time
    public static final int MAX_DIR_LIGHTS = 1;
    
    //if the engine is paused
    public static boolean paused = false;
    //the time scale
    public static float timeScale = 1.0f;
    
    //music volume
    public static final float MUSIC_VOLUME = 1.0f;
    //sound volume
    public static final float SOUND_VOLUME = 0.83f;
}
