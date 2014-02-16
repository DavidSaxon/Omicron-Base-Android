//default per vertex lighting fragment shader

//set the precision
precision mediump float;

//VARIABLES
//the texture
uniform sampler2D u_Texture;
//the uv co-ordinates
varying vec2 v_UVCoord;
//the colour
varying vec4 v_Colour;

//MAIN METHOD
void main() {


	//the texture
    gl_FragColor = texture2D(u_Texture, v_UVCoord) * v_Colour;
}