/***************************\
| The renderer for Omicron. |
|							|
| @author David Saxon       |
\***************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.logic.engine.Engine;
import nz.co.withfire.omicronengine.omicron.resources.loaders.MeshLoader;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class OmicronRenderer implements GLSurfaceView.Renderer{

	//VARIABLES
	//the android context
	private final Context context;
	
	//the game engine
	private final Engine engine;
	
	//the render list
	private static RenderList renderList = new RenderList();
	
	//the camera
	private static Camera camera = new PerspectiveCamera(
		60.0f, 0.1f, 500.0f);
	
	//the list of current unprocessed touch events
	private  List<MotionEvent> touchEvents =
        new CopyOnWriteArrayList<MotionEvent>();
	
	//TESTING
	Mesh testCube;
	
    //Matrix
    //the projection matrix
    private static final float[] projectionMatrix = new float[16];
    //the view matrix
    private static final float[] viewMatrix = new float[16];
	
	//CONSTRUCTOR
	/**Creates a new renderer
	@param context the android context
	@param engine the game engine*/
	public OmicronRenderer(final Context context, final Engine engine) {
		
		//initialise variables
		this.context = context;
		this.engine = engine;
	}
	
    //PUBLIC METHODS
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	
        //initialise openGL
        initGL();
        
        //TESTING load mesh
        renderList.add(MeshLoader.loadOBJ(context, R.raw.mesh_materialdemo_cube,
    		Renderable.Type.STD, 0));
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	
    	//set the dimensions of the camera
    	Camera.setDimensions(new Vector2(width, height));
    	
    	//set up the camera
    	camera.setProjection(projectionMatrix);
    	camera.setView(viewMatrix);
    	
        //set up the transformation utilities
        TransformationsUtil.init(new Vector2(width, height),
            viewMatrix, projectionMatrix);
    	
    	//initialise the engine
    	engine.init();
    }
    
    @Override
    public void onDrawFrame(GL10 arg0) {
    	
    	processTouch();
    	
    	//redraw background colour
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT |
			GLES20.GL_COLOR_BUFFER_BIT);
        
        //execute the engine
        engine.execute();
    	
        //apply the camera transformations
        camera.applyTransformations(viewMatrix);
        
        //render the standard elements of the scene
        renderList.renderStd(viewMatrix, projectionMatrix);

        //reset the camera
        camera.setProjection(projectionMatrix);
        camera.setView(viewMatrix);
        
        //render the gui elements of the scene
        renderList.renderGUI(viewMatrix, projectionMatrix);
        
        //reset the camera
        camera.setProjection(projectionMatrix);
        camera.setView(viewMatrix);
        
    	GLES20.glFinish();
    }
    
    /**Adds a renderable to the render's render lists
    @param renderable the renderable to add*/
    public static void add(Renderable renderable) {
    	
    	renderList.add(renderable);
    }
    
    /**@param event touch event to input*/
    public void touchEvent(MotionEvent event) {
    	
    	//add to the list of touch events
    	touchEvents.add(event);
    }
    
    /**@return the current camera of the renderer*/
    public static Camera getCamera() {
    	
    	return camera;
    }
    
    /**@param camera the new camera of the renderer*/
    public static void setCamera(Camera camera) {
    	
    	OmicronRenderer.camera = camera;
    }
    
    //PRIVATE METHODS
    /**Process the touch events and passes them to the renderer*/
    private void processTouch() {
    	
        for (MotionEvent e : touchEvents) {
            
            for (int index = 0; index < e.getPointerCount(); ++index) {
                
                //get the touch point
                Vector2 touchPos = new Vector2(e.getX(index),
                    e.getY(index));
                
                //convert to OpenGL co-ordinates
                touchPos.copy(TransformationsUtil.screenPosToOpenGLPos(
                    touchPos, viewMatrix, projectionMatrix));
                
                engine.touchEvent(e.getAction(), index, touchPos);
            }
        }
    }
    
    /**Initialise openGL*/
    private void initGL() {
        
        //set the clear colour
        GLES20.glClearColor(0.3f, 0.5f, 0.8f, 1.0f);
        
        //enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);
        
        //enable backface culling
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glFrontFace(GLES20.GL_CCW);
        
        //enable transparency
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc (GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
