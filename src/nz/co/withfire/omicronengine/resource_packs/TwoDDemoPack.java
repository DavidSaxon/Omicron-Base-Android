/***********************************\
| Resources for the 2d demo scene.  |
|                                   |
| @author David Saxon               |
\***********************************/

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

public class TwoDDemoPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //SHADERS
        //Background
        ResourceManager.add("glow_fish", new ShaderResource(
            R.raw.shader_vertex_twoddemo_glow_fish,
            R.raw.shader_fragment_twoddemo_glow_fish,
            ResourceGroup.TWOD_DEMO));
        
        //TEXTURES
        //glow beam
        ResourceManager.add("glow_fish", new TextureResource(
            R.drawable.twoddemo_glow_beam,
            ResourceGroup.TWOD_DEMO));
        
        //MATERIALS
        //glow beam
        ResourceManager.add("glow_fish", new MaterialResource(
            "glow_fish", null, "glow_fish", null,
            MaterialResource.SHADELESS,
            ResourceGroup.TWOD_DEMO));
        
        //MESHES
        //glow fish
        ResourceManager.add("glow_fish", new RenderableResource(
            RenderableType.MESH,
            R.raw.mesh_twoddemo_strip,
            Renderable.Group.STD, 5, "glow_fish",
            ResourceGroup.TWOD_DEMO));
    }  
}
