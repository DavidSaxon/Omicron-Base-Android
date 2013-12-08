/*****************************************\
| Important values relating to debugging. |
|                                         |
| @author David Saxon                     |
\*****************************************/


package nz.co.withfire.omicronengine.override;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class DebugValues {

    //VARIABLES
    //is true to show bounding boxes
    public static final boolean DEBUG_BOUNDINGS = false;
    
    //is true to disable sounds
    public static final boolean DISABLE_SOUNDS = false;
    //is true to disable music
    public static final boolean DISABLE_MUSIC = false;
    
    //the colour of bounding boxes
    public static final Vector4 boundingColour =
        new Vector4(0.0f, 1.0f, 0.0f, 1.0f);
}
