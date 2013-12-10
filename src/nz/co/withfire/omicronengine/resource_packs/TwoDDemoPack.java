/***********************************\
| Resources for the 2d demo scene.  |
|                                   |
| @author David Saxon               |
\***********************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.BoundingResource;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class TwoDDemoPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //SHADERS
        //glow fish
        ResourceManager.add("glow_fish", new ShaderResource(
            R.raw.shader_vertex_twoddemo_glow_fish,
            R.raw.shader_fragment_twoddemo_glow_fish,
            ResourceGroup.TWOD_DEMO));
        
        //TEXTURES
        //glow fish
        ResourceManager.add("glow_fish", new TextureResource(
            R.drawable.twoddemo_glow_beam,
            ResourceGroup.TWOD_DEMO));
        //explosion
        ResourceManager.add("fish_explosion", new TextureResource(
            R.drawable.twoddemo_explosion,
            ResourceGroup.TWOD_DEMO));
        
        //MATERIALS
        //glow fish
        ResourceManager.add("glow_fish", new MaterialResource(
            "glow_fish", null, "glow_fish", null,
            MaterialResource.SHADELESS,
            ResourceGroup.TWOD_DEMO));
        //explosion
        ResourceManager.add("fish_explosion", new MaterialResource(
            "default", null, "fish_explosion", null,
            MaterialResource.SHADELESS,
            ResourceGroup.TWOD_DEMO));
        
        //MESHES
        //glow fish
        ResourceManager.add("glow_fish", new RenderableResource(
            R.raw.mesh_twoddemo_glow_fish,
            Renderable.Group.STD, 5, "glow_fish",
            ResourceGroup.TWOD_DEMO));
        
        //ANIMATIONS
        //explosion
        ResourceManager.add("fish_explosion", new RenderableResource(
            R.raw.animation_level_explosion_medium,
            new Vector2(0.75f, 0.75f), 1,
            Renderable.Group.STD, 6, "fish_explosion",
            ResourceGroup.TWOD_DEMO));
        
        //BOUNDINGS
        ResourceManager.add("glow_fish", new BoundingResource(
            R.raw.bounding_twoddemo_glow_fish,
            ResourceGroup.TWOD_DEMO));
    }  
}
