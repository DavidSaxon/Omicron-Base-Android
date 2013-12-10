/***************************\
| Stores an OpenGL texture. |
|                            |
| @author David Saxon        |
\***************************/

package nz.co.withfire.omicronengine.omicron.resources.types;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import nz.co.withfire.omicronengine.omicron.graphics.material.texture.Texture;
import nz.co.withfire.omicronengine.omicron.resources.loaders.TextureLoader;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class TextureResource {

    //VARIABLES
    //the resource id of the texture
    private final int resourceId;

    //the resource group of the texture
    private final ResourceGroup group;

    //the texture
    private Texture texture = null;

    //is true once the texture has been loaded
    private boolean loaded = false;

    //CONSTRUCTOR
    /**Creates a new texture resource
    @param resourceId the resource id of the texture
    @param group the group of the texture*/
    public TextureResource(int resourceId, ResourceGroup group) {

        //initialise variables
        this.resourceId = resourceId;
        this.group = group;
    }

    //PUBLIC METHODS
    /**Loads the texture
    @param context the android context*/
    public void load(final Context context) {

        //check if the texture has already been loaded
        if (loaded) {

            return;
        }

        texture = new Texture(TextureLoader.loadPNG(context, resourceId));

        //successfully loaded
        loaded = true;
    }

    /**Frees the texture from memory*/
    public void destroy() {

        //do nothing if the texture is not loaded
        if (!loaded) {

            return;
        }

        //put the id in an array
        int texArray[] = {texture.getId()};
        //delete the texture from OpenGL
        GLES20.glDeleteTextures(1, texArray, 0);
        //remove the texture
        texture = null;

        //successfully destroyed
        loaded = false;
    }

    /**@return the loaded texture*/
    public Texture getTexture() {

        //check that the texture has been loaded
        if (!loaded) {

            //report error
            Log.v(Values.TAG, "Attempted to use an un-loaded texture");
            throw new RuntimeException(
                "Attempted to use an un-loaded texture");
        }

        return texture;
    }

    /**@return the group the texture is within*/
    public ResourceGroup getGroup() {

        return group;
    }

    /**@return whether texture has been loaded*/
    public boolean isLoaded() {

        return loaded;
    }
}
