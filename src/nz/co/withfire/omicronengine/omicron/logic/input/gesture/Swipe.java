/*****************************\
| Represents a swipe gesture. |
|                             |
| @author David Saxon        |
\*****************************/

package nz.co.withfire.omicronengine.omicron.logic.input.gesture;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class Swipe implements Gesture {

    //VARIABLES
    //the current position of the swipe
    private Vector2 pos = new Vector2();
    //the original position of the swipe
    private Vector2 originalPos = new Vector2();
    
    //is true if the swipe has been released
    private boolean finished;
    
    //CONSTRUCTOR
    /**Creates a new swipe gesture
    @param pos the current position of the swipe
    @param originalPos where the swipe started
    @param finished if the swipe has finished*/
    public Swipe(Vector2 pos, Vector2 originalPos, boolean finished) {
        
        this.pos.copy(pos);
        this.originalPos.copy(originalPos);
        this.finished = finished;
    }
    
    //PUBLIC METHODS
    /**@return if the current position of the swipe*/
    public Vector2 getPos() {
        
        return pos;
    }
    
    /**@return the starting position of the swipe*/
    public Vector2 getOriginalPos() {
        
        return originalPos;
    }
    
    /**@return if the swipe has finished*/
    public boolean finished() {
        
        return finished;
    }
}