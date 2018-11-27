/*
Marko Smiljanic
CSc 155 Section 02
Due Date: November 27th
*/
package a3;

import graphicslib3D.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.shape.*;

public class TorusObj extends Obj3D {

	private Torus tor;
	
	public TorusObj(float x, float y, float z, float r1, float r2, int n) {
		setPoint(x, y, z);
		tor = new Torus(r1, r2, n);
	}
	
	public Vertex3D[] getVertices() {
		return tor.getVertices();
	}
	public int[] getIndices() {
		return tor.getIndices();
	}
}