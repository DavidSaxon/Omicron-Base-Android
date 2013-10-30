/******************************************************************\
| A suspicious looking cube that holds the fate of the human race. |
|																   |
| @author David Saxon											   |
\******************************************************************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.lighting.PointLight;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class CubeOfFate extends Entity {

	//VARIABLES
	//the mesh
	private final Mesh cubeMesh;
	
	//the light
	private PointLight light;
	
	//Colour changing
	//the colour change rate
	private final float CHANGE_SPEED = 0.005f;
	//the colour we are increasing 0 = red, 1 = green, 2 = blue
	private int colourInc = 1;
	//current colour value
	private float colourVal = 0.0f;
	
	//CONSTRUCTOR
	/**Creates a new metal cube*/
	public CubeOfFate() {
		
		//get the mesh
		cubeMesh = (Mesh) ResourceManager.getRenderable("cube_of_fate");
		//set the transformations
		cubeMesh.setTranslation(new Vector3(2.5f, 0.0f, 2.5f));
		//add to the renderer
		OmicronRenderer.add(cubeMesh);
		
		//create and add the light
		light = new PointLight(new Vector3(cubeMesh.getMaterial().getColour()),
			0.6f, 1.75f, new Vector3(2.5f, 0.0f, 2.5f));
		OmicronRenderer.add(light);
	}
	
	//PUBLIC METHODS
	@Override
	public void update() {
		
		//change the colour
		colourVal += CHANGE_SPEED * FPSManager.getTimeScale();
		
		//check if we have peaked
		if (colourVal >= 1.0f) {
			
			colourVal -= 1.0f;
			colourInc = (colourInc + 1) % 3;
		}
		
		//set the new colour
		switch (colourInc) {
			
			case 0: {
				
				cubeMesh.getMaterial().setColour(
					new Vector4(colourVal, 0.0f, 1.0f - colourVal, 1.0f));
				break;
			}
			case 1: {
				
				cubeMesh.getMaterial().setColour(
						new Vector4(1.0f - colourVal, colourVal, 0.0f, 1.0f));
				break;
			}
			default: {
				
				cubeMesh.getMaterial().setColour(
						new Vector4(0.0f, 1.0f - colourVal, colourVal, 1.0f));
				break;
			}
		}
		
		light.setColour(new Vector3(cubeMesh.getMaterial().getColour()));
	}
	
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(cubeMesh);
	}
}
