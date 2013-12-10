/********************************************************************\
| The fragment Omicron vertex shader, does all the necessary things. |
|                                                                     |
| @author David Saxon                                                 |
\********************************************************************/

//set the precision
precision mediump float;

//VARIABLES
//the interpolated position
varying vec3 v_Position;
//the interpolated normal
varying vec3 v_Normal;

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

//the number of point lights
uniform int u_PointCount;
//the point light colours
uniform vec3 u_PointColour[16];
//the point light distances
uniform float u_PointDistances[16];
//the point light positions
uniform vec3 u_PointPos[16];

//MAIN METHOD
void main() {

    //the diffuse colour
    vec3 diffuseAll = vec3(0.0, 0.0, 0.0);

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

        //POINT LIGHTS
        //iterate over the point lights
        for (int i = 0; i < u_PointCount; ++i) {

            //calculate the distance from the light
            float distance = length(u_PointPos[i] - v_Position) /
                u_PointDistances[i];

            //calculate the direction from the light to the vertex
            vec3 lightVector = normalize(u_PointPos[i] - v_Position);

            //calculate the dot product between the normal and the light vector
            float diffuse = max(dot(v_Normal, lightVector), 0.0);

            //add attenuation
            diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));

            //calculate the diffuse vector
            vec3 diffuseVector = vec3(diffuse, diffuse, diffuse);
            diffuseVector *= u_PointColour[i];

            diffuseAll += diffuseVector;
        }

        //set the colour
        gl_FragColor = preLightColour * (vec4(diffuseAll, 1.0) + u_Ambient);
    }

}