/**********************************************\
| A collection of useful and important values. |
|                                               |
| @author David Saxon                           |
\**********************************************/

package nz.co.withfire.omicron_engine.omicron.utilities;

import java.util.Random;

public class ValuesUtil {

    //VARIABLES
    //the size of a float in bytes
    public static final int FLOAT_SIZE = 4;

    //the number of milliseconds in a second
    public static final int MS_IN_SEC = 1000;

    //the dpi of the screen
    public static float dpi = 1.0f;
    
    //random number generator
    public static Random rand = new Random();
}
