/**************************\
| A mysterious white cube. |
|                          |
| @author David Saxon      |
\**************************/

package nz.co.withfire.omicronengine.entities.physics_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class WhiteCube extends Entity {

    //VARIABLES
    //the mesh
    private final Mesh cubeMesh;
    
    //CONSTRUCTOR
    /**Creates a new white cube
    @param pos the position of the cube*/
    public WhiteCube(Vector3 pos) {
        
        this.pos.copy(pos);
        
        //get the mesh
        cubeMesh = (Mesh) ResourceManager.getRenderable("white_cube");
        cubeMesh.setTranslation(pos);
        //add to the renderer
        OmicronRenderer.add(cubeMesh);
    }
    
    //PUBLIC METHODS
    @Override
    public void cleanUp() {

        OmicronRenderer.remove(cubeMesh);
    }
}
