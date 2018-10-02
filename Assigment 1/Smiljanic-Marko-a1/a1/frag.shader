#version 430
in vec4 initialColor;
out vec4 finalColor;

uniform int grad;

void main(void)
{
	if(grad == 1) {
		finalColor = initialColor;
	}
	else {
		finalColor = vec4(0.0, 0.0, 1.0, 1.0);
	}
}