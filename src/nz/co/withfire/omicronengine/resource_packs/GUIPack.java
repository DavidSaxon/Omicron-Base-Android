/************************\
| Resources for the GUI. |
|						 |
| @author David Saxon    |
\************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class GUIPack {

	//PUBLIC METHODS
	/**Builds the resources*/
	public static void build() {
		
		//MATERIALS
		//fader
		ResourceManager.add("gui_fader", new MaterialResource(
			"default", new Vector4(0.0f, 0.0f, 0.0f, 1.0f) , null, null,
			MaterialResource.SHADELESS,
			ResourceGroup.GUI));
		
		//MESHES
		//fader
		ResourceManager.add("gui_fader", new RenderableResource(
			RenderableType.MESH,
			R.raw.sprite_std,
			Renderable.Group.GUI, 5, "gui_fader",
			ResourceGroup.GUI));
	}
}
