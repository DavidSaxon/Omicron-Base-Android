/***********************************\
| Resources for the loading scene.  |
|                                   |
| @author David Saxon               |
\***********************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class LoadingPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //TEXTURES
        //Cog
        ResourceManager.add("loading_cog", new TextureResource(
            R.drawable.loading_cog,
            ResourceGroup.LOADING));
        
        //MATERIALS
        //Cog
        ResourceManager.add("loading_cog", new MaterialResource(
            "default", null, "loading_cog", null,
            MaterialResource.SHADELESS,
            ResourceGroup.LOADING));
        //bar
        ResourceManager.add("loading_bar", new MaterialResource(
            "default", new Vector4(0.8f, 0.8f, 0.8f, 1.0f), null, null,
            MaterialResource.SHADELESS,
            ResourceGroup.LOADING));
        
        //SPRITES
        //Cog
        ResourceManager.add("loading_cog", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1,
            Renderable.Group.STD, 5, "loading_cog",
            ResourceGroup.LOADING));
        //bar
        ResourceManager.add("loading_bar", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1,
            Renderable.Group.STD, 4, "loading_bar",
            ResourceGroup.LOADING));
    }
}
