/*******************************************************************\
| The default Omicron vertex shader, does all the necessary things. |
|																	|
| @author David Saxon												|
\*******************************************************************/

//VARIABLES
//the model view projection matrix
uniform mat4 u_MVPMatrix;

//the vertex positions
attribute vec4 a_Position;
//uv co-ordinates
attribute vec2 a_UVCoord;

//uv data that will be passed to the fragment shader
varying vec2 v_UVCoord;

//MAIN METHOD
void main() {
	
	//pass the uv co-ordinates through to the fragment shader
    v_UVCoord = a_UVCoord;

	//set the position
	gl_Position = u_MVPMatrix * a_Position;
}