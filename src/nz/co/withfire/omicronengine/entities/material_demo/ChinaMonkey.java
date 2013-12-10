/******************************\
| A monkey made of bone china. |
|                               |
| @author David Saxon          |
\******************************/

package nz.co.withfire.omicronengine.entities.material_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;

public class ChinaMonkey extends Entity {

    //VARIABLES
    //the mesh
    private final Mesh monekyMesh;

    //CONSTRUCTOR
    /**Creates a new china monkey*/
    public ChinaMonkey() {

        //get the mesh
        monekyMesh = (Mesh) ResourceManager.getRenderable("china_monkey");
        //add to the renderer
        OmicronRenderer.add(monekyMesh);
    }

    //PUBLIC METHODS
    @Override
    public void cleanUp() {

        OmicronRenderer.remove(monekyMesh);
    }
}
