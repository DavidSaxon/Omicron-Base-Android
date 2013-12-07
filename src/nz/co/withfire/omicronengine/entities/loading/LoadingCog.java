/****************************************\
| A cog displayed at the loading screen. |
|                                        |
| @author David Saxon                    |
\****************************************/

package nz.co.withfire.omicronengine.entities.loading;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class LoadingCog extends Entity {

    //VARIABLES
    //the mesh
    private final Mesh cogMesh;
    
    //the rotation
    private Vector3 rot = new Vector3();
    
    //CONSTRUCTOR
    /**Creates a new loading cog*/
    public LoadingCog() {
        
        //get the mesh
        cogMesh = (Mesh) ResourceManager.getRenderable("loading_cog");
        //scale
        cogMesh.setScale(new Vector3(0.5f, 0.5f, 1.0f));
        //add to the renderer
        OmicronRenderer.add(cogMesh);
    }
    
    @Override
    public void update() {
        
        //spin
        rot.addZ(2.0f * FPSManager.getTimeScale());
        cogMesh.setGlobalRot(rot);
    }
    
    @Override
    public void cleanUp() {
        
        OmicronRenderer.remove(cogMesh);
    }
}
