/**************************************************************************\
| Customs draw functions can be extended with renderable drawing code      |
| and then used by a renderable, in addition or in replace of the standard |
| draw code.	                                                           |
|																		   |
| @author David Saxon													   |
\**************************************************************************/


package nz.co.withfire.omicronengine.omicron.graphics.renderable;

public class CustomDrawFunction {

	//PUBLIC METHODS
	/**Override this method to add custom draw to a shape
	@param program the OpenGL program of the shape*/
	public void draw(int program) {
		
		//TO OVERRIDE
	}
}
