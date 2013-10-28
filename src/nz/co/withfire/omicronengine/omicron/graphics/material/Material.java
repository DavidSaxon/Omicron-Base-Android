/**********************************************\
| A material that is attached to a renderable. |
|											   |
| @author David Saxon						   |
\**********************************************/

package nz.co.withfire.omicronengine.omicron.graphics.material;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.omicron.graphics.shader.Shader;

public class Material {

	//VARIABLES
	//the shader of the material
	private Shader shader;
	
	//the colour of the material
	private Vector4 colour = new Vector4();
	//the texture of the material
	//TODO:
	
	//CONSTRUCTOR
	/**Creates a new material
	NOTE: no values can be assigned via the constructor*/
	public Material() {
	}
	
	//PUBLIC METHODS
	/**@return the shader of the material*/
	public Shader getShader() {
		
		return shader;
	}
	
	/**@return the colour of the material*/
	public Vector4 getColour() {
		
		return colour;
	}
	
	/**@param shader the new shader of the material*/
	public void setShader(Shader shader) {
		
		this.shader = shader;
	}
	
	/**@param colour the new colour of the material*/
	public void setColour(Vector4 colour) {
		
		this.colour = colour;
	}
}
