/**************************************\
| Vertex shader for the spectrum beam. |
|									   |
| @author David Saxon				   |
\**************************************/

//VARIABLES
//the model matrix
uniform mat4 u_MMatrix;
//the model view projection matrix
uniform mat4 u_MVPMatrix;

//the vertex positions
attribute vec4 a_Position;

//vertices data that will be passed to the fragment shader
varying vec3 v_Position;

//MAIN METHOD
void main() {

	//apply transformations to position
    v_Position = vec3(u_MMatrix * a_Position);

    //set the position
	gl_Position = u_MVPMatrix * a_Position;
}