//default per vertex lighting fragment shader

//set the precision
precision mediump float;

//VARIABLES
//the uv co-ordinates
varying vec2 v_UVCoord;
//the colour
varying vec4 v_Colour;

//MAIN METHOD
void main() {

	//set the colour
    gl_FragColor = v_Colour;
}