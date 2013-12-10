/***********************************************************\
| Used to defined custom shader input code for renderables. |
|                                                            |
| @author David Saxon                                        |
\***********************************************************/


package nz.co.withfire.omicronengine.omicron.graphics.renderable;

public interface CustomShaderInputFunction {

    //PUBLIC METHODS
    /**Override this method to add custom shader input to a renderable
    @param program the OpenGL program of the renderable*/
    public void shaderInput(int program);
}
