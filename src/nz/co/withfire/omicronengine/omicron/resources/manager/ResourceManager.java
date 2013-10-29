/**********************************************************\
| Manages the creation, loading, and freeing of resources. |
|														   |
| @author David Saxon									   |
\**********************************************************/

package nz.co.withfire.omicronengine.omicron.resources.manager;

import java.util.HashMap;
import java.util.Map;

import nz.co.withfire.omicronengine.omicron.graphics.material.shader.Shader;
import nz.co.withfire.omicronengine.omicron.resources.types.ShaderResource;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;
import nz.co.withfire.omicronengine.override.Values;

import android.content.Context;
import android.util.Log;

public class ResourceManager {

	//VARIABLES
	//the android context
	static Context context;
	
	//shader resource map
	private static Map<String, ShaderResource> shaders = null;
	
	//PUBLIC METHODS
	/**Initialises the resource manager
	@param context the android context*/
	public static void init(final Context context) {
		
		//initialise variables
		ResourceManager.context = context;
		shaders = new HashMap<String, ShaderResource>();
		
		//build the resource packs
		//TODO:
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
	
	/**Loads all the resource in the group into memory
	@param group the group to load*/
	public static void loadGroup(ResourceGroup group) {
		
		loadShaders(group);
	}
	
	//FREE
	/**Frees all shaders from memory*/
	public static void freeShaders() {
		
		for (ShaderResource s : shaders.values()) {
			
			if (s.isLoaded()) {
				
				//TODO: free
			}
		}
	}
	
	/**Frees the shaders within the group from memory
	@param group the group to free*/
	public static void freeShaders(ResourceGroup group) {
		
		for (ShaderResource s : shaders.values()) {
			
			if (s.isLoaded() && s.getGroup() == group) {
				
				//TODO: free
			}
		}
	}
	
	/**Frees all loaded resources from memory*/
	public static void free() {
		
		freeShaders();
	}
	
	/**Frees all loaded resources within the group from memory
	@param group the group to free*/
	public static void free(ResourceGroup group) {
		
		freeShaders(group);
	}
	
	//GET
	/**@return gets the shader from the resource map
	@param label the label of the shader
	@return the shader*/
	public static Shader getShader(String label) {
		
		return shaders.get(label).getShader();
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
	
	/**Cleans up the resource manager*/
	public static void cleanUp() {
		
		context = null;
		if (shaders != null) {
			
			shaders.clear();
			shaders = null;
		}
	}
}
