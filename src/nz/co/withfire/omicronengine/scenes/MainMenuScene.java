/*****************************************************\
| The main menu where the demos can be selected from. |
|													  |
| @author David Saxon								  |
\*****************************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.entities.main_menu.Background;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Gesture;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.GestureWatcher;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Pinch;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Swipe;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class MainMenuScene extends Scene {

	//VARIABLES
	//Gestures
	//the gesture watcher
	private GestureWatcher gestureWatcher = new GestureWatcher();
    //the last swipe position
    private Vector2 lastSwipe = null;
    //if there was a pinch last frame
    private boolean pinchLast = false;
    //the pinch distance
    private float pinchDis = 0.0f;
    
    //the camera
  	private Camera camera = new PerspectiveCamera(2.9f, 100.0f);
  	
  	//PUBLIC METHODS
  	@Override
  	public void init() {
  		
  		//set the camera
  		OmicronRenderer.setCamera(camera);
  		//initialise the scene
  		initEntities();
  	}
  	
	@Override
	public boolean execute() {
	
		//super call
		super.execute();
		
		//process touch
		processTouch();
		
		return false;
	}
	
	@Override
	public void touchEvent(int event, int index, Vector2 touchPos) {
		
		//pass to the gesture watcher
		gestureWatcher.inputEvent(event, index, touchPos);
	}
	
	//PRIVATE METHODS
	/**Process the touch events*/
	private void processTouch() {
		
		//get the current gesture from the gesture watcher
		Gesture gesture = gestureWatcher.getGesture();
		
		//swipe store
        Vector2 lastSwipeStore = lastSwipe;
        lastSwipe = null;
        //pinch store
        boolean pinchLastStore = pinchLast;
        pinchLast = false;
        
		//find out what kind of gesture we have
		if (gesture instanceof Swipe) {
			
			//move the camera
			//moveCamera((Swipe) gesture, lastSwipeStore);
		}
        else if (gesture instanceof Pinch) {
            
            //zoom the camera
            //zoomCamera((Pinch) gesture, pinchLastStore);
        }
	}
	
	/**Adds the initial entities to the scene*/
	private void initEntities() {
		
		entities.add(new Background());
	}
}
