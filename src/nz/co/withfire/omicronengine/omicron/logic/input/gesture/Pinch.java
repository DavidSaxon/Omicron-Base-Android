/*****************************\
| A two finger touch gesture. |
|                             |
| @author David Saxon         |
\*****************************/

package nz.co.withfire.omicronengine.omicron.logic.input.gesture;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class Pinch implements Gesture {

    //VARIABLES
    //the position of the first point
    private Vector2 pos1 = new Vector2();
    //the position of the second point
    private Vector2 pos2 = new Vector2();
    //the centre position of the two points
    private Vector2 centrePos = new Vector2();
    
    //CONSTRUCTOR
    /**Creates a new pinch gesture
    @param pos1 the position of the first point
    @param pos2 the position of the second point*/
    public Pinch(Vector2 pos1, Vector2 pos2) {
        
        this.pos1.copy(pos1);
        this.pos2.copy(pos2);
        
        //find the centre position
        findCentre();
    }
    
    //PUBLIC METHODS
    /**@return the position of the first point*/
    public Vector2 getPos1() {
        
        return pos1;
    }
    
    /**@return the position of the second point*/
    public Vector2 getPos2() {
        
        return pos2;
    }
    
    /**@return the centre position*/
    public Vector2 getCentrePos() {
        
        return centrePos;
    }
    
    //PRIVATE METHODS
    /**Finds the centre position*/
    private void findCentre() {
        
        //find the greatest x and y positions
        float greatestX = Math.max(pos1.x, pos2.x);
        float greatestY = Math.max(pos1.y, pos2.y);
        
        //set the centre pos
        centrePos.set(greatestX, greatestY);
    }
}