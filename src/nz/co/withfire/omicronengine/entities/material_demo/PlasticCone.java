/*************************\
| A cone made of plastic. |
|					      |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class PlasticCone extends Entity {

	//VARIABLES
	//the mesh
	private final Mesh coneMesh;
	
	//CONSTRUCTOR
	/**Creates a new metal cube*/
	public PlasticCone() {
		
		//get the mesh
		coneMesh = (Mesh) ResourceManager.getRenderable("plastic_cone");
		//set the transformations
		coneMesh.setTranslation(new Vector3(2.5f, 0.0f, -2.5f));
		//add to the renderer
		OmicronRenderer.add(coneMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(coneMesh);
	}
}
