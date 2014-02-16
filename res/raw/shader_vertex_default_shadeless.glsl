//default per vertex lighting vertex shader

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

    //set the colour
    v_Colour = u_Colour;
}