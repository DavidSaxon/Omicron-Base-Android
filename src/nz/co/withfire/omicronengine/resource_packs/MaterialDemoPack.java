/****************************************\
| Resources for the material demo scene. |
|										 |
| @author David Saxon					 |
\****************************************/

package nz.co.withfire.omicronengine.resource_packs;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource.RenderableType;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
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
		//wood
		ResourceManager.add("wood", new TextureResource(
			R.drawable.materialdemo_wood,
			ResourceGroup.MATERIAL_DEMO));
		//metal
		ResourceManager.add("metal", new TextureResource(
			R.drawable.materialdemo_metal,
			ResourceGroup.MATERIAL_DEMO));
		//cube of fate
		ResourceManager.add("cube_of_fate", new TextureResource(
			R.drawable.materialdemo_cube_of_fate,
			ResourceGroup.MATERIAL_DEMO));
		
		//MATERIALS
		//the skybox
		ResourceManager.add("skybox", new MaterialResource(
			"default", null, "skybox", null,
			MaterialResource.SHADELESS,
			ResourceGroup.MATERIAL_DEMO));
		//wood
		ResourceManager.add("wood", new MaterialResource(
			"default", null, "wood", null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		//metal
		ResourceManager.add("metal", new MaterialResource(
			"default", null, "metal", null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		//cube of fate
		ResourceManager.add("cube_of_fate", new MaterialResource(
			"default", new Vector4(1.0f, 0.0f, 0.0f, 1.0f),
			"cube_of_fate", null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		//china
		ResourceManager.add("china", new MaterialResource(
			"default", new Vector4(1.0f, 1.0f, 1.0f, 1.0f),
			null, null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		//bronze
		ResourceManager.add("bronze", new MaterialResource(
			"default", new Vector4(0.7f, 0.55f, 0.2f, 1.0f),
			null, null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		//red plastic
		ResourceManager.add("red_plastic", new MaterialResource(
			"default", new Vector4(0.8f, 0.1f, 0.1f, 1.0f),
			null, null,
			MaterialResource.NONE,
			ResourceGroup.MATERIAL_DEMO));
		
		//MESHES
		//the skybox
		ResourceManager.add("skybox", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_skybox,
			Renderable.Group.STD, 4, "skybox",
			ResourceGroup.MATERIAL_DEMO));
		//table
		ResourceManager.add("table", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_table,
			Renderable.Group.STD, 5, "wood",
			ResourceGroup.MATERIAL_DEMO));
		//metal cube
		ResourceManager.add("metal_cube", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_cube,
			Renderable.Group.STD, 5, "metal",
			ResourceGroup.MATERIAL_DEMO));
		//cube of fate
		ResourceManager.add("cube_of_fate", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_cube,
			Renderable.Group.STD, 5, "cube_of_fate",
			ResourceGroup.MATERIAL_DEMO));
		//monkey
		ResourceManager.add("china_monkey", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_monkey,
			Renderable.Group.STD, 5, "china",
			ResourceGroup.MATERIAL_DEMO));
		//sphere
		ResourceManager.add("bronze_sphere", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_sphere,
			Renderable.Group.STD, 5, "bronze",
			ResourceGroup.MATERIAL_DEMO));
		//cone
		ResourceManager.add("plastic_cone", new RenderableResource(
			RenderableType.MESH,
			R.raw.mesh_materialdemo_cone,
			Renderable.Group.STD, 5, "red_plastic",
			ResourceGroup.MATERIAL_DEMO));
	}
}
