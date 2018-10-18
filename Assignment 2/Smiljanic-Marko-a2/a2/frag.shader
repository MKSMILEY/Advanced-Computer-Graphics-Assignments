#version 430

in vec2 tc;
//in vec4 varyingColor;
out vec4 color;

uniform mat4 mv_matrix;
uniform mat4 proj_matrix;
layout (binding = 0) uniform sampler2D samp;

void main(void)
{	
	//color = varyingColor;
	color = texture(samp, tc);
}
