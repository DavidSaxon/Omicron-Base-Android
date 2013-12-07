/******************************\
| The logic engine of Omicron. |
|							   |
| @author David Saxon		   |
\******************************/

package nz.co.withfire.omicronengine.omicron.logic.engine;

import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;

public class Engine {
	
	//VARIABLES
	//the fps manager
	private FPSManager fps = new FPSManager();
	
	//the initial scene
	private Scene initScene;
	//the current scene
	private Scene scene;
	
	//is true once the engine has been initialised
	private boolean isInit = false;
	
	//CONSTRUCTOR
	/**Creates a new engine
	@param initScene the initial scene*/
	public Engine(Scene initScene) {
	    
	    this.initScene = initScene;
	}
	
	//PUBLIC METHODS
	/**Initialises the engine*/
	public void init() {
		
		//set the first scene to start up an init
		scene = initScene;
		scene.init();
		
		//zero the fps manager
		fps.zero();
		
		isInit = true;
	}
	
	/**Executes the engine*/
	public void execute() {
	    
		//update the fps manager
		fps.update();
		
		//Log.v(Values.TAG, "fps: " + fps.getFPS());
		
		//perform a loading cycle
		ResourceManager.loadCycle();
		
		//execute the current scene
		if (scene.execute()) {

			//set the new scene
			scene = scene.nextScene();
			scene.init();
			fps.zero();
		}
	}
	
	/**Inputs a touch event to the engine
	@param event the event type
	@param index the event index
	@touchPos the position of the touch event*/
	public void touchEvent(int event, int index, Vector2 touchPos) {
		
		//pass the motion event to the scene
		scene.touchEvent(event, index, touchPos);
	}
	
   /**Is called when the back button is pressed
    @return if this method has override the back button*/
    public boolean backPressed() {
        
        return scene.backPressed();
    }
	
	/**@return if the engine has been initialised*/
	public boolean isInit() {
		
		return isInit;
	}
}
