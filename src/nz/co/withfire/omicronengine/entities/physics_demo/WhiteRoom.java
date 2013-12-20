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

public class WhiteRoom extends Entity {

    //VARIABLES
    //the mesh
    private final Mesh mesh;
    
    //CONSTRUCTOR
    /**Creates a new white room*/
    public WhiteRoom() {
        
        //get the mesh
        mesh = (Mesh) ResourceManager.getRenderable("white_room");
        //add to the renderer
        OmicronRenderer.add(mesh);
    }
    
    //PUBLIC METHODS
    @Override
    public void cleanUp() {

        OmicronRenderer.remove(mesh);
    }
}
