//default std vertex shader

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

//the colour
uniform vec4 u_Colour;

//the colour to pass to the fragment shader
varying vec4 v_Colour;
//uv data that will be passed to the fragment shader
varying vec2 v_UVCoord;

//the ambient light
uniform vec4 u_Ambient;

//the number of point lights
uniform int u_PointCount;
//the point light colours
uniform vec3 u_PointColour[1];
//the point light distances
uniform float u_PointDistances[1];
//the point light positions
uniform vec3 u_PointPos[1];

//the number of directional lights
uniform int u_DirCount;
//the directional light colours
uniform vec3 u_DirColour[1];
//the directional light positions
uniform vec3 u_DirPos[1];

//MAIN METHOD
void main() {

    //apply transformations to position
    vec3 position = vec3(u_MMatrix * a_Position);

    //pass the uv co-ordinates through to the fragment shader
    v_UVCoord = a_UVCoord;

    //transform the normal into eye space
    vec3 normal = vec3(u_MMatrix * vec4(a_Normal, 0.0));

    //set the position
    gl_Position = u_MVPMatrix * a_Position;

    //LIGHTING
    //the diffuse colour
    vec3 diffuseAll = vec3(0.0, 0.0, 0.0);

    //POINT LIGHT
    if (u_PointCount > 0) {

        //calculate the distance from the light
        float distance = length(u_PointPos[0] - position) /
            u_PointDistances[0];

        //calculate the direction from the light to the vertex
        vec3 lightVector = normalize(u_PointPos[0] - position);

        //calculate the dot product between the normal and the light vector
        float diffuse = max(dot(normal, lightVector), 0.0);

        //add attenuation
        diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));

        //calculate the diffuse vector
        vec3 diffuseVector = vec3(diffuse, diffuse, diffuse);
        diffuseVector *= u_PointColour[0];

        diffuseAll += diffuseVector;
    }

    //DIRECTIONAL LIGHT
    if (u_DirCount > 0) {

        //calculate the dot product between the normal and the light vector
        float diffuse = max(dot(normal, u_DirPos[0]), 0.0);

        //calculate the diffuse vector
        vec3 diffuseVector = vec3(diffuse, diffuse, diffuse);
        diffuseVector *= u_DirColour[0];

        diffuseAll += diffuseVector;
    }

    //set the colour
    v_Colour = u_Colour * (vec4(diffuseAll, 1.0) + u_Ambient);
}