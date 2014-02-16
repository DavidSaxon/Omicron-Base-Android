/*********************\
| Stores a material.  |
|                      |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicron_engine.omicron.resources.types;

import java.util.List;

import android.content.Context;
import android.util.Log;

import nz.co.withfire.omicron_engine.omicron.graphics.material.Material;
import nz.co.withfire.omicron_engine.omicron.graphics.material.attribute.MaterialAttribute;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicron_engine.override.Values;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class MaterialResource {

    //VARIABLES
    //the shader label
    private final String shaderLabel;
    //the colour of the material
    private Vector4 colour = null;
    //the texture label
    private String textureLabel = null;
    //the list of attributes of the material
    private final List<MaterialAttribute> attributes;
    //the flags of the material
    private final int flags;

    //the flag types
    public static final int NONE      = 0;
    public static final int WIREFRAME = 2;
    public static final int SHADELESS = 4;

    //the resource group of the material
    private final ResourceGroup group;

    //the material
    private Material material = null;

    //is true once the material has been loaded
    private boolean loaded = false;

    //CONSTRUCTOR
    /**Creates a new material resource
    @param shaderLabel the label of the shader
    @param the colour of the material
    @param texture the texture label
    @param attributes the extra attributes of the material
    @param flags the material flags
    @group the resource group of the material*/
    public MaterialResource(String shaderLabel, Vector4 colour,
        String textureLabel, List<MaterialAttribute> attributes,
        int flags, ResourceGroup group) {

        //initialise variables
        this.shaderLabel = shaderLabel;
        this.colour = colour;
        this.textureLabel = textureLabel;
        this.attributes = attributes;
        this.flags = flags;
        this.group = group;
    }

    //PUBLIC METHODS
    /**Loads the material
    #WARNING: the shaders, texture, and attributes must already be loaded
    @param context the android context*/
    public void load(final Context context) {

        //check if the material has already been loaded
        if (loaded) {

            return;
        }

        //create the material
        material = new Material();
        //set the shader
        material.setShader(ResourceManager.getShader(shaderLabel));
        //set the colour
        if (colour != null) {

            material.setColour(colour);
        }
        //set the texture
        if (textureLabel != null) {

            material.setTexture(ResourceManager.getTexture(textureLabel));
        }
        //set the attributes
        //TODO:
        //set the flags
        if ((flags & WIREFRAME) != 0) {

            material.setWireframe(true);
        }
        if ((flags & SHADELESS) != 0) {

            material.setShadeless(true);
        }

        //successfully loaded
        loaded = true;
    }

    /**Frees the material from memory*/
    public void destroy() {

        //do nothing if the material is not loaded
        if (!loaded) {

            return;
        }

        //remove the material
        material = null;

        //successfully destroyed
        loaded = false;
    }

    /**@return the loaded material*/
    public Material getMaterial() {

        //check that the material has been loaded
        if (!loaded) {

            //report error
            Log.v(Values.TAG, "Attempted to use an un-loaded material");
            throw new RuntimeException(
                "Attempted to use an un-loaded material");
        }

        return material;
    }

    /**@return the group the material is within*/
    public ResourceGroup getGroup() {

        return group;
    }

    /**@return whether material has been loaded*/
    public boolean isLoaded() {

        return loaded;
    }
}
