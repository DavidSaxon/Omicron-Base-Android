/****************************************************\
| A splash screen at start up that fades in and out. |
|                                                     |
| @author David Saxon                                 |
\****************************************************/

package nz.co.withfire.omicron_engine.entities.start_up;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.logic.entity.Entity;

public class Splash extends Entity {

    //VARIABLES
    //the sprite
    private final Sprite sprite;

    //CONSTRUCTOR
    /**Creates a new splash
    @param sprite the sprite of the splash*/
    public Splash(Sprite sprite) {

        //get the mesh
        this.sprite = sprite;
        //add to the renderer
        OmicronRenderer.add(this.sprite);
    }

    //PUBLIC METHODS
    @Override
    public void cleanUp() {

        OmicronRenderer.remove(sprite);
    }
}
