/**********************************\
| The background in the main menu. |
|								   |
| @author David Saxon			   |
\**********************************/

package nz.co.withfire.omicronengine.entities.main_menu;

import android.opengl.GLES20;
import android.util.Log;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.CustomShaderInputFunction;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable.CustomShaderInputMode;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.Values;

public class MainMenuBackground extends Entity {

	//VARIABLES
	//the mesh
	private Mesh mesh;
	
	//the rotation
	private Vector3 rot = new Vector3();
	
	//CONSTRUCTOR
	/**Creates a new main menu background*/
	public MainMenuBackground() {
		
		//get the mesh
	    mesh = (Mesh) ResourceManager.getRenderable("main_menu_background");
		
	    Log.v(Values.TAG, "" + TransformationsUtil.getOpenGLDim());
	    
		//scale to screen width
	    mesh.setScale(new Vector3(
			TransformationsUtil.getOpenGLDim().x * 1.3f,
			TransformationsUtil.getOpenGLDim().x * 1.3f, 1.0f));
		
		//add the custom shader input function
	    mesh.setCustomShaderInputMode(CustomShaderInputMode.ADD);
	    mesh.setCustomShaderInputFunction(new FlickerShaderInput());
		
		//add to the renderer
		OmicronRenderer.add(mesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void update() {
	    
	    rot.z += 0.25f * FPSManager.getTimeScale();
	    mesh.setGlobalRot(rot);
	}
	
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(mesh);
	}
	
	//PRIVATE INNER CLASS
	private class FlickerShaderInput implements CustomShaderInputFunction {

	    //VARIABLES
	    //flicker timer
	    private float flickerTimer = 1.1f;
	    //the current multiplier
	    private Vector3 colourMultiplier = new Vector3();

		//PUBLIC METHODS
		@Override
        public void shaderInput(int program) {
			
		    if (flickerTimer > 1.0f) {
		    
		        //generate random colours
		        colourMultiplier.x = 
	                0.9f + (ValuesUtil.rand.nextFloat() * 0.25f);
                colourMultiplier.y =
                    0.9f + (ValuesUtil.rand.nextFloat() * 0.25f);
                colourMultiplier.z =
                    0.9f + (ValuesUtil.rand.nextFloat() * 0.25f);
                flickerTimer = 0.0f;
		    }
		    else {
		        
		        flickerTimer += 0.4f * FPSManager.getTimeScale();
		    }
		    
			//pass in the colour multiplier
			GLES20.glUniform3fv(
				GLES20.glGetUniformLocation(program, "u_ColourMultiplier"),
				1, colourMultiplier.toArray(), 0);
        }
	}
}
