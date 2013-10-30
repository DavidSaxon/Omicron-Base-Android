/*********************\
| A table top.        |
|				      |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;

public class Table extends Entity {
	
	//VARIABLES
	//the mesh
	private final Mesh tableMesh;
	
	//CONSTRUCTOR
	/**Creates a new metal cube*/
	public Table() {
		
		//get the mesh
		tableMesh = (Mesh) ResourceManager.getRenderable("table");
		//add to the renderer
		OmicronRenderer.add(tableMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(tableMesh);
	}
}
