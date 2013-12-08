/**********************************************************\
| Manages the creation, loading, and freeing of resources. |
|														   |
| @author David Saxon									   |
\**********************************************************/

package nz.co.withfire.omicronengine.omicron.resources.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.co.withfire.omicronengine.omicron.graphics.material.Material;
import nz.co.withfire.omicronengine.omicron.graphics.material.shader.Shader;
import nz.co.withfire.omicronengine.omicron.graphics.material.texture.Texture;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicronengine.omicron.resources.types.MaterialResource;
import nz.co.withfire.omicronengine.omicron.resources.types.RenderableResource;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.omicron.resources.types.TextureResource;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.resource_packs.AllPack;
import nz.co.withfire.omicronengine.resource_packs.GUIPack;
import nz.co.withfire.omicronengine.resource_packs.LoadingPack;
import nz.co.withfire.omicronengine.resource_packs.MainMenuPack;
import nz.co.withfire.omicronengine.resource_packs.MaterialDemoPack;
import nz.co.withfire.omicronengine.resource_packs.StartUpPack;
import nz.co.withfire.omicronengine.resource_packs.TwoDDemoPack;

import android.content.Context;
import android.util.Log;

public class ResourceManager {

	//VARIABLES
	//the android context
	static Context context;
	
	//shader resource map
	private static Map<String, ShaderResource> shaders = null;
	//texture resource map
	private static Map<String, TextureResource> textures = null;
	//material resource map
	private static Map<String, MaterialResource> materials = null;
	//renderable resource map
	private static Map<String, RenderableResource> renderables = null;
	
	//concurrent loading lists
	//shaders
    private static List<ShaderResource> shadersLoad =
       new ArrayList<ShaderResource>();
	//textures
    private static List<TextureResource> texturesLoad =
        new ArrayList<TextureResource>();
	//materials
    private static List<MaterialResource> materialsLoad =
        new ArrayList<MaterialResource>();
	//renderables
	private static List<RenderableResource> renderablesLoad =
        new ArrayList<RenderableResource>();
	
	//we are currently loading something in the background
	private static boolean inBackground = false;
	//the number of resource there are to load in the current request
	private static int loadAmount = 0;
	//the number we have loaded so far in the current request
	private static int loadComplete = 0;
	
	//PUBLIC METHODS
	/**Initialises the resource manager
	@param context the android context*/
	public static void init(final Context context) {
		
		//initialise variables
		ResourceManager.context = context;
		shaders =     new HashMap<String, ShaderResource>();
		textures =    new HashMap<String, TextureResource>();
		materials =   new HashMap<String, MaterialResource>();
		renderables = new HashMap<String, RenderableResource>();
		
		//build the resource packs
		AllPack.build();
		GUIPack.build();
		LoadingPack.build();
		StartUpPack.build();
		MainMenuPack.build();
		MaterialDemoPack.build();
		TwoDDemoPack.build();
	}
	
	//LOAD
	/**Loads all shaders into memory*/
	public static void loadShaders() {
		
		for (ShaderResource s : shaders.values()) {
			
			s.load(context);
		}
	}
	
	/**Loads all shaders within the resource group into memory
	@param group the group to load*/
	public static void loadShaders(ResourceGroup group) {
		
		for (ShaderResource s : shaders.values()) {
			
			if (s.getGroup() == group) {
			
				s.load(context);
			}
		}
	}
	
	/**Loads all textures into memory*/
	public static void loadTextures() {
		
		for (TextureResource t : textures.values()) {
			
			t.load(context);
		}
	}
	
	/**Loads all textures within the resource group into memory
	@param group the group to load*/
	public static void loadTextures(ResourceGroup group) {
		
		for (TextureResource t : textures.values()) {
			
			if (t.getGroup() == group) {
			
				t.load(context);
			}
		}
	}
	
	/**Loads all materials into memory*/
	public static void loadMaterials() {
		
		for (MaterialResource m : materials.values()) {
			
			m.load(context);
		}
	}
	
	/**Loads all materials within the resource group into memory
	@param group the group to load*/
	public static void loadMaterials(ResourceGroup group) {
		
		for (MaterialResource m : materials.values()) {
			
			if (m.getGroup() == group) {
			
				m.load(context);
			}
		}
	}
	
	/**Loads all renderables into memory*/
	public static void loadRenderables() {
		
		for (RenderableResource r : renderables.values()) {
			
			r.load(context);
		}
	}
	
	/**Loads all renderables within the resource group into memory
	@param group the group to load*/
	public static void loadRenderables(ResourceGroup group) {
		
		for (RenderableResource r : renderables.values()) {
			
			if (r.getGroup() == group) {

				r.load(context);
			}
		}
	}
	
