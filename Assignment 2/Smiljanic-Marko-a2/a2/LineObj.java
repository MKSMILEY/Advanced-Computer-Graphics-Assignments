/*
Marko Smiljanic
CSc 155 Section 02
Due Date: October 15th
*/
package a2;

import graphicslib3D.*;


public class LineObj extends Obj3D {
	private int dir;
	
	public LineObj(float x, float y, float z, int n) {
		setPoint(x, y, z);
		dir = n;
	} 
	
	public float[] getVertices() {
		if(dir == 0) {
			float[] pos = {	0.0f, 0.0f, 0.0f, 100.0f, 0.0f, 0.0f	};
			return pos;
		}
		else if(dir == 1) {
			float[] pos = {	0.0f, 0.0f, 0.0f, 0.0f, 100.0f, 0.0f	};
			return pos;
		}
		else {
			float[] pos = {	0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 100.0f	};
			return pos;
		}
	}
}