/*
Marko Smiljanic
CSc 155 Section 02
Due Date: November 27th
*/
package a3;

import graphicslib3D.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.shape.*;

public class SphereObj extends Obj3D {

	private Sphere sph;
	
	public SphereObj(float x, float y, float z, int n) {
		setPoint(x, y, z);
		sph = new Sphere(n);
	}
	
	public Vertex3D[] getVertices() {
		return sph.getVertices();
	}
	public int[] getIndices() {
		return sph.getIndices();
	}
}