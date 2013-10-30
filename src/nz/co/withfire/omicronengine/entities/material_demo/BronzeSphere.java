/**************************\
| A sphere made of bronze. |
|					       |
| @author David Saxon      |
\**************************/


package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class BronzeSphere extends Entity {

	//VARIABLES
	//the mesh
	private final Mesh sphereMesh;
	
	//CONSTRUCTOR
	/**Creates a new metal cube*/
	public BronzeSphere() {
		
		//get the mesh
		sphereMesh = (Mesh) ResourceManager.getRenderable("bronze_sphere");
		//set the transformations
		sphereMesh.setTranslation(new Vector3(-2.5f, 0.0f, -2.5f));
		//add to the renderer
		OmicronRenderer.add(sphereMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(sphereMesh);
	}
}
