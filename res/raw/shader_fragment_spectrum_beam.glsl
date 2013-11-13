/****************************************\
| Fragment shader for the spectrum beam. |
|									     |
| @author David Saxon				     |
\****************************************/

//set the precision
precision highp float;

//VARIABLES
//the resolution of the shader in pixels
uniform vec2 u_Resolution;
//time in seconds
uniform float u_Time;

//the interpolated position
varying vec3 v_Position;

//FUNCTIONS
/*Gets the horizontal base colour*/
vec3 getHorColour(vec2 uv) {

	//rgb colour scheme
	// float xCol = (uv.x - (u_Time / 8.0)) * 3.0;
	
	// xCol = mod(xCol, 3.0);
	// vec3 horColour = vec3(0.25, 0.25, 0.25);
	
	// if (xCol < 1.0) {
		
	// 	horColour.r += 1.0 - xCol;
	// 	horColour.g += xCol;
	// }
	// else if (xCol < 2.0) {
		
	// 	xCol -= 1.0;
	// 	horColour.g += 1.0 - xCol;
	// 	horColour.b += xCol;
	// }
	// else {
		
	// 	xCol -= 2.0;
	// 	horColour.r += xCol;
	// 	horColour.b += 1.0 - xCol;
	// }

	//pastel colour scheme
	// float xCol = (uv.x - (u_Time / 8.0)) * 3.0;
	
	// xCol = mod(xCol, 3.0);
	// vec3 horColour = vec3(0.25, 0.25, 0.25);
	
	// if (xCol < 1.0) {
		
	// 	horColour.r = 1.0;
	// 	horColour.g += xCol;
	// 	horColour.b += 1.0 - xCol;
	// }
	// else if (xCol < 2.0) {
		
	// 	xCol -= 1.0;
	// 	horColour.r = 1.0 - xCol;
	// 	horColour.g += 1.0;
	// 	horColour.b += xCol;
	// }
	// else {
		
	// 	xCol -= 2.0;
	// 	horColour.r = xCol;
	// 	horColour.g += 1.0 - xCol;
	// 	horColour.b += 1.0;
	// }

	//full colour scheme
	float xCol = (uv.x - (u_Time / 8.0)) * 6.0;
	xCol = mod(xCol, 6.0);
	vec3 horColour = vec3(0.25, 0.25, 0.25);
	
	if (xCol < 1.0) {
		
		horColour.r += 1.0;
		horColour.g += xCol;
	}
	else if (xCol < 2.0) {
		
		xCol -= 1.0;
		horColour.r += 1.0 - xCol;
		horColour.g += 1.0;
	}
	else if (xCol < 3.0) {
		
		xCol -= 2.0;
		horColour.g += 1.0;
		horColour.b += xCol;
	}
	else if (xCol < 4.0) {
		
		xCol -= 3.0;
		horColour.g += 1.0 - xCol;
		horColour.b += 1.0;
	}
	else if (xCol < 5.0) {
		
		xCol -= 4.0;
		horColour.b += 1.0;
		horColour.r += xCol;
	}
	else {

		xCol -= 5.0;
		horColour.b += 1.0 - xCol;
		horColour.r += 1.0;
	}

	return horColour;
}

//MAIN METHOD
void main() {

	vec2 uv = gl_FragCoord.xy / u_Resolution.xy;

	//get the colour for horizontal lines
	vec3 horColour = getHorColour(uv);

	//background lines
	float backValue = 1.0;
	float aspect = u_Resolution.x / u_Resolution.y;
	if (mod(uv.y * 30.0, 1.0) > 0.75 || mod(uv.x * 30.0 * aspect, 1.0) > 0.75) {
		
		backValue = 1.2;	
	}
	
	vec3 backLines  = vec3(backValue);

	//main beam
	uv = (2.0 * uv) - 1.0;
	float beamWidth = abs(1.0 / (uv.y * 35.0));
	vec3 horBeam = vec3(beamWidth);

	gl_FragColor = vec4(horBeam * backLines * horColour, 1.0);
}