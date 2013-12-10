/******************************\
| Stores an OpenGL shader set. |
|                               |
| @author David Saxon           |
\******************************/

package nz.co.withfire.omicronengine.omicron.resources.types;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import nz.co.withfire.omicronengine.omicron.graphics.material.shader.Shader;
import nz.co.withfire.omicronengine.omicron.resources.loaders.ShaderLoader;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class ShaderResource {

    //VARIABLES
    //the resource ids of the shaders
    private final int vertexId;
    private final int fragmentId;

    //the resource group of the shader
    private final ResourceGroup group;

    //the shader
    private Shader shader = null;

    //is true once the shader has been loaded
    private boolean loaded = false;

    //CONSTRUCTOR
    /**Creates a new shader resource with a vertex and fragment shader
    @param vertexId the id of the vertex shader
    @param fragmentId the id of the fragment shader
    @param group the resource group of the shader*/
    public ShaderResource(int vertexId, int fragmentId, ResourceGroup group) {

        //initialise variables
        this.vertexId = vertexId;
        this.fragmentId = fragmentId;
        this.group = group;
    }

    //PUBLIC METHODS
    /**Loads the shader and compiles it
    @param context the android context*/
    public void load(final Context context) {

        //check if the shader has already been loaded
        if (loaded) {

            return;
        }

        //compile the shaders
        int vertexShader = 0;
        if (vertexId != 0) {

            vertexShader = ShaderLoader.compileShader(context,
                GLES20.GL_VERTEX_SHADER, vertexId);
        }
        int fragmentShader = 0;
        if (fragmentId != 0) {

            fragmentShader = ShaderLoader.compileShader(context,
                GLES20.GL_FRAGMENT_SHADER, fragmentId);
        }

        //create the program
        int program = GLES20.glCreateProgram();

        //attach the shaders
        if (vertexShader != 0) {

            GLES20.glAttachShader(program, vertexShader);
        }
        if (fragmentShader != 0) {

            GLES20.glAttachShader(program, fragmentShader);
        }

        //create openGL program executables
        GLES20.glLinkProgram(program);

        //create the shader object
        shader = new Shader(vertexShader, fragmentShader, program);

        //successfully loaded
        loaded = true;
    }

    /**Frees the shader from memory*/
    public void destroy() {

        //do nothing if the shader is not loaded
        if (!loaded) {

            return;
        }

        //delete shaders and program
        GLES20.glDeleteShader(shader.getVertex());
        GLES20.glDeleteShader(shader.getFragment());
        GLES20.glDeleteProgram(shader.getProgram());
        //remove the shader
        shader = null;

        //successfully destroyed
        loaded = false;
    }

    /**@return the loaded shader*/
    public Shader getShader() {

        //check that the shader has been loaded
        if (!loaded) {

            //report error
            Log.v(Values.TAG, "Attempted to use an un-loaded shader");
            throw new RuntimeException(
                "Attempted to use an un-loaded shader");
        }

        return shader;
    }

    /**@return the group the shader is within*/
    public ResourceGroup getGroup() {

        return group;
    }

    /**@return whether shader has been loaded*/
    public boolean isLoaded() {

        return loaded;
    }
}
