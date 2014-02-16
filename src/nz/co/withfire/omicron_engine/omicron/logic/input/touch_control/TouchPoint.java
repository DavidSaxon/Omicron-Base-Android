/******************************************************\
| Stores data about about a touch point on the screen. |
|                                                      |
| @author David Saxon                                  |
\******************************************************/

package nz.co.withfire.omicron_engine.omicron.logic.input.touch_control;

import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;

public class TouchPoint {

    //VARIABLES
    //the current touch position
    private Vector2 currentPos = new Vector2();
    //the first position of the touch point
    private Vector2 originalPos = new Vector2();
    
    //is true if the touch point has finished
    private boolean finished = false;
    
    //is true if reading has been completed
    private boolean readDone = false;
    
    //CONSTRUCTOR
    /**Creates a new touch point
    @param pos the current position of the touch point*/
    public TouchPoint(Vector2 pos) {
        
        currentPos.copy(pos);
        originalPos.copy(pos);
    }
    
    //PUBLIC METHODS
    /**@param pos the new current position*/
    public void setCurrentPos(Vector2 pos) {
        
        currentPos.copy(pos);
    }
    
    /**Finishes the touch point*/
    public void finish() {
        
        finished = true;
    }
    
    /**Finishes reading*/
    public void readDone() {
        
        readDone = true;
    }
    
    /**@return the current position*/
    public Vector2 getCurrentPos() {
        
        return currentPos;
    }
    
    /**@return the original position*/
    public Vector2 getOriginalPos() {
        
        return originalPos;
    }
    
    /**@return if the touch point has finished*/
    public boolean finished() {
        
        return finished;
    }
    
    /**@return if reading is done*/
    public boolean readingDone() {
        
        return readDone;
    }
}