/*********************\
| The skybox.         |
|                      |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;

public class Skybox extends Entity {

    //VARIABLES
    //the mesh
    private final Mesh boxMesh;

    //CONSTRUCTOR
    /**Creates a new skybox*/
    public Skybox() {

        //get the mesh
        boxMesh = (Mesh) ResourceManager.getRenderable("skybox");
        //add to the renderer
        OmicronRenderer.add(boxMesh);
    }

    //PUBLIC METHODS
    @Override
    public void cleanUp() {

        OmicronRenderer.remove(boxMesh);
    }
}
