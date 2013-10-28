/************************************************************************\
| Shader's hold a pair of vertex and fragment shaders and the respective |
| compiled program.													     |
|																	     |
| @author David Saxon													 |
\************************************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.shader;

public class Shader {

	//VARIABLES
	//the vertex shader
	private final int vertex;
	//the fragment shader
	private final int fragment;
	//the OpenGL program
	private final int program;
	
	//CONSTRUCTOR
	/**Creates a new shader
	@param vertex the handle to the vertex shader
	@param fragment the handle to the fragment shader
	@param program the handle to the OpenGL program*/
	public  Shader(int vertex, int fragment, int program) {
		
		this.vertex = vertex;
		this.fragment = fragment;
		this.program = program;
	}
	
	//PUBLIC METHODS
	/**@return a handle to the vertex shader*/
	public int getVertex() {
		
		return vertex;
	}
	
	/**@return a handle to the fragment shader*/
	public int getFragment() {
		
		return fragment;
	}
	
	/**@return a handle to the OpenGL program*/
	public int getProgram() {
		
		return program;
	}
}
