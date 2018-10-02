#version 430

uniform float x1;
uniform float x2;
uniform float x3;

uniform float y1;
uniform float y2;
uniform float y3;

out vec4 initialColor;

void main(void)
{

	if(gl_VertexID == 0) {
		initialColor = vec4(1.0, 0.0, 0.0, 1.0);
		gl_Position = vec4( x1, y1, 0.0, 1.0);
	}
	else if(gl_VertexID == 1) {
		initialColor = vec4(0.0, 1.0, 0.0, 1.0);
		gl_Position = vec4(x2, y2, 0.0, 1.0);
	}
	else {
		initialColor = vec4(0.0, 0.0, 1.0, 1.0);
		gl_Position = vec4( x3, y3, 0.0, 1.0);
	}
}