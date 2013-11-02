/****************************************\
| Resources for the material all scenes. |
|										 |
| @author David Saxon					 |
\****************************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class AllPack {
	
	//PUBLIC METHODS
	/**Builds the resources*/
	public static void build() {
		
    	//SHADERS
		ResourceManager.add("default", new ShaderResource(
			R.raw.shader_vertex_default,
			R.raw.shader_fragment_default,
			ResourceGroup.ALL));
	}
}
