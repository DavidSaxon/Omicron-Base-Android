/****************************************************\
| A splash screen at start up that fades in and out. |
|													 |
| @author David Saxon								 |
\****************************************************/

package nz.co.withfire.omicronengine.entities.start_up;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class Splash extends Entity {

	//VARIABLES
	//the mesh
	private final Mesh splashMesh;
	
	//Fading
	//the fade speed
	private final float FADE_SPEED = 0.02f;
	//the current fade level
	private float fade = 0.0f;
	
	//Waiting
	//the minimum number of seconds we will wait at the splash
	private final int WAIT_MIN = 1000;
	//the time we started waiting
	private long waitStart = 0;
	
	//the state of the fade 0 = fading in, 1 = waiting, 2 = fading out, 3 = done
	private int state = 0;
	
	//CONSTRUCTOR
	/**Creates a new splash
	@param splashMesh the mesh of the splash*/
	public Splash(Mesh splashMesh) {
		
		//get the mesh
		this.splashMesh = splashMesh;
		//add to the renderer
		OmicronRenderer.add(splashMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void update() {
		
		//fade in
		if (state == 0 && fade < 1.0f) {
			
			fade += FADE_SPEED * FPSManager.getTimeScale();
		}
		else if (state == 0){
			
			//wait
			fade = 1.0f;
			waitStart = System.currentTimeMillis();
			++state;
		}
		
		//wait
		if (state == 1) {
			
			if ((int) (System.currentTimeMillis() - waitStart) > WAIT_MIN) {
				
				++state;
			}
		}
		
		//fade out
		if (state == 2 && fade > 0.0f) {
			
			fade -= FADE_SPEED * FPSManager.getTimeScale();
		}
		else if (state == 2){
			
			//done
			fade = 0.0f;
			++state;
		}
		
		//set the material fade
		splashMesh.getMaterial().setColour(
			new Vector4(1.0f, 1.0f, 1.0f, fade));
	}
	
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(splashMesh);
	}
	
	/**@return if fading in has completed*/
	public boolean fadedIn() {
		
		return state > 0;
	}
	
	/**@return if fading out has completed*/
	public boolean fadedOut() {
		
		return state > 2;
	}
}
