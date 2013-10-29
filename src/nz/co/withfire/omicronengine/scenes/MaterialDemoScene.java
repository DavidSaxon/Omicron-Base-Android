/***************************************\
| Scene that demos Omicron's materials. |
|										|
| @author David Saxon				    |
\***************************************/

package nz.co.withfire.omicronengine.scenes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.util.Log;
import android.view.MotionEvent;
import nz.co.withfire.omicronengine.entities.material_demo.CubeOfFate;
import nz.co.withfire.omicronengine.entities.material_demo.MetalCube;
import nz.co.withfire.omicronengine.entities.material_demo.Skybox;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Gesture;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.GestureWatcher;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Pinch;
import nz.co.withfire.omicronengine.omicron.logic.input.gesture.Swipe;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MaterialDemoScene extends Scene {

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
	private Camera camera = new PerspectiveCamera(60.0f, 0.1f, 750.0f);
	//the position of the camera
	private Vector3 camPos = new Vector3(0.0f, 0.0f, 4.0f);
	//the rotation of the camera
	private Vector3 camRot = new Vector3();
	//the zoom of the camera
	private float camZoom = 1.0f;
	
	//the rotation multiplier
	private final float ROTATION_MULTIPLIER = 600.0f;
	//the zoom multiplier
	private final float ZOOM_MULTIPLIER = 8.0f;
	//the zoom clamps
	private final float ZOOM_CLAMP_LOWER  = 0.15f;
	private final float ZOOM_CLAMP_UPPER = 1.75f;
	
	//CONSTRUCTOR
	/**Creates a new scene to demo materials*/
	public MaterialDemoScene() {
		
		//loading
		ResourceManager.load(ResourceGroup.MATERIAL_DEMO);
		
		//initialise the scene
		initCamera();
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
			
			swipeMove.setX(swipe.getPos().getX() - lastSwipeStore.getX());
			swipeMove.setY(swipe.getPos().getY() - lastSwipeStore.getY());
		}
		
		camRot.add(new Vector3(
			-swipeMove.getY() * ROTATION_MULTIPLIER * FPSManager.getTimeScale(),
			 swipeMove.getX() * ROTATION_MULTIPLIER * FPSManager.getTimeScale(),
			 0.0f));
		
		//update the last swipe
		lastSwipe = swipe.getPos();
	}
	
	/**Zooms the camera based on the pinch gesture
	@param pinch the pinch gesture
	@param pinchLastStore whether there was a pinch last frame*/
	private void zoomCamera(Pinch pinch, boolean pinchLastStore) {
		
		if (pinchLastStore) {
			
			float thisPinchDis = pinch.getPos1().distance(pinch.getPos2());
            float zoom = ((thisPinchDis - pinchDis) * ZOOM_MULTIPLIER) *
        		FPSManager.getTimeScale();
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
		
		camera.setPostTrans(camPos);
		OmicronRenderer.setCamera(camera);
	}
	
	/**Initialises the entities in the scene*/
	private void initEntities() {
		
		entityList.add(new Skybox());
		entityList.add(new CubeOfFate());
		//entityList.add(new MetalCube());
		
	}
}
