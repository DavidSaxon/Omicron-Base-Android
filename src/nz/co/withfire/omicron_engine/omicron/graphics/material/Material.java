/**********************************************\
| A material that is attached to a renderable. |
|                                               |
| @author David Saxon                           |
\**********************************************/

package nz.co.withfire.omicron_engine.omicron.graphics.material;

import nz.co.withfire.omicron_engine.omicron.graphics.material.shader.Shader;
import nz.co.withfire.omicron_engine.omicron.graphics.material.texture.Texture;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;

public class Material {

    //VARIABLES
    //the shader of the material
    private Shader shader;

    //the colour of the material
    private Vector4 colour = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);
    //the texture of the material
    private Texture texture = null;

    //is true if the material should be rendered in wireframe mode
    private boolean wireframe = false;
    //is true if the material is unaffected by lights
    private boolean shadeless = false;

    //CONSTRUCTOR
    /**Creates a new material
    NOTE: no values can be assigned via the constructor*/
    public Material() {
    }

    //PUBLIC METHODS
    /**Creates a copy of this material*/
    public Material clone() {
        
        Material clone = new Material();
        clone.shader = shader;
        clone.colour = colour.clone();
        clone.texture = texture;
        clone.wireframe = wireframe;
        clone.shadeless = shadeless;
        
        return clone;
    }
    
    //GETTERS
    /**@return the shader of the material*/
    public Shader getShader() {

        return shader;
    }

    /**@return the colour of the material*/
    public Vector4 getColour() {

        return colour;
    }

    /**@return the texture of the material*/
    public Texture getTexture() {

        return texture;
    }

    /**@return if the material is rendered as a wireframe*/
    public boolean getWireframe() {

        return wireframe;
    }

    /**@return if the material is un-affected by lights*/
    public boolean getShadeless() {

        return shadeless;
    }

    //SETTERS
    /**@param shader the new shader of the material*/
    public void setShader(Shader shader) {

        this.shader = shader;
    }

    /**@param colour the new colour of the material*/
    public void setColour(Vector4 colour) {

        this.colour = colour;
    }

    /**@param texture the new texture of the material*/
    public void setTexture(Texture texture) {

        this.texture = texture;
    }

    /**Sets the wireframe mode of the material*/
    public void setWireframe(boolean wireframe) {

        this.wireframe = wireframe;
    }

    /**Sets the shadeless mode of the material*/
    public void setShadeless(boolean shadeless) {

        this.shadeless = shadeless;
    }
}
