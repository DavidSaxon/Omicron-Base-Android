/****************************************************\
| Important values that should be changed as needed. |
|                                                     |
| @author David Saxon                                 |
\****************************************************/

package nz.co.withfire.omicronengine.override;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class Values {

    //VARIABLES
    //the program name (for debug)
        public static final String TAG = "Omicron";

    //the natural dimensions of the screen (in OpenGL space)
    public static final Vector2 NATURAL_SCREEN_SIZE  =
        new Vector2(1.77777777f, 1.0f);

    //the number of possible point lights at one time
    public static final int MAX_POINT_LIGHTS = 16;
}
