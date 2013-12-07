//set the precision
precision mediump float;

//VARIABLES
//the interpolated position
varying vec3 v_Position;
//the interpolated normal
varying vec3 v_Normal;

//the texture
uniform sampler2D u_Texture;
//the uv co-ordinates
varying vec2 v_UVCoord;

//the colour multiplier
uniform vec3 u_ColourMultiplier;

//MAIN METHOD
void main() {

	gl_FragColor = texture2D(u_Texture, v_UVCoord) * vec4(u_ColourMultiplier, 1.0);
}