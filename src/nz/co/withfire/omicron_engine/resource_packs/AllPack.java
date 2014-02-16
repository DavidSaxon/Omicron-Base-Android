/****************************************\
| Resources for the material all scenes. |
|                                         |
| @author David Saxon                     |
\****************************************/

package nz.co.withfire.omicron_engine.resource_packs;

import nz.co.withfire.omicron_engine.R;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class AllPack {

    //PUBLIC METHODS
    /**Builds the resources*/
    public static void build() {

        //SHADERS
        //default lighting colour
        ResourceManager.add("default_lighting_colour", new ShaderResource(
            R.raw.shader_vertex_default_lighting,
            R.raw.shader_fragment_default_colour,
            ResourceGroup.ALL));
        //default lighting texture
        ResourceManager.add("default_lighting_texture", new ShaderResource(
                R.raw.shader_vertex_default_lighting,
                R.raw.shader_fragment_default_texture,
                ResourceGroup.ALL));
        //default shadeless colour
        ResourceManager.add("default_shadeless_colour", new ShaderResource(
            R.raw.shader_vertex_default_shadeless,
            R.raw.shader_fragment_default_colour,
            ResourceGroup.ALL));
            //default shadeless texture
        ResourceManager.add("default_shadeless_texture", new ShaderResource(
            R.raw.shader_vertex_default_shadeless,
            R.raw.shader_fragment_default_texture,
            ResourceGroup.ALL));
    }
}
