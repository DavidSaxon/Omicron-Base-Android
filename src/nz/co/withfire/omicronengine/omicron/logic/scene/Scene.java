/****************************************************************************\
| A scene is a specific area of the game that can be executed by the engine. |
|																		     |
| @author David Saxon														 |
\****************************************************************************/

package nz.co.withfire.omicronengine.omicron.logic.scene;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import android.view.MotionEvent;

public abstract class Scene {

	//VARIABLES
	//TODO: entity list
	
	//PUBLIC METHODS
	/**Initialises the scene*/
	public void init() {
	}
	
	/**Executes one frame of the scene
	@return true if the scene has finished*/
	public boolean execute() {
		
		//update entities and collision data
		//TODO:
		
		return false;
	}
	
	/**@return the next scene*/
	public Scene nextScene() {
		
		//clean up
		//TODO:
		
		return null;
	}
	
	/**Inputs a motion event
	@param event the event type
	@param index the event index
	@touchPos the position of the touch event*/
	public void touchEvent(int event, int index, Vector2 touchPos) {
		
		//do nothing
	}
}
