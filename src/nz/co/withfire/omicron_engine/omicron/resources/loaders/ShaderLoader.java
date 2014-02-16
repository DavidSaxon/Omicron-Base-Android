/**********************\
| For loading shaders. |
|                       |
| @author David Saxon  |
\**********************/

package nz.co.withfire.omicron_engine.omicron.resources.loaders;

import java.util.Scanner;

import nz.co.withfire.omicron_engine.override.Values;
import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderLoader {

    //PUBLIC METHODS
    /**Compiles an OpenGL shader from a resource
    @param context the android context
    @param shaderType the type of shader
    @param resourceId the resource id of the shader
    @return a handle to the shader*/
    public static int compileShader(final Context context,
        final int shaderType, int resourceId) {

        //get the shader source code
        String shaderSource = StringLoader.loadString(context, resourceId);
        
        return compileShader(shaderType, shaderSource);
    }

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

            String type = "Vetex";
            if (shaderType == GLES20.GL_FRAGMENT_SHADER) {
                
                type = "Fragement";
            }
            
            Scanner scanner = new Scanner(shaderSource);
            String name = scanner.nextLine();
            scanner.close();
            
            //report error
            String message = type + " shader compliation failure (" +
                name + "): " + GLES20.glGetShaderInfoLog(shaderHandle);
            Log.v(Values.TAG, message );
            throw new RuntimeException(message);
        }

        return shaderHandle;
    }
}
