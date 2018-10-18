/*
Marko Smiljanic
CSc 155 Section 02
Due Date: October 15th
*/
package a2;

import graphicslib3D.*;


public class DiamondObj extends Obj3D {
	
	private float[] obj_Positions = 
	{	-1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 2.0f, 0.0f,    //front
		1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 2.0f, 0.0f,    //right
		1.0f, 0.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, 2.0f, 0.0f,  //back
		-1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 2.0f, 0.0f,  //left
		-1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, -2.0f, 0.0f,	//front below
		1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, -2.0f, 0.0f,    //right below
		1.0f, 0.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, -2.0f, 0.0f,  //back below
		-1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -2.0f, 0.0f,  //left below
	};
	
	public DiamondObj(float x, float y, float z) {
		setPoint(x, y, z);
	} 
	
	public float[] getVertices() {
		return obj_Positions;
	}
}