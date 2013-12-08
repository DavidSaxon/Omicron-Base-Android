//VARIABLES
//the model matrix
uniform mat4 u_MMatrix;
//the model view projection matrix
uniform mat4 u_MVPMatrix;

//the vertex positions
attribute vec4 a_Position;
//uv co-ordinates
attribute vec2 a_UVCoord;
//the normals
attribute vec3 a_Normal;

//vertices data that will be passed to the fragment shader
varying vec3 v_Position;
//uv data that will be passed to the fragment shader
varying vec2 v_UVCoord;
//the fade
varying float v_Fade;

//time
uniform float u_Time;
//the position of the fish
uniform vec3 u_Position;

float wiggle(float value) {

	return sin(value + (u_Time * 6.0)) * 0.03;
}

void main() {
	
	vec4 pos = a_Position;

	//apply transformations to position
    v_Position = vec3(u_MMatrix * pos);

	//pass the uv co-ordinates through to the fragment shader
    v_UVCoord = a_UVCoord;

    pos = u_MVPMatrix * pos;

    pos.y += wiggle(v_UVCoord.x);

	//set the position
	gl_Position = pos;

	if (v_UVCoord.x <= 11.0) {

		v_Fade = v_UVCoord.x / 12.0;
	}
	else {

		v_Fade = 0.0;
	}
}