/*******************************************************\
| Fragment shader for simulating a wave of electricity. |
|													    |
| @author David Saxon								    |
\*******************************************************/

//set the precision
precision highp float;

//VARIABLES
//the resolution of the shader in pixels
uniform vec2 u_Resolution;
//time in seconds
uniform float u_Time;

//the interpolated position
varying vec3 v_Position;

//MAIN METHOD
void main() {

	//get the position in relation to the resolution
	vec2 pixelPos = gl_FragCoord.xy / u_Resolution.xy;

	vec3 wave_color = vec3(0.0);
		
	float wave_width = 0.01;

	for (float i = 0.0; i < 2.0; i += 1.0) {

		//this wave's co-ordinates
		highp vec2 waveCoords = (pixelPos * 2.0) - 1.0;
		
		//time shift
		highp float shift = u_Time * 9.0;
		shift = mod(shift, 18.5);

		//wave functions	
		waveCoords.y += (sin((waveCoords.x *   7.0) + ( shift       )    )) * 0.2;
		waveCoords.y += (cos((waveCoords.x *   2.0) + (-shift /  9.0)    )) * 0.05;
		waveCoords.y += (cos((waveCoords.x *  20.0) + (-shift *  2.1)    )) * 0.1;
		waveCoords.y += (sin((waveCoords.x *  43.0) + ( shift / 18.0) + i)) * 0.05;
		waveCoords.y += (cos((waveCoords.x * 103.0) + ( shift * 11.1)    )) * 0.01;
		waveCoords.y += (cos((waveCoords.x * 107.0) + ( shift * 22.2)    )) * 0.01;

		//set the wave
		wave_width = abs(1.0 / (150.0 * waveCoords.y));
		if (i < 1.0) {
			
			wave_color += vec3(wave_width * 1.0, wave_width * 0.5, wave_width * 0.25);
		}
		else {
			
			wave_color += vec3(wave_width * 1.0, wave_width * 0.5, wave_width * 2.0);
		}
	}

	gl_FragColor = vec4(wave_color, 1.0);
}