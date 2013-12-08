//set the precision
precision mediump float;

//VARIABLES
//the interpolated position
varying vec3 v_Position;

//the colour
uniform vec4 u_Colour;
//the fade
varying float v_Fade;

//the texture
uniform sampler2D u_Texture;
//the uv co-ordinates
varying vec2 v_UVCoord;

//MAIN METHOD
void main() {

	vec4 texCol = texture2D(u_Texture, v_UVCoord);
	vec3 texDim = texCol.rgb * 0.7;

	gl_FragColor = vec4(texDim, (texCol.a * v_Fade)) + vec4(u_Colour.rgb, 0.0);
}