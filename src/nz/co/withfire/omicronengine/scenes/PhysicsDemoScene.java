/*************************************\
| Scene that demos Omicron's physics. |
|                                     |
| @author David Saxon                 |
\*************************************/

package nz.co.withfire.omicronengine.scenes;

import android.util.Log;
import android.view.MotionEvent;
import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.physics_demo.FPSCamera;
import nz.co.withfire.omicronengine.entities.physics_demo.WhiteCube;
import nz.co.withfire.omicronengine.entities.physics_demo.WhiteRoom;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.lighting.AmbientLight;
import nz.co.withfire.omicronengine.omicron.graphics.lighting.PointLight;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;
import nz.co.withfire.omicronengine.override.Values;

public class PhysicsDemoScene extends Scene {

    //VARIABLES
    //the next scene
    private Scene nextScene = null;
    
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.01f, 1000.0f);
    //the fps camera controller
    private FPSCamera fpsCam;
    
    //fade out
    private Fader fadeOut = null;
    
    //is true when touch is controlling the camera
    private boolean camRot = false;
    //the centre of the rot touch pad
    private Vector2 rotCentre = new Vector2();
    //the rot touch pos
    private Vector2 rotPos = new Vector2();
    //the rotation index
    int rotIndex = 0;
    
    //the move index
    int moveIndex = 0;
    
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the camera
        camera.setPos(new Vector3(0.0f, 0.0f, 6.0f));
        OmicronRenderer.setCamera(camera);
        
        //set up lighting
        initLighting();
        //create entities
        initEntites();
    }
    
    @Override
    public boolean execute() {
        
        //super call
        super.execute();
        
        //move the camera
        if (camRot) {
            
            float xDis = Math.abs(rotCentre.x - rotPos.x);
            float yDis = Math.abs(rotCentre.y - rotPos.y);
            
            if (xDis > 0.1f || yDis > 0.1f) {
            
                float xRatio = xDis / (xDis + yDis);
                float yRatio = yDis / (xDis + yDis);
                
                if (rotPos.x < rotCentre.x) {
                    
                    xRatio = -xRatio;
                }
                if (rotPos.y < rotCentre.y) {
                    
                    yRatio = -yRatio;
                }
                
                fpsCam.addRot(new Vector3(yRatio * 3.2f, xRatio * 4.0f, 0.0f));
            }
        }
        
        return fadeOut != null && fadeOut.complete();
    }
    
    @Override
    public Scene nextScene() {

        //super call
        super.nextScene();

        ResourceManager.destroy(ResourceGroup.PHYSICS_DEMO);
        ResourceManager.load(ResourceGroup.MAIN_MENU);

        return nextScene;
    }
    
    @Override
    public void touchEvent(int event, int index, Vector2 touchPos) {
        
//        Log.v(Values.TAG, "event: " + event);
//        
//        if (event == 0) {
//            
//            Log.v(Values.TAG, "1 down");
//        }
//        if (event == 261) {
//            
//            Log.v(Values.TAG, "2 down");
//        }
//        if (event == 6) {
//            
//            Log.v(Values.TAG, "1 up");
//        }
//        if (event == 262) {
//            
//            Log.v(Values.TAG, "2 up");
//        }
        
        if (event == MotionEvent.ACTION_DOWN ||
            event == MotionEvent.ACTION_POINTER_DOWN ||
            event == MotionEvent.ACTION_POINTER_1_DOWN ||
            event == MotionEvent.ACTION_POINTER_2_DOWN) {
            
            if (touchPos.x > 0.0f) {
                
                camRot = true;
                rotCentre.copy(touchPos);
                rotPos.copy(touchPos);
            }
            else {
                
                moveIndex = index;
                fpsCam.move(true);
            }
        }
        else if (event == MotionEvent.ACTION_UP ||
            event == MotionEvent.ACTION_POINTER_UP ||
            event == MotionEvent.ACTION_POINTER_1_UP ||
            event == MotionEvent.ACTION_POINTER_2_UP) {
            
            if (touchPos.x > 0.0f) { 
                
                Log.v(Values.TAG, "rot up: " + touchPos.x);
                camRot = false;
            }
            else {
                
                Log.v(Values.TAG, "move up: " + touchPos.x);
                fpsCam.move(false);
            }
        }
        else {
            
            if (touchPos.x > 0.0f) {
                
                //get the distance from the centre
                rotPos = touchPos;
            }
        }
    }
    
    @Override
    public boolean backPressed() {

        if (fadeOut == null) {

            nextScene = new MainMenuScene();
            fadeOut = new Fader(FadeDirection.FADE_OUT, 0.02f,
                new Vector4(0.0f, 0.0f, 0.0f, 1.0f), false);
            entities.add(fadeOut);
        }

        return true;
    }
    
    //PRIVATE METHODS
    /**Sets up the scene's lighting*/
    private void initLighting() {
        
        //set the ambient light
        AmbientLight.set(new Vector3(1.0f, 1.0f, 1.0f), 0.2f);
        //add point lights
        OmicronRenderer.add(new PointLight(
            new Vector3(1.0f, 1.0f, 1.0f), 1.0f, 5.0f,
            new Vector3(0.0f, 0.0f, -4.0f)));
        
        OmicronRenderer.add(new PointLight(
            new Vector3(1.0f, 0.0f, 1.0f), 0.8f, 1.1f,
            new Vector3(0.0f, 1.3f, -3.3f)));
        OmicronRenderer.add(new PointLight(
            new Vector3(0.0f, 0.5f, 1.0f), 0.8f, 1.1f,
            new Vector3(0.0f, -1.3f, 3.3f)));
    }
    
    /**Creates the scene's entities*/
    private void initEntites() {
        
        entities.add(new Fader(FadeDirection.FADE_IN, 0.01f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), true));
        entities.add(new WhiteCube(new Vector3( 3.0f, 0.0f, 0.0f)));
        entities.add(new WhiteCube(new Vector3(-3.0f, 0.0f, 0.0f)));
        entities.add(new WhiteRoom());
        fpsCam = new FPSCamera(camera);
        entities.add(fpsCam);
    }
}