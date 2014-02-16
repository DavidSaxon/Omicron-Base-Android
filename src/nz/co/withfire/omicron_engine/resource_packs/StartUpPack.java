/***********************************\
| Resources for the start up scene. |
|                                       |
| @author David Saxon                |
\***********************************/

package nz.co.withfire.omicron_engine.resource_packs;

import nz.co.withfire.omicron_engine.R;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource.FaceDirection;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class StartUpPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {

        //TEXTURES
        //Omicron splash
        ResourceManager.add("omicron_splash", new TextureResource(
            R.drawable.startup_omicron_splash,
            ResourceGroup.START_UP));
        //WithFire splash
        ResourceManager.add("withfire_splash", new TextureResource(
            R.drawable.startup_withfire_splash,
            ResourceGroup.START_UP));

        //MATERIALS
        //Omicron splash
        ResourceManager.add("omicron_splash", new MaterialResource(
            "default_shadeless_texture", null, "omicron_splash", null,
            MaterialResource.SHADELESS,
            ResourceGroup.START_UP));
        //WithFire splash
        ResourceManager.add("withfire_splash", new MaterialResource(
            "default_shadeless_texture", null, "withfire_splash", null,
            MaterialResource.SHADELESS,
            ResourceGroup.START_UP));

        //SPRITES
        //Omicron splash
        ResourceManager.add("omicron_splash", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1, FaceDirection.FRONT,
            Renderable.Group.STD, 5, "omicron_splash",
            ResourceGroup.START_UP));
        //WithFire splash
        ResourceManager.add("withfire_splash", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1, FaceDirection.FRONT,
            Renderable.Group.STD, 5, "withfire_splash",
            ResourceGroup.START_UP));
    }
}
