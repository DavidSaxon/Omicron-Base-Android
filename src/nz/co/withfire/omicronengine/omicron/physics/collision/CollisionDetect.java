/********************************************************\
| Methods used for checking if collisions have occurred. |
|                                                        |
| @author David Saxon                                    |
\********************************************************/

package nz.co.withfire.omicronengine.omicron.physics.collision;

import android.util.Log;
import nz.co.withfire.omicronengine.omicron.physics.bounding.Bounding;
import nz.co.withfire.omicronengine.omicron.physics.bounding.BoundingCircle;
import nz.co.withfire.omicronengine.omicron.physics.bounding.BoundingRect;
import nz.co.withfire.omicronengine.override.Values;

public class CollisionDetect {

    //PUBLIC METHODS
    /**Checks if there is a collision between two boundings
    @param a the first bounding
    @param b the second bounding
    @return if there is a collision*/
    public static boolean detect(Bounding a, Bounding b) {
        
        
        
        //if there are two bounding rectangles
        if (a instanceof BoundingRect && b instanceof BoundingRect) {
            
            return dectectRectRect((BoundingRect) a,(BoundingRect) b);
        }
        //two circles
        else if (a instanceof BoundingCircle && b instanceof BoundingCircle) {
            
            return dectectCircleCircle((BoundingCircle) a,(BoundingCircle) b);
        }
        
        return false;
    }
    
    //PRIVATE METHODS
    /**Checks if there is a collision between two rectangles
    @param a the first bounding rect
    @param b the second bounding rect
    @return if there is a collision*/
    private static boolean dectectRectRect(BoundingRect a, BoundingRect b) {
        
        //get the values of the bounding rectangles
        float ahdx = a.getDim().x * 2.0f;
        float ahdy = a.getDim().y * 2.0f;
        float ax1  = a.getPos().x - ahdx;
        float ax2  = a.getPos().x + ahdx;
        float ay1  = a.getPos().y - ahdy;
        float ay2  = a.getPos().y + ahdy;
        float bhdx = b.getDim().x * 2.0f;
        float bhdy = b.getDim().y * 2.0f;
        float bx1  = b.getPos().x - bhdx;
        float bx2  = b.getPos().x + bhdx;
        float by1  = b.getPos().y - bhdy;
        float by2  = b.getPos().y + bhdy;
        
        //calculate the area of intersection
        float area = Math.max(0, Math.min(ax2, bx2) - Math.max(ax1, bx1)) *
            Math.max(0, Math.min(ay2, by2) - Math.max(ay1, by1));
        
        return area > 0.0f;
    }
    
    /**Checks if there is a collision between two circles
    @param a the first bounding circle
    @param b the second bounding circle
    @return if there is a collision*/
    private static boolean dectectCircleCircle(
        BoundingCircle a, BoundingCircle b) {
        
        //get the distance bewteen the positions
        float distance = a.getPos().distance(b.getPos());
        //get the combined radius
        float radius = a.getRadius() + b.getRadius();
        
        if (distance <= radius) {
            
            return true;
        }
        
        return false;
    }
}
