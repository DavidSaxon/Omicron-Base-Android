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

//if the material is shadeless
uniform float u_Shadeless;
//the ambient light
uniform vec4 u_Ambient;

//MAIN METHOD
void main() {

	//the diffuse colour
	vec4 diffuse = vec4(0.0, 0.0, 0.0, 1.0);

	//the colour of the texture
	vec4 textureColour = vec4(1.0, 1.0, 1.0, 1.0);
	if (u_HasTexture > 0.5) {

		textureColour = texture2D(u_Texture, v_UVCoord);
	}

	//the colour before lighting
	vec4 preLightColour = textureColour * u_Colour;

	//do not compute lighting
	if (u_Shadeless > 0.5) {

		//set the colour
		gl_FragColor = preLightColour;
	}
	//compute lighting
	else {

		//set the colour
		gl_FragColor = preLightColour * (diffuse + u_Ambient);
	}
	
}