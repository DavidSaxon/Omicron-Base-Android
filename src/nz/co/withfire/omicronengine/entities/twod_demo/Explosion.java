/*************************************\
| An explosion between two glow fish. |
|                                     |
| @author David Saxon                 |
\*************************************/

package nz.co.withfire.omicronengine.entities.twod_demo;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Animation;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class Explosion extends Entity {

    //VARIABLES
    //the animation
    private Animation animation;
    
    //CONSTRUCTOR
    /**Creates a new explosion
    @param pos the position of the explosion
    @param colour the colour of the explosion*/
    public Explosion(Vector3 pos, Vector4 colour) {
        
        this.pos = pos.clone();
        
        //get the animation
        animation = (Animation) ResourceManager.getRenderable("fish_explosion");
        //set colour
        animation.getMaterial().setColour(colour);
        //set translation
        animation.setTranslation(pos);
        //add to the renderer
        OmicronRenderer.add(animation);
    }
    
    //PUBLIC METHODS
    @Override
    public boolean shouldRemove() {
        
        return animation.finished();
    }
    
    @Override
    public void cleanUp() {
        
        OmicronRenderer.remove(animation);
    }
}