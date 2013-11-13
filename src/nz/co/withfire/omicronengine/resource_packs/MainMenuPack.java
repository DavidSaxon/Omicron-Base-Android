/******************************\
| Resources for the main menu. |
|							   |
| @author David Saxon		   |
\******************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MainMenuPack {

	//PUBLIC METHODS
	/**Builds the resources*/
	public static void build() {
		
    	//SHADERS
		//electric wave
		ResourceManager.add("electric_wave", new ShaderResource(
			R.raw.shader_vertex_electric_wave,
			R.raw.shader_fragment_electric_wave,
			ResourceGroup.MAIN_MENU));
		//spectrum beam
		ResourceManager.add("spectrum_beam", new ShaderResource(
			R.raw.shader_vertex_spectrum_beam,
			R.raw.shader_fragment_spectrum_beam,
			ResourceGroup.MAIN_MENU));
		
		//MATERIALS
		//electric wave
		ResourceManager.add("electric_wave", new MaterialResource(
			"electric_wave", null, null, null,
			MaterialResource.SHADELESS,
			ResourceGroup.MAIN_MENU));
		//spectrum beam
		ResourceManager.add("spectrum_beam", new MaterialResource(
			"spectrum_beam", null, null, null,
			MaterialResource.SHADELESS,
			ResourceGroup.MAIN_MENU));
		
		//MESHES
		//electric wave
		ResourceManager.add("electric_wave", new RenderableResource(
			RenderableType.MESH,
			R.raw.sprite_std,
			Renderable.Group.STD, 5, "electric_wave",
			ResourceGroup.MAIN_MENU));
		//spectrum beam
		ResourceManager.add("spectrum_beam", new RenderableResource(
			RenderableType.MESH,
			R.raw.sprite_std,
			Renderable.Group.STD, 5, "spectrum_beam",
			ResourceGroup.MAIN_MENU));
	}
}
