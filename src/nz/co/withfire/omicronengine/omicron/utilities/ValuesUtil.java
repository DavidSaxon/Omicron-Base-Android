/**********************************************\
| A collection of useful and important values. |
|											   |
| @author David Saxon					       |
\**********************************************/

package nz.co.withfire.omicronengine.omicron.utilities;

import java.util.Random;

public class ValuesUtil {

	//VARIABLES
	//the program name (for debug)
	public static final String TAG = "Omicron";
	
	//the size of a float in bytes
	public static final int FLOAT_SIZE = 4;
	
	//the number of milliseconds in a second
    public static int MS_IN_SEC = 1000;
    
    //random number generator
    public static Random rand = new Random();
}
