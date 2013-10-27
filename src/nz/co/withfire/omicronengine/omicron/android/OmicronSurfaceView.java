/********************************************************************\
| This surface needs to be set as the content view to begin Omicron. |
|																     |
| @author David Saxon												 |
\********************************************************************/

package nz.co.withfire.omicronengine.omicron.android;

import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.engine.Engine;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

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
		
        //create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);
		
        //create a new renderer
		renderer = new OmicronRenderer(context, engine);
        
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
}
