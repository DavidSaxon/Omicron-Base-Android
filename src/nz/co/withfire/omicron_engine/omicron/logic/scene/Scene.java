/****************************************************************************\
| A scene is a specific area of the game that can be executed by the engine. |
|                                                                             |
| @author David Saxon                                                         |
\****************************************************************************/

package nz.co.withfire.omicron_engine.omicron.logic.scene;

import android.hardware.SensorEvent;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.logic.entity.EntityList;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;

public abstract class Scene {

    //VARIABLES
    //the entity list
    protected EntityList entities = new EntityList();
    
    //notification from the activity that we have resumed
    private static boolean resumeNotification = false;
    //notification to all extending themes that we have resumed
    protected static boolean resume = false;

    //PUBLIC METHODS
    /**Initialises the scene*/
    public abstract void init();

    /**Executes one frame of the scene
    @return true if the scene has finished*/
    public boolean execute() {

        //update entities
        entities.update();
        
        //check for resume
        if (resume) {
            
            resume = false;
        }
        else if (resumeNotification) {
            
            resumeNotification = false;
            resume = true;
        }

        return false;
    }

    /**@return the next scene*/
    public Scene nextScene() {

        //clean up
        entities.clear();
        OmicronRenderer.getRenderList().clearLights();

        return null;
    }
    
    /**Notifies that the activity has just resumed*/
    public static void notifyResume() {
        
        resumeNotification = true;
    }

    /**Inputs a motion event
    @param event the event type
    @param actionIndex the index of the action
    @param index the index of this point
    @param touchPos the position of the touch event
    @param count the number of touch pointers there*/
    public void touchEvent(int event, int actionIndex,
        int index, Vector2 touchPos, int count) {

        //do nothing
    }

    /**Is called when the back button is pressed
    @return if this method has override the back button*/
    public boolean backPressed() {

        return false;
    }
    
    /**Incoming sensor event
    @param event the sensor event*/
    public void onSensorChanged(SensorEvent event) {
        
        //do nothing
    }
}
