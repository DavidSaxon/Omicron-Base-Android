/********************************************************************\
| The fragment Omicron vertex shader, does all the necessary things. |
|																	 |
| @author David Saxon												 |
\********************************************************************/

//set the precision
precision mediump float;

//VARIABLES
//the colour
uniform vec4 u_Colour;

//if there is a texture
uniform float u_HasTexture;
//the texture
uniform sampler2D u_Texture;
//the uv co-ordinates
varying vec2 v_UVCoord;

//MAIN METHOD
void main() {
	
	//the colour of the texture
	vec4 textureColour = vec4(1.0, 1.0, 1.0, 1.0);
	if (u_HasTexture > 0.5) {

		textureColour = texture2D(u_Texture, v_UVCoord);
	}

	//set the colour
	gl_FragColor = textureColour * u_Colour;
}