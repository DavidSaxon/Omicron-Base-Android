/*************************************\
| Resources for the main menu scene.  |
|                                     |
| @author David Saxon                 |
\*************************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MainMenuPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //SHADERS
        //Background
        ResourceManager.add("main_menu_background", new ShaderResource(
            R.raw.shader_vertex_menu_main_background,
            R.raw.shader_fragment_menu_main_background,
            ResourceGroup.MAIN_MENU));
        
        //TEXTURES
        //Background
        ResourceManager.add("main_menu_background", new TextureResource(
            R.drawable.menu_main_background,
            ResourceGroup.MAIN_MENU));
        
        //MATERIALS
        //Background
        ResourceManager.add("main_menu_background", new MaterialResource(
            "main_menu_background", null, "main_menu_background", null,
            MaterialResource.SHADELESS,
            ResourceGroup.MAIN_MENU));
        
        //MESHES
        //Background
        ResourceManager.add("main_menu_background", new RenderableResource(
            RenderableType.MESH,
            R.raw.sprite_std,
            Renderable.Group.STD, 5, "main_menu_background",
            ResourceGroup.MAIN_MENU));
    }
}
