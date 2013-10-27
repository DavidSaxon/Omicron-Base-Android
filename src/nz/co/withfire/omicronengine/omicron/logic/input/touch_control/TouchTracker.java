/***************************\
| Used to read touch input. |
|                           |
| @author David Saxon       |
\***************************/

package nz.co.withfire.omicronengine.omicron.logic.input.touch_control;

import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

import android.util.Log;
import android.view.MotionEvent;

public class TouchTracker {
    
    //VARIABLES
    //current points
    private TouchPoint points[] = new TouchPoint[2];
    
    //CONSTRUCTOR
    /**Creates a new gesture reader
    @param gestures the gestures to watch for*/
    public TouchTracker() {
        
    }
    
    //PUBLIC METHODS
    /**Updates the tracker by removing finished and read points*/
    public void update() {
        
        for (int i = 0; i < points.length; ++i) {
            
            if (points[i] != null && points[i].readingDone()) {
                
                points[i] = null;
            }
        }
    }
    
    /**Pass in a touch event
    @param eventType the event type
    @param index the index of the event
    @param pos the position of the event*/
    public void inputEvent(int eventType, int index, Vector2 pos) {
    	
        //we only care about 2 points so ignore others
        if (index < 2) {
            
            //press down
            if (eventType == MotionEvent.ACTION_DOWN ||
                eventType == MotionEvent.ACTION_POINTER_DOWN ||
                eventType == MotionEvent.ACTION_POINTER_1_DOWN ||
                eventType == MotionEvent.ACTION_POINTER_2_DOWN) {
            	
                //create a new touch point
                points[index] = new TouchPoint(pos);
                
                if (index == 0 && points[1] != null) {
                    
                    points[1].finish();
                }
            }
            //move
            if (eventType == MotionEvent.ACTION_MOVE) {
                
                if (points[index] != null) {
                    
                    points[index].setCurrentPos(pos);
                }
                else {
                    
                    points[index] = new TouchPoint(pos);
                }
            } 
            else if (eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP ||
                eventType == MotionEvent.ACTION_POINTER_1_UP ||
                eventType == MotionEvent.ACTION_POINTER_2_UP) {
                
                for (int i = 0; i < 2; ++i) {
                
                    if (points[i] != null && !points[i].finished()) {
                        
                        points[i].finish();
                    }
                }
            }
        }
    }
    
    /**@return the first touch point*/
    public TouchPoint getPoint1() {
        
        return points[0];
    }
    
    /**@return the second touch point*/
    public TouchPoint getPoint2() {
        
        return points[1];
    }
}