	/**Loads all resources into memory
	#WARNING: you prolly shouldn't do this*/
	public static void load() {
		
		loadShaders();
		loadTextures();
		loadMaterials();
		loadRenderables();
	}
	
	/**Loads all the resources in the group into memory
	@param group the group to load*/
	public static void load(ResourceGroup group) {
		
		loadShaders(group);
		loadTextures(group);
		loadMaterials(group);
		loadRenderables(group);
	}
	
	/**Concurrently loads all of the resources in the group into memory
	@param group the group to load*/
	public static void concurrentLoad(ResourceGroup group) {
	    
	    loadAmount = 0;
	    loadComplete = 0;
        
        //create the list of shaders
        for (ShaderResource s : shaders.values()) {
            
            if (s.getGroup() == group) {

                ++loadAmount;
                shadersLoad.add(s);
            }
        }
        
        //create the list of textures
        for (TextureResource t : textures.values()) {
            
            if (t.getGroup() == group) {
                
                ++loadAmount;
                texturesLoad.add(t);
            }
        }
        
        //create the list of materials
        for (MaterialResource m : materials.values()) {
            
            if (m.getGroup() == group) {

                ++loadAmount;
                materialsLoad.add(m);
            }
        }
        
        //create the list of renderables
        for (RenderableResource r : renderables.values()) {
            
            if (r.getGroup() == group) {

                ++loadAmount;
                renderablesLoad.add(r);
            }
        }
	}
	
	/**@return if the resource manager is currently performing
	concurrent loading*/
	public static boolean isConcurrentLoading() {
	    
	    return shadersLoad.size() > 0 ||
            texturesLoad.size() > 0 ||
            materialsLoad.size() > 0 ||
            renderablesLoad.size() > 0;
	}
	
	/**@return the current loading percent of the concurrent loading*/
	public static float concurrentLoadPercent() {
	    
	    return ((float) loadComplete) / ((float) loadAmount);
	}
	
	//FREE
	/**Frees all shaders from memory*/
	public static void destroyShaders() {
		
		for (ShaderResource s : shaders.values()) {
			
			if (s.isLoaded()) {
				
				s.destroy();
			}
		}
	}
	
	/**Frees the shaders within the group from memory
	@param group the group to free*/
	public static void destroyShaders(ResourceGroup group) {
		
		for (ShaderResource s : shaders.values()) {
			
			if (s.isLoaded() && s.getGroup() == group) {
				
				s.destroy();
			}
		}
	}
	
	/**Frees all textures from memory*/
	public static void destroyTextures() {
		
		for (TextureResource t : textures.values()) {
			
			if (t.isLoaded()) {
				
				t.destroy();
			}
		}
	}
	
	/**Frees the textures within the group from memory
	@param group the group to free*/
	public static void destroyTextures(ResourceGroup group) {
		
		for (TextureResource t : textures.values()) {
			
			if (t.isLoaded() && t.getGroup() == group) {
				
				t.destroy();
			}
		}
	}
	
	/**Frees all materials from memory*/
	public static void destroyMaterials() {
		
		for (MaterialResource m : materials.values()) {
			
			if (m.isLoaded()) {
				
				m.destroy();
			}
		}
	}
	
	/**Frees the materials within the group from memory
	@param group the group to free*/
	public static void destroyMaterials(ResourceGroup group) {
		
		for (MaterialResource m : materials.values()) {
			
			if (m.isLoaded() && m.getGroup() == group) {
				
				m.destroy();
			}
		}
	}
	
	/**Frees all renderables from memory*/
	public static void destroyRenderables() {
		
		for (RenderableResource r : renderables.values()) {
			
			if (r.isLoaded()) {
				
				r.destroy();
			}
		}
	}
	
	/**Frees the renderables within the group from memory
	@param group the group to free*/
	public static void destroyRenderables(ResourceGroup group) {
		
		for (RenderableResource r : renderables.values()) {
			
			if (r.isLoaded() && r.getGroup() == group) {
				
				r.destroy();
			}
		}
	}
	
	/**Frees all loaded resources from memory*/
	public static void destroy() {
		
		destroyShaders();
		destroyTextures();
		destroyMaterials();
		destroyRenderables();
	}
	
	/**Frees all loaded resources within the group from memory
	@param group the group to free*/
	public static void destroy(ResourceGroup group) {
		
		destroyShaders(group);
		destroyTextures(group);
		destroyMaterials(group);
		destroyRenderables(group);
	}
	
	//GET
	/**Gets the shader from the resource map
	@param label the label of the shader
	@return the shader*/
	public static Shader getShader(String label) {
		
		return shaders.get(label).getShader();
	}
	
