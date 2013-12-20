/***************************************\
| Resources for the physics demo scene. |
|                                       |
| @author David Saxon                   |
\***************************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class PhysicsDemoPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {
        
        //MATERIALS
        //white
        ResourceManager.add("whiteware", new MaterialResource(
            "default", new Vector4(1.0f, 1.0f, 1.0f, 1.0f), null, null,
            MaterialResource.NONE,
            ResourceGroup.PHYSICS_DEMO));
        
        //MESHES
        //white cube
        ResourceManager.add("white_cube", new RenderableResource(
            R.raw.mesh_physicsdemo_cube,
            Renderable.Group.STD, 5, "whiteware",
            ResourceGroup.PHYSICS_DEMO));
        //white room
        ResourceManager.add("white_room", new RenderableResource(
            R.raw.mesh_physicsdemo_room,
            Renderable.Group.STD, 5, "whiteware",
            ResourceGroup.PHYSICS_DEMO));
    }
}
