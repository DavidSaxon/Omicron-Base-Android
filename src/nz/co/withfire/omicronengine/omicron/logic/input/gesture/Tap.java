/**************************\
| Represents a tap gesture |
|                          |
| @author David Saxon     |
\**************************/

package nz.co.withfire.omicronengine.omicron.logic.input.gesture;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class Tap implements Gesture {

    //VARIABLES
    //the position of the tap
    private Vector2 pos = new Vector2();
    
    //CONSTRUCTOR
    /**Creates a new tap gesture
    @param pos the position of the tap*/
    public Tap(Vector2 pos) {
        
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    /**@return the position of the tap*/
    public Vector2 getPos() {
        
        return pos;
    }
}