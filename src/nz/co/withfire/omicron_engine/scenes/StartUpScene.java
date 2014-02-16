/***********************************************************\
| The start up scene which is default to all Omicron games. |
|                                                            |
| @author David Saxon                                        |
\***********************************************************/

package nz.co.withfire.omicron_engine.scenes;

import nz.co.withfire.omicron_engine.entities.start_up.Splash;
import nz.co.withfire.omicron_engine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicron_engine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.logic.scene.Scene;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.override.DebugValues;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class StartUpScene extends Scene {

    //VARIABLES
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.1f, 100.0f);
    
    //the current splash screen
    private Splash splash = null;
    
    //the minimum wait times at splash
    private final int MIN_WAIT_TIME = 20;
    //the time we started waiting
    private long waitStart = 0;

    int stage = 0;
    
    //PUBLIC METHODS
    @Override
    public void init() {

        //set the camera
        camera.setPos(new Vector3(0.0f, 0.0f, 1.7f));
        OmicronRenderer.setCamera(camera);
        
        //load the start up resources
        ResourceManager.load(ResourceGroup.ALL);
        ResourceManager.load(ResourceGroup.GUI);
        ResourceManager.load(ResourceGroup.START_UP);

        //load in debug resources if we need to
        if (DebugValues.inDebugMode()) {

            ResourceManager.load(ResourceGroup.DEBUG);
        }

        //create and add the Omicron splash
        splash = new Splash(
    		(Sprite) ResourceManager.getRenderable("omicron_splash"));
        entities.add(splash);
        waitStart = System.currentTimeMillis();
    }

    @Override
    public boolean execute() {

        //super call
        super.execute();

        if (stage == 0 &&
            System.currentTimeMillis() - waitStart >= MIN_WAIT_TIME) {
            
            stage = 1;
            entities.remove(splash);
            splash = new Splash(
        		(Sprite) ResourceManager.getRenderable("withfire_splash"));
            entities.add(splash);
            waitStart = System.currentTimeMillis();
        }
        else if (stage == 1 &&
            System.currentTimeMillis() - waitStart >= MIN_WAIT_TIME) {
            
            entities.remove(splash);
            return true;
        }

        return false;
    }

    /**@return the next scene*/
    public Scene nextScene() {

        //super call
        super.nextScene();

        //release start up resources
        ResourceManager.destroy(ResourceGroup.START_UP);

        return new StartUpScene();
    }

    @Override
    public boolean backPressed() {

        return true;
    }
}
