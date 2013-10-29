/****************************************\
| Resources for the material demo scene. |
|										 |
| @author David Saxon					 |
\****************************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MaterialDemoPack {

	//PUBLIC METHODS
	/**Builds the resources*/
	public static void build() {
		
    	//SHADERS
    	//TODO: put in all
		ResourceManager.add("default", new ShaderResource(
			R.raw.shader_vertex_default,
			R.raw.shader_fragment_default,
			ResourceGroup.MATERIAL_DEMO));
		
		//TEXTURES
		//the skybox
		ResourceManager.add("skybox", new TextureResource(
			R.drawable.materialdemo_skybox,
			ResourceGroup.MATERIAL_DEMO));
		//the metal cube
		ResourceManager.add("metal", new TextureResource(
			R.drawable.materialdemo_metal,
			ResourceGroup.MATERIAL_DEMO));
	}
}
