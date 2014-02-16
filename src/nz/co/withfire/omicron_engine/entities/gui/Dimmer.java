package nz.co.withfire.omicron_engine.entities.gui;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.logic.entity.Entity;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;

public class Dimmer extends Entity {

    //VARIABLES
    private Sprite sprite;
    
    //CONSTRUCTOR
    public Dimmer() {
        
        sprite = (Sprite) ResourceManager.getRenderable("gui_dimmer");
        //shift forward
        sprite.setTranslation(new Vector3(0.0f, 0.0f, 0.2f));
        //scale
        sprite.setScale(new Vector3(3.0f, 3.0f, 3.0f));
        OmicronRenderer.add(sprite);
    }
    
    public Dimmer(Vector4 colour) {
        
        sprite = (Sprite) ResourceManager.getRenderable("gui_dimmer");
        sprite.getMaterial().setColour(colour);
        //shift forward
        sprite.setTranslation(new Vector3(0.0f, 0.0f, 0.02f));
        //scale
        sprite.setScale(new Vector3(3.0f, 3.0f, 3.0f));
        OmicronRenderer.add(sprite);
    }
    
    //PUBLIC METHODS
    @Override
    public void cleanUp() {
        
        OmicronRenderer.remove(sprite);
    }
}
