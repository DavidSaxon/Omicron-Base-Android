/**********************\
| Stores a renderable. |
|                       |
| @author David Saxon  |
\**********************/

package nz.co.withfire.omicronengine.omicron.resources.types;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.loaders.RenderableLoader;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class RenderableResource {

    //ENUMERATORS
    //the renderbale types
    public enum RenderableType {

        MESH,
        SPRITE,
        ANIMATION
    };

    //VARIABLES
    //the type of renderable this is storing
    private final RenderableType type;
    //the renderable group
    private final Renderable.Group layerGroup;
    //the layer of the renderable
    private final int layer;
    //the material label
    private final String materialLabel;

    //the resource group of the renderable
    private final ResourceGroup group;

    //the resource id
    private int resourceId;
    //the dimensions
    private Vector2 dim;
    //the vertex divisions
    private int divisions;
    
    //the renderable
    private Renderable renderable = null;

    //is true once the renderable has been loaded
    private boolean loaded = false;

    //CONSTRUCTORS
    /**Creates a new mesh renderable resource
    @param resourceId the resource id of the renderable
    @param layerGroup the renderable group
    @param layer the layer of the renderable
    @param materialLabel the label of the material
    @param group the group of the resource*/
    public RenderableResource(int resourceId,
         Renderable.Group layerGroup, int layer,
        String materialLabel, ResourceGroup group) {
        
        //initialise variables
        this.type = RenderableType.MESH;
        this.resourceId = resourceId;
        this.layerGroup = layerGroup;
        this.layer = layer;
        this.materialLabel = materialLabel;
        this.group = group;
    }

    /**Creates a new sprite renderable resource
    @param dim the dimensions of the sprite
    @param divisions the vertex divisions of the sprite
    @param layerGroup the renderable group
    @param layer the layer of the renderable
    @param materialLabel the label of the material
    @param group the group of the resource*/
    public RenderableResource(Vector2 dim,
        int divisions, Renderable.Group layerGroup,
        int layer, String materialLabel, ResourceGroup group) {
        
        //initialise the variables
        this.type = RenderableType.SPRITE;
        this.dim = dim.clone();
        this.divisions = divisions;
        this.layerGroup = layerGroup;
        this.layer = layer;
        this.materialLabel = materialLabel;
        this.group = group;
        
    }
    
    /**Creates a new animation renderable resource
    @param resourceId the resource id of the animation file
    @param dim the dimensions of the sprite
    @param divisions the vertex divisions of the sprite
    @param layerGroup the renderable group
    @param layer the layer of the renderable
    @param materialLabel the label of the material
    @param group the group of the resource*/
    public RenderableResource(int resourceId, Vector2 dim,
        int divisions, Renderable.Group layerGroup,
        int layer, String materialLabel, ResourceGroup group) {
        
        //initialise the variables
        this.type = RenderableType.ANIMATION;
        this.resourceId = resourceId;
        this.dim = dim.clone();
        this.divisions = divisions;
        this.layerGroup = layerGroup;
        this.layer = layer;
        this.materialLabel = materialLabel;
        this.group = group;
    }
    
    //PUBLIC METHODS
    /**Loads the renderable
    #WARNING: the material must be already loaded
    @param context the android context*/
    public void load (final Context context) {

        //check if the renderable has already been loaded
        if (loaded) {

            return;
        }

        //load the correct type
        switch(type) {

            case MESH: {

                renderable = RenderableLoader.loadOBJ(context,
                    resourceId, layerGroup, layer);
                break;
            }
            case SPRITE: {
                
                renderable = RenderableLoader.loadSprite(
                    layerGroup, layer, dim, divisions);
                break;
            }
            case ANIMATION: {
                
                renderable = RenderableLoader.loadAnimation(
                    context, resourceId, layerGroup, layer, dim, divisions);
                break;
            }
        }

        //set the material
        renderable.setMaterial(ResourceManager.getMaterial(materialLabel));

        //successfully loaded
        loaded = true;
    }

    /**Frees the renderable from memory*/
    public void destroy() {

        //do nothing if the renderable is not loaded
        if (!loaded) {

            return;
        }

        //remove the material
        renderable = null;

        //successfully destroyed
        loaded = false;
    }

    /**@return the loaded renderable*/
    public Renderable getRenderable() {

        //check that the renderable has been loaded
        if (!loaded) {

            //report error
            Log.v(Values.TAG, "Attempted to use an un-loaded renderable");
            throw new RuntimeException(
                "Attempted to use an un-loaded renderable");
        }

        return renderable;
    }

    /**@return the group the renderable is within*/
    public ResourceGroup getGroup() {

        return group;
    }

    /**@return whether renderable has been loaded*/
    public boolean isLoaded() {

        return loaded;
    }
}
