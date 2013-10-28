/**********************\
| For loading shaders. |
|					   |
| @author David Saxon  |
\**********************/

package nz.co.withfire.omicronengine.omicron.resources.loaders;

import nz.co.withfire.omicronengine.override.Values;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderLoader {

	//PUBLIC METHODS
	//TODO: option to not take a string!
	/**Compiles an OpenGL shader from source code
	@param shaderType the type of shader
	@param shaderSource the source code of the shader
	@return a handle to the shader*/
	public static int compileShader(final int shaderType,
		final String shaderSource) {
		
        //initialise the handle to the shader
        int shaderHandle = GLES20.glCreateShader(shaderType);
        
        //make sure the shader handle has been created
        if (shaderHandle == 0) {
        	
            //throw an error
            throw new RuntimeException("Error creating shader handle");
        }
        
        //pass in the shader source
        GLES20.glShaderSource(shaderHandle, shaderSource);
        
        //compile the shader
        GLES20.glCompileShader(shaderHandle);
        
        //get the compilation status
        final int[] compileStatus= new int[1];
        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS,
            compileStatus, 0);
        
        //check that the shader compiled correctly
        if (compileStatus[0] == 0) {
            
            //report error
            Log.v(Values.TAG, "Shader compilation failure");
            throw new RuntimeException("Shader compilation failure");
        }
        
        return shaderHandle;
	}
}
