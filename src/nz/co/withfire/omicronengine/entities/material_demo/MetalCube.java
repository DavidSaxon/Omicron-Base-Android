/***********************\
| A Cube made of metal. |
|					    |
| @author David Saxon   |
\***********************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;

public class MetalCube extends Entity {

	//VARIABLES
	//the mesh
	private final Mesh cubeMesh;
	
	//CONSTRUCTOR
	/**Creates a new metal cube*/
	public MetalCube() {
		
		//get the mesh
		cubeMesh = (Mesh) ResourceManager.getRenderable("metal_cube");
		//add to the renderer
		OmicronRenderer.add(cubeMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(cubeMesh);
	}
}