	/**Gets the texture from the resource map
	@param label the label of texture
	@return the texture*/
	public static Texture getTexture(String label) {
		
		return textures.get(label).getTexture();
	}
	
	/**Gets the material from the resource map
	@param label the label of the material
	@return the material*/
	public static Material getMaterial(String label) {
		
		return materials.get(label).getMaterial();
	}
	
	/**Gets the renderable from the resource map
	@param label the label of the renderable
	@return the renderable*/
	public static Renderable getRenderable(String label) {
		
		return renderables.get(label).getRenderable().deepCopy();
	}
	
	//ADD
	/**Adds a new shader resource to the resource map
	@param label the label of the shader
	@param shader the shader resource to add*/
	public static void add(String label, ShaderResource shader) {
		
        //check to make sure the map doesn't contain the key
        if (shaders.containsKey(label)) {
            
            Log.v(Values.TAG, "Invalid shader key");
            throw new RuntimeException("Invalid shader key");
        }
        
        shaders.put(label,  shader);
	}
	
	/**Adds a new texture resource to the resource map
	@param label the label of the texture
	@param texture the texture resource to add*/
	public static void add(String label, TextureResource texture) {
		
        //check to make sure the map doesn't contain the key
        if (textures.containsKey(label)) {
            
            Log.v(Values.TAG, "Invalid texture key");
            throw new RuntimeException("Invalid texture key");
        }
        
        textures.put(label, texture);
	}
	
	/**Adds a new material resource to the resource map
	@param label the label of the material
	@param material the material resource to add*/
	public static void add(String label, MaterialResource material) {
		
        //check to make sure the map doesn't contain the key
        if (materials.containsKey(label)) {
            
            Log.v(Values.TAG, "Invalid material key");
            throw new RuntimeException("Invalid material key");
        }
        
        materials.put(label, material);
	}
	
	/**Adds a new renderable resource to the resource map
	@param label the label of the renderable
	@param renderable the renderable resource to add*/
	public static void add(String label, RenderableResource renderable) {
		
        //check to make sure the map doesn't contain the key
        if (renderables.containsKey(label)) {
            
            Log.v(Values.TAG, "Invalid renderable key");
            throw new RuntimeException("Invalid renderable key");
        }
        
        renderables.put(label, renderable);
	}
	
	/**Cleans up the resource manager*/
	public static void cleanUp() {
		
		context = null;
		if (shaders != null) {
			
			shaders.clear();
			shaders = null;
		}
		if (textures != null) {
			
			textures.clear();
			textures = null;
		}
		if (materials != null) {
			
			materials.clear();
			materials = null;
		}
		if (renderables != null) {
			
			renderables.clear();
			renderables = null;
		}
	}
	
	/**Callback to perform concurrent loading
	#WARNING: this should not be manually called*/
	public static void loadCycle() {
	    
	    if (inBackground) {
	        
	        return;
	    }
	    
	    //load a shader
	    if (shadersLoad.size() > 0) {
	        
	        //get the shader resource and load then remove
	        ShaderResource s = shadersLoad.get(0);
	        s.load(context);
	        shadersLoad.remove(s);
	        ++loadComplete;
	        
	        return;
	        
	    }
	    
	    //load a texture
        if (texturesLoad.size() > 0) {
            
            //get the texture resource and load then remove
            TextureResource r = texturesLoad.get(0);
            r.load(context);
            texturesLoad.remove(r);
            ++loadComplete;
            
            return;
        }
        
        //load a material
        if (materialsLoad.size() > 0) {
            
            //get the material resource and load then remove
            MaterialResource m = materialsLoad.get(0);
            m.load(context);
            materialsLoad.remove(m);
            ++loadComplete;
            
            return;
        }
	    
	    //load a renderable
	    if (renderablesLoad.size() > 0) {
	        
	        //start up a new loading thread
	        RenderableLoadThread thread = new RenderableLoadThread(
                renderablesLoad.get(0));
	        thread.start();
	        ++loadComplete;
	        
	        return;
	    }
	}
	
	//LOADING THREADS
	/**Concurrently loads a renderable*/
	public static class RenderableLoadThread extends Thread {
	    
	    //VARIABLES
	    //the renderable resource to load
	    private RenderableResource toLoad;
	    
	    //CONSTRUCTOR
	    /**Creates a new renderable load thread
	    @toLoad the renderable resource to load*/
	    public RenderableLoadThread(RenderableResource toLoad) {
	        
	        this.toLoad = toLoad;
	    }
	    
	    //PUBLIC METHODS
	    @Override
	    public void run() {
	        
	        inBackground = true;
	        
	        //load
	        toLoad.load(context);
	        
	        //remove from list
	        renderablesLoad.remove(toLoad);
	        
	        inBackground = false;
	    }
	}
}
