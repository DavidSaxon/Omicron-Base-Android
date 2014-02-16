/********************************\
| Utilities relating to colours. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicron_engine.omicron.utilities;

import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;

public class ColourUtil {

    /**Gets the average of the two colours
    @param a the first colour
    @param b the second colour
    @return the average of the colours*/
    public static Vector3 average(final Vector3 a, final Vector3 b) {
        
        float red = (a.x / 2.0f) + (b.x / 2.0f);
        float green = (a.y / 2.0f) + (b.y / 2.0f);
        float blue = (a.z / 2.0f) + (b.z / 2.0f);
        
        return new Vector3(red, green, blue);
    }
    
    /**Gets the average of the two colours
    @param a the first colour
    @param b the second colour
    @return the average of the colours*/
    public static Vector4 average(final Vector4 a, final Vector4 b) {
        
        float red = (a.x / 2.0f) + (b.x / 2.0f);
        float green = (a.y / 2.0f) + (b.y / 2.0f);
        float blue = (a.z / 2.0f) + (b.z / 2.0f);
        float alpha = (a.w / 2.0f) + (b.w / 2.0f);
        
        return new Vector4(red, green, blue, alpha);
    }
    
    //TODO: average lists
    
    /**Combines the two given colours
    @param a the first colour
    @param b the second colour
    @return the combination of the colours*/
    public static Vector3 combine(final Vector3 a, final Vector3 b) {
        
        float red = a.x + b.x;
        float blue = a.y + b.y;
        float green = a.z + b.z;
        
        //clamp
        red = MathUtil.clamp(red, 0.0f, 1.0f);
        blue = MathUtil.clamp(blue, 0.0f, 1.0f);
        green = MathUtil.clamp(green, 0.0f, 1.0f);
        
        return new Vector3(red, blue, green);
    }
    
    /**Combines the two given colours
    @param a the first colour
    @param b the second colour
    @return the combination of the colours*/
    public static Vector4 combine(final Vector4 a, final Vector4 b) {
        
        float red = a.x + b.x;
        float blue = a.y + b.y;
        float green = a.z + b.z;
        float alpha = a.w + b.w;
        
        //clamp
        red = MathUtil.clamp(red, 0.0f, 1.0f);
        blue = MathUtil.clamp(blue, 0.0f, 1.0f);
        green = MathUtil.clamp(green, 0.0f, 1.0f);
        alpha = MathUtil.clamp(alpha, 0.0f, 1.0f);
        
        return new Vector4(red, blue, green, alpha);
    }
}