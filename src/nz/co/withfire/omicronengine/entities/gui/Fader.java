/**********************************\
| For fading in and out of scenes. |
|                                   |
| @author David Saxon               |
\**********************************/

package nz.co.withfire.omicronengine.entities.gui;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class Fader extends Entity {

    //ENUMERATORS
    //the fade directions
    public enum FadeDirection {

        FADE_IN,
        FADE_OUT
    };

    //VARIABLES
    //the sprite
    private final Sprite sprite;

    //the colour
    private Vector4 colour;

    //is true to remove at the end
    private final boolean removeAtEnd;

    //Fading
    //the fade direction
    private final FadeDirection fadeDirection;
    //the fade speed
    private final float fadeSpeed;
    //the current fade level
    private float fade;

    //CONSTRUCTOR
    /**Creates a new fader
    @param fadeDirection whether we are fading in or out
    @param fadeSpeed the speed of the fade
    @param colour the colour of the fader
    @param removeAtEnd if the fader should be removed at the end*/
    public Fader(FadeDirection fadeDirection, float fadeSpeed,
        Vector4 colour, boolean removeAtEnd) {

        //initialise variables
        this.colour = colour;
        this.fadeDirection = fadeDirection;
        this.fadeSpeed = fadeSpeed;
        this.removeAtEnd = removeAtEnd;

        switch(fadeDirection) {

            case FADE_IN: {

                fade = 1.0f;
                break;
            }
            case FADE_OUT: {

                fade = 0.0f;
                break;
            }
        }

        //get the sprite
        this.sprite = (Sprite) ResourceManager.getRenderable("gui_fader");
        //set the fade of the material
        colour.w = fade;
        sprite.getMaterial().setColour(colour);
        //shift forward
        sprite.setTranslation(new Vector3(0.0f, 0.0f, 0.02f));
        //scale
        sprite.setScale(new Vector3(3.0f, 3.0f, 3.0f));
        //add to the renderer
        OmicronRenderer.add(sprite);
    }

    //PUBLIC METHODS
    @Override
    public void update() {

        //fade change amount
        float fadeAmount = fadeSpeed * FPSManager.getTimeScale();

        switch(fadeDirection) {

            case FADE_IN: {

                fade -= fadeAmount;
                break;
            }
            case FADE_OUT: {

                fade += fadeAmount;
                break;
            }
        }

        //set the fade of the material
        colour.w = fade;
        sprite.getMaterial().setColour(colour);
    }

    @Override
    public void cleanUp() {

        OmicronRenderer.remove(sprite);
    }

    @Override
    public boolean shouldRemove() {

        if (!removeAtEnd) {

            return false;
        }

        switch(fadeDirection) {

            case FADE_IN: {

                return fade <= 0.0f;
            }
            default: {

                return fade >= 1.0f;
            }
        }
    }

    /**@return when the fader is complete*/
    public boolean complete() {

       switch(fadeDirection) {

            case FADE_IN: {

                return fade <= 0.0f;
            }
            default: {

                return fade >= 1.0f;
            }
        }
    }
}
