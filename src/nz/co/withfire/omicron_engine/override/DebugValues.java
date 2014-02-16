/*****************************************\
| Important values relating to debugging. |
|                                         |
| @author David Saxon                     |
\*****************************************/


package nz.co.withfire.omicron_engine.override;

public class DebugValues {

    //VARIABLES
    //is true to show bounding boxes
    public static final boolean DEBUG_BOUNDINGS = false;
    
    //is true to disable sounds
    public static final boolean DISABLE_SOUNDS = false;
    //is true to disable music
    public static final boolean DISABLE_MUSIC = false;
    
    //is true if the in game UI should be hidden
    public static final boolean HIDE_UI = false;
    
    //PUBLIC METHODS
    /**@return if at least one debug mode is active*/
    public static boolean inDebugMode() {
        
        return DEBUG_BOUNDINGS;
    }
}
