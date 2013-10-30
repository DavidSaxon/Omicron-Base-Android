/**********************************\
| For fading in and out of scenes. |
|								   |
| @author David Saxon			   |
\**********************************/

package nz.co.withfire.omicronengine.entities.gui;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class Fader extends Entity {

	//ENUMERATORS
	//the fade directions
	public enum FadeDirection {
		
		FADE_IN,
		FADE_OUT
	};
	
	//VARIABLES
	//the mesh
	private final Mesh faderMesh;
	
	//the colour
	private Vector4 colour;
	
	//Fading
	//the fade direction
	private final FadeDirection fadeDirection;
	//the fade speed
	private final float fadeSpeed;
	//the current fade level
	private float fade;
	
	//CONSTRUCTOR
	/**Creates a new fader
	@param fadeDirection whether we are fading in or out
	@param fadeSpeed the speed of the fade
	@param colour the colour of the fader*/
	public Fader(FadeDirection fadeDirection, float fadeSpeed,
		Vector4 colour) {
		
		//initialise variables
		this.colour = colour;
		this.fadeDirection = fadeDirection;
		this.fadeSpeed = fadeSpeed;
		
		switch(fadeDirection) {
			
			case FADE_IN: {
				
				fade = 1.0f;
				break;
			}
			case FADE_OUT: {
				
				fade = 0.0f;
				break;
			}
		}
		
		//get the mesh
		this.faderMesh = (Mesh) ResourceManager.getRenderable("gui_fader");
		//set the fade of the material
		colour.setW(fade);
		faderMesh.getMaterial().setColour(colour);
		//scale
		faderMesh.setScale(new Vector3(3.0f, 3.0f, 3.0f));
		//add to the renderer
		OmicronRenderer.add(faderMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void update() {
		
		//fade change amount
		float fadeAmount = fadeSpeed * FPSManager.getTimeScale();
		
		switch(fadeDirection) {
			
			case FADE_IN: {
				
				fade -= fadeAmount;
				break;
			}
			case FADE_OUT: {
				
				fade += fadeAmount;
				break;
			}
		}
		
		//set the fade of the material
		colour.setW(fade);
		faderMesh.getMaterial().setColour(colour);
	}
	
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(faderMesh);
	}
	
	@Override
	public boolean shouldRemove() {
		
		switch(fadeDirection) {
			
			case FADE_IN: {
				
				return fade <= 0.0f;
			}
			case FADE_OUT: {
				
				return fade >= 1.0f;
			}
		}
		
		return false;
	}
}
