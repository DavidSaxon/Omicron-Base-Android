/***************************************\
| Scene that demos Omicron's materials. |
|                                        |
| @author David Saxon                    |
\***************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.material_demo.BronzeSphere;
import nz.co.withfire.omicronengine.entities.material_demo.ChinaMonkey;
import nz.co.withfire.omicronengine.entities.material_demo.CubeOfFate;
import nz.co.withfire.omicronengine.entities.material_demo.MetalCube;
import nz.co.withfire.omicronengine.entities.material_demo.PlasticCone;
import nz.co.withfire.omicronengine.entities.material_demo.Skybox;
import nz.co.withfire.omicronengine.entities.material_demo.Table;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.lighting.AmbientLight;
import nz.co.withfire.omicronengine.omicron.graphics.lighting.PointLight;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Gesture;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.GestureWatcher;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Pinch;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Swipe;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MaterialDemoScene extends Scene {

    //VARIABLES
    //the next scene
    private Scene nextScene;

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
    private Camera camera = new PerspectiveCamera(60.0f, 0.01f, 1000.0f);
    //the position of the camera
    private Vector3 camPos = new Vector3(0.0f, 0.0f, 2.0f);
    //the rotation of the camera
    private Vector3 camRot = new Vector3(-45.0f, 0.0f, 0.0f);
    //the zoom of the camera
    private float camZoom = 0.25f;

    //the rotation multiplier
    private final float ROTATION_MULTIPLIER = 50.0f;
    //the zoom multiplier
    private final float ZOOM_MULTIPLIER = 1.5f;
    //the zoom clamps
    private final float ZOOM_CLAMP_LOWER  = 0.10f;
    private final float ZOOM_CLAMP_UPPER = 0.5f;

    //fade out
    private Fader fadeOut = null;

    //PUBLIC METHODS
    @Override
    public void init() {

        //initialise the scene
        initCamera();
        initLighting();
        initEntities();
    }

    @Override
    public boolean execute() {

        //super call
        super.execute();

        //process the touch events
        processTouch();

        //update the camera transformations
        camera.setLocalRot(camRot);
        camera.setZoom(camZoom);

        return fadeOut != null && fadeOut.complete();
    }

    @Override
    public Scene nextScene() {

        //super call
        super.nextScene();

        ResourceManager.destroy(ResourceGroup.MATERIAL_DEMO);
        ResourceManager.load(ResourceGroup.MAIN_MENU);

        return nextScene;
    }

    @Override
    public void touchEvent(int event, int index, Vector2 touchPos) {

        //pass to the gesture watcher
        gestureWatcher.inputEvent(event, index, touchPos);
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
            moveCamera((Swipe) gesture, lastSwipeStore);
        }
        else if (gesture instanceof Pinch) {

            //zoom the camera
            zoomCamera((Pinch) gesture, pinchLastStore);
        }
    }

    /**Rotates the camera around the centre point based on the swipe gesture
    @param swipe the swipe gesture
    @param lastSwipeStore the store of the last swipe*/
    private void moveCamera(Swipe swipe, Vector2 lastSwipeStore) {

        //get the distance the swipe has moved
        Vector2 swipeMove = new Vector2();
        if (lastSwipeStore != null) {

            swipeMove.x = swipe.getPos().x - lastSwipeStore.x;
            swipeMove.y = swipe.getPos().y - lastSwipeStore.y;
        }

        camRot.add(new Vector3(
            -swipeMove.y * ROTATION_MULTIPLIER,
             swipeMove.x * ROTATION_MULTIPLIER,
             0.0f));

        //clamp the camera
        if (camRot.x > 90.0f) {

            camRot.y = 90.0f;
        }
        if (camRot.x < -90.0f) {

            camRot.x = -90.0f;
        }

        //update the last swipe
        lastSwipe = swipe.getPos();
    }

    /**Zooms the camera based on the pinch gesture
    @param pinch the pinch gesture
    @param pinchLastStore whether there was a pinch last frame*/
    private void zoomCamera(Pinch pinch, boolean pinchLastStore) {

        if (pinchLastStore) {

            float thisPinchDis = pinch.getPos1().distance(pinch.getPos2());
            float zoom = ((thisPinchDis - pinchDis) * ZOOM_MULTIPLIER);
            pinchDis = thisPinchDis;

            camZoom += zoom;

            //clamp the zoom
            camZoom = MathUtil.clamp(
                camZoom, ZOOM_CLAMP_LOWER, ZOOM_CLAMP_UPPER);
        }
        else {

            //store the pinch details
            pinchLast = true;
            pinchDis = pinch.getPos1().distance(pinch.getPos2());
        }
    }

    /**Initialises the camera in the scene*/
    private void initCamera() {

        camera.setLocalRot(camRot);
        camera.setZoom(camZoom);
        camera.setRotPostTrans(camPos);
        OmicronRenderer.setCamera(camera);
    }

    /**Initialises the scene's lighting*/
    private void initLighting() {

        //set the ambient light
        AmbientLight.set(new Vector3(1.0f, 1.0f, 1.0f), 0.2f);

        //add point lights
        OmicronRenderer.add(new PointLight(
            new Vector3(1.0f, 1.0f, 1.0f), 0.8f, 2.0f,
            new Vector3(-3.0f, 2.0f, -3.0f)));
    }

    /**Initialises the entities in the scene*/
    private void initEntities() {

        entities.add(new Fader(FadeDirection.FADE_IN, 0.01f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), true));
        entities.add(new Skybox());
        entities.add(new Table());
        entities.add(new CubeOfFate());
        entities.add(new MetalCube());
        entities.add(new ChinaMonkey());
        entities.add(new BronzeSphere());
        entities.add(new PlasticCone());
    }
}
