/***********************************\
| Resources for the start up scene. |
|						   		    |
| @author David Saxon			    |
\***********************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class StartUpPack {

	//PUBLIC METHODS
	/**Builds the resources*/
	public static void build() {
		
		//TEXTURES
		//Omicron splash
		ResourceManager.add("omicron_splash", new TextureResource(
			R.drawable.startup_omicron_splash,
			ResourceGroup.START_UP));
		
		//MATERIALS
		//Omicron splash
		ResourceManager.add("omicron_splash", new MaterialResource(
			"default", null, "omicron_splash", null,
			MaterialResource.SHADELESS,
			ResourceGroup.START_UP));
		
		//MESHES
		//Omicron splash
		ResourceManager.add("omicron_splash", new RenderableResource(
			RenderableType.MESH,
			R.raw.sprite_std,
			Renderable.Group.STD, 5, "omicron_splash",
			ResourceGroup.START_UP));
	}
}
