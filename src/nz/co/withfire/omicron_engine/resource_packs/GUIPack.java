/************************\
| Resources for the GUI. |
|                         |
| @author David Saxon    |
\************************/

package nz.co.withfire.omicron_engine.resource_packs;

import nz.co.withfire.omicron_engine.R;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.resources.types.BoundingResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource.FaceDirection;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class GUIPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {

        //MATERIALS
        //fader
        ResourceManager.add("gui_fader", new MaterialResource(
            "default_shadeless_colour",
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f) , null, null,
            MaterialResource.SHADELESS,
            ResourceGroup.GUI));
        //dimmer
        ResourceManager.add("gui_dimmer", new MaterialResource(
            "default_shadeless_colour",
            new Vector4(0.0f, 0.0f, 0.0f, 0.75f) , null, null,
            MaterialResource.SHADELESS,
            ResourceGroup.GUI));

        //SPRITES
        //fader
        ResourceManager.add("gui_fader", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1, FaceDirection.FRONT,
            Renderable.Group.GUI, 5, "gui_fader",
            ResourceGroup.GUI));
        //dimmer
        ResourceManager.add("gui_dimmer", new RenderableResource(
            new Vector2(2.0f, 2.0f), 1, FaceDirection.FRONT,
            Renderable.Group.GUI, 5, "gui_dimmer",
            ResourceGroup.GUI));
        
        //BOUNDINGS
        //touch point
        ResourceManager.add("touch_point", new BoundingResource(
            R.raw.bounding_touch_point,
            ResourceGroup.GUI));
    }
}
