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
import nz.co.withfire.omicronengine.omicron.graphics.material.Material;
import nz.co.withfire.omicronengine.omicron.graphics.material.shader.Shader;
import nz.co.withfire.omicronengine.omicron.graphics.material.texture.Texture;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.logic.engine.Engine;
import nz.co.withfire.omicronengine.omicron.resources.loaders.MeshLoader;
import nz.co.withfire.omicronengine.omicron.resources.loaders.ShaderLoader;
import nz.co.withfire.omicronengine.omicron.resources.loaders.TextureLoader;
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
	private static RenderList renderList = null;
	
	//the camera
	private static Camera camera = null;
	
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
		renderList = new RenderList();
		camera =  new PerspectiveCamera(60.0f, 0.1f, 500.0f);
	}
	
    //PUBLIC METHODS
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	
        //initialise openGL
        initGL();
        
        //TESTING load mesh
        Mesh testCube = MeshLoader.loadOBJ(context, R.raw.mesh_materialdemo_cube,
        		Renderable.Type.STD, 0);
        //the material
        Material material = new Material();
        
        //compile the shaders
		int vertexShader = ShaderLoader.compileShader(context,
			GLES20.GL_VERTEX_SHADER, R.raw.shader_vertex_default);
		int fragmentShader = ShaderLoader.compileShader(context,
			GLES20.GL_FRAGMENT_SHADER, R.raw.shader_fragment_default);
		
		//create the openGL program
        int program = GLES20.glCreateProgram();
        //attach the vertex shader to the program
        GLES20.glAttachShader(program, vertexShader);
        //attach the fragment shader to the program
        GLES20.glAttachShader(program, fragmentShader);
        //create openGL program executables
        GLES20.glLinkProgram(program);
        //create the shader
        material.setShader(new Shader(vertexShader, fragmentShader, program));
        
        //texture
        Texture texture = new Texture(TextureLoader.loadPNG(context,
    		R.drawable.materialdemo_metal));
        material.setTexture(texture);
        
        //colour
        //material.setColour(new Vector4(0.8f, 0.5f, 1.0f, 1.0f)); 
        
        testCube.setMaterial(material);
        renderList.add(testCube);
        
        Mesh skybox = MeshLoader.loadOBJ(context, R.raw.mesh_materialdemo_skybox,
        		Renderable.Type.STD, 0);
        //the material
        Material smaterial = new Material();

        //create the shader
        smaterial.setShader(new Shader(vertexShader, fragmentShader, program));
        
        //texture
        Texture stexture = new Texture(TextureLoader.loadPNG(context,
    		R.drawable.materialdemo_skybox));
        smaterial.setTexture(stexture);
        
        //colour
        //smaterial.setColour(new Vector4(0.8f, 0.5f, 1.0f, 1.0f)); 
        
        skybox.setMaterial(smaterial);
        renderList.add(skybox);
        
        
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
    
    /**Cleans up the renderer*/
    public void cleanUp() {
    	
    	renderList = null;
    	if (camera != null) {
    		
    		camera.cleanUp();
    		camera = null;
    	}
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
