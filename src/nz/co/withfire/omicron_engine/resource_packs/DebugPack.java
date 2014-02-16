/***********************************************\
| Resources for the standard debugging assets.  |
|                                               |
| @author David Saxon                           |
\***********************************************/

package nz.co.withfire.omicron_engine.resource_packs;

import nz.co.withfire.omicron_engine.R;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class DebugPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //MATERIALS
        //bounding
        ResourceManager.add("debug_bounding", new MaterialResource(
            "default_shadeless_colour",
            new Vector4(0.0f, 1.0f, 0.0f, 1.0f), null, null,
            MaterialResource.SHADELESS,
            ResourceGroup.DEBUG));
        
        //MESHES
        //bounding rect
        ResourceManager.add("debug_bounding_rect", new RenderableResource(
            R.raw.mesh_debug_bounding_rect,
            Renderable.Group.STD, 5, "debug_bounding",
            ResourceGroup.DEBUG));
        //bounding circle
        ResourceManager.add("debug_bounding_circle", new RenderableResource(
            R.raw.mesh_debug_bounding_circle,
            Renderable.Group.STD, 5, "debug_bounding",
            ResourceGroup.DEBUG));
        //bounding cube
        ResourceManager.add("debug_bounding_cube", new RenderableResource(
            R.raw.mesh_debug_bounding_cube,
            Renderable.Group.STD, 5, "debug_bounding",
            ResourceGroup.DEBUG));
    }
}
