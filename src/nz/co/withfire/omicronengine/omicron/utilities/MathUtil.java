/*********************\
| Math Utilities.     |
|					  |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicronengine.omicron.utilities;

public class MathUtil {

	//VARIABLES
    //radians to degrees constant
    public static float RADIANS_TO_DEGREES = 57.2957795f;
    //degrees to radians constant
    public static float DEGREES_TO_RADIANS = 0.0174532925f;
    
    //PUBLIC METHODS
    /**Clamps the given number between the two thresholds
    @param value the value to clamp
    @param lower the lower threshold
    @param upper the upper threshold
    @return the clamped value*/
    public static float clamp(float value, float lower, float upper) {
    	
    	if (value < lower) {
    		
    		return lower;
    	}
    	if (value > upper) {
    		
    		return upper;
    	}
    	
    	return value;
    }
}
