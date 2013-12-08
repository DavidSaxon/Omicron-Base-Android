/********************************************************************\
| This surface needs to be set as the content view to begin Omicron. |
|																     |
| @author David Saxon												 |
\********************************************************************/

package nz.co.withfire.omicronengine.omicron.android;

import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.engine.Engine;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.override.Values;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class OmicronSurfaceView extends GLSurfaceView {

	//VARIABLES
	//the renderer
	private OmicronRenderer renderer;
	//the game engine
	private Engine engine;
	
	//CONSTRUCTOR
	/**Creates a new Omicron surface view
	@param context the android context
	@param initScene the initial scene*/
	public OmicronSurfaceView(Context context, Scene initScene) {
		
		//super call
		super(context);
		
		//initialise resources
		ResourceManager.init(context);
		//initialise the music manager
		MusicManager.init(context);
		
        //create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);
		
		//create a new engine
		engine = new Engine(initScene);
		
        //create a new renderer
		renderer = new OmicronRenderer(engine);
        
        //set the configuration chooser
        setEGLConfigChooser(true);
        
        //set the renderer
        setRenderer(renderer);
        
        //set the rendering mode
        setRenderMode(RENDERMODE_CONTINUOUSLY);
	}
	
	//PUBLIC METHODS
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		renderer.touchEvent(e);
		
		return true;
	}
	
    /**Is called when the back button is pressed
    @return if this method has override the back button*/
    public boolean backPressed() {
        
        return engine.backPressed();
    }
	
	/**Cleans up Omicron*/
	public void cleanUp() {
		
	    MusicManager.stop();
		ResourceManager.cleanUp();
		renderer.cleanUp();
	}
}
