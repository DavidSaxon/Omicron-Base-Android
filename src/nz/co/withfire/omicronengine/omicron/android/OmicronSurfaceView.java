/********************************************************************\
| This surface needs to be set as the content view to begin Omicron. |
|																     |
| @author David Saxon												 |
\********************************************************************/

package nz.co.withfire.omicronengine.omicron.android;

import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.engine.Engine;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class OmicronSurfaceView extends GLSurfaceView {

	//VARIABLES
	//the renderer
	private OmicronRenderer renderer;
	//the game engine
	private Engine engine = new Engine();
	
	//CONSTRUCTOR
	/**Creates a new Omicron surface view
	@param context the android context*/
	public OmicronSurfaceView(Context context) {
		
		//super call
		super(context);
		
		//initialisation
		ResourceManager.init(context);
		
        //create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);
		
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
	
	/**Cleans up Omicron*/
	public void cleanUp() {
		
		ResourceManager.cleanUp();
		renderer.cleanUp();
	}
}
