/****************************************\
| A cog displayed at the loading screen. |
|                                        |
| @author David Saxon                    |
\****************************************/

package nz.co.withfire.omicronengine.entities.loading;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class LoadingCog extends Entity {

    //VARIABLES
    //the mesh
    private final Sprite sprite;
    
    //the rotation
    private Vector3 rot = new Vector3();
    
    //CONSTRUCTOR
    /**Creates a new loading cog*/
    public LoadingCog() {
        
        //get the sprite
        sprite = (Sprite) ResourceManager.getRenderable("loading_cog");
        //scale
        sprite.setScale(new Vector3(0.5f, 0.5f, 1.0f));
        //add to the renderer
        OmicronRenderer.add(sprite);
    }
    
    @Override
    public void update() {
        
        //spin
        rot.z += 2.0f * FPSManager.getTimeScale();
        sprite.setGlobalRot(rot);
    }
    
    @Override
    public void cleanUp() {
        
        OmicronRenderer.remove(sprite);
    }
}
