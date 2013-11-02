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
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.Values;

public class Background extends Entity {

	//VARIABLES
	//the electricity mesh
	private final Mesh electricMesh;
	
	//CONSTRUCTOR
	/**Creates a new main menu background*/
	public Background() {
		
		//get the mesh
		electricMesh = (Mesh) ResourceManager.getRenderable("electric_wave");
		
		//scale to screen width
		electricMesh.setScale(new Vector3(1.7777f, 1.0f, 1.0f));
		
		//add the custom shader input function
		electricMesh.setCustomShaderInputMode(CustomShaderInputMode.ADD);
		electricMesh.setCustomShaderInputFunction(new ElectricShaderInput());
		
		//add to the renderer
		OmicronRenderer.add(electricMesh);
	}
	
	//PUBLIC METHODS
	@Override
	public void cleanUp() {
		
		OmicronRenderer.remove(electricMesh);
	}
	
	//PRIVATE INNER CLASS
	private class ElectricShaderInput implements CustomShaderInputFunction {

		//VARIABLES
		//time passed
		private float timePassed = 0.0f;
		//the last time at update
		private long lastTime = System.currentTimeMillis();;

		//PUBLIC METHODS
		@Override
        public void shaderInput(int program) {
			
			//update time
			long currentTime = System.currentTimeMillis();
			float timeDiff = currentTime - lastTime;
			lastTime = currentTime;
			timePassed += timeDiff / ValuesUtil.MS_IN_SEC;
			
			//pass in the resolution of the shader
			GLES20.glUniform2fv(
				GLES20.glGetUniformLocation(program, "u_Resolution"),
				1, Camera.getDimensions().toArray(), 0);
			
			//pass in time
			GLES20.glUniform1f(
				GLES20.glGetUniformLocation(program, "u_Time"),
				timePassed);
        }
	}
}
