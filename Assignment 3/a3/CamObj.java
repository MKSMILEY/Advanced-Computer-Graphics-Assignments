/*
Marko Smiljanic
CSc 155 Section 02
Due Date: November 27th
*/
package a3;

import graphicslib3D.*;

public class CamObj extends Obj3D {
	private Matrix3D viewMat;
	float pitch = 0;
	float yaw = 0;
	
	float moveAmt = 0.1f;
	
	public CamObj(float x, float y, float z) {
		setPoint(x, y, z);
		
		viewMat = new Matrix3D();
		computeView();
	}
	
	public void computeView() {
		Vector3D loc = new Vector3D(this.getPoint().getX(), this.getPoint().getY(), this.getPoint().getZ());
		float cosYaw = (float)Math.cos(Math.toRadians(yaw));
		float sinYaw = (float)Math.sin(Math.toRadians(yaw));
		float cosPitch = (float)Math.cos(Math.toRadians(pitch));
		float sinPitch = (float)Math.sin(Math.toRadians(pitch));
		Vector3D xAxis = new Vector3D(cosYaw, 0, -sinYaw);
		Vector3D yAxis = new Vector3D(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
		Vector3D zAxis = new Vector3D(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch);
		double[] matrixArray = new double[] {xAxis.getX(), yAxis.getX(), zAxis.getX(), 0,
											xAxis.getY(), yAxis.getY(), zAxis.getY(), 0,
											xAxis.getZ(), yAxis.getZ(), zAxis.getZ(), 0,
											-(xAxis.dot(loc)), -(yAxis.dot(loc)), -(zAxis.dot(loc)), 1};
		viewMat.setValues(matrixArray);
	}
	
	public void moveForward() {
		addPoint(0.0f, 0.0f, -moveAmt);
		computeView();
	}
	
	public void moveBack() {
		addPoint(0.0f, 0.0f, moveAmt);
		computeView();
	}
	
	public void moveLeft() {
		addPoint(-moveAmt, 0.0f, 0.0f);
		computeView();
	}
	
	public void moveRight() {
		addPoint(moveAmt, 0.0f, 0.0f);
		computeView();
	}
	
	public void moveUp() {
		addPoint(0.0f, moveAmt, 0.0f);
		computeView();
	}
	
	public void moveDown() {
		addPoint(0.0f, -moveAmt, 0.0f);
		computeView();
	}
	
	public void pitchUp() {
		pitch++;
		computeView();
	}
	
	public void pitchDown() {
		pitch--;
		computeView();
	}
	
	public void yawLeft() {
		yaw++;
		computeView();
	}
	
	public void yawRight() {
		yaw--;
		computeView();
	}
	
	public Matrix3D getView() {
		return viewMat;
	}
	
}