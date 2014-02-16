/******************************\
| The logic engine of Omicron. |
|                               |
| @author David Saxon           |
\******************************/

package nz.co.withfire.omicron_engine.omicron.logic.engine;

import android.hardware.SensorEvent;
import nz.co.withfire.omicron_engine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicron_engine.omicron.logic.scene.Scene;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.sound.MusicManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;

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
        
        //perform a loading cycle
        ResourceManager.loadCycle();
        
        //update the music manager
        MusicManager.update();

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
    @param actionIndex the index of the action
    @param index the index of this point
    @param touchPos the position of the touch event
    @param count the number of touch pointers there*/
    public void touchEvent(int event, int actionIndex,
        int index, Vector2 touchPos, int count) {

        //pass the motion event to the scene
        scene.touchEvent(event, actionIndex, index, touchPos, count);
    }

   /**Is called when the back button is pressed
    @return if this method has override the back button*/
    public boolean backPressed() {

        return scene.backPressed();
    }

    /**Incoming sensor event
    @param event the sensor event*/
    public void onSensorChanged(SensorEvent event) {
        
        //pass on
        if (scene != null) {
        
            scene.onSensorChanged(event);
        }
    }
    
    /**@return if the engine has been initialised*/
    public boolean isInit() {

        return isInit;
    }
}
