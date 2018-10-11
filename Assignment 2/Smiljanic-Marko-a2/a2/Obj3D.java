package a2;

import graphicslib3D.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.shape.*;

public abstract class Obj3D {

	private Point3D loc = new Point3D(0.0f, 0.0f, 0.0f);
	
	public Point3D getPoint() {
		return loc;
	}
	
	public void setPoint(float x, float y, float z) {
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
	}
	public void setPoint(Point3D p) {
		loc.setX(p.getX());
		loc.setY(p.getY());
		loc.setZ(p.getZ());
	}
	
	public void addPoint(float x, float y, float z) {
		Point3D p = new Point3D(x, y, z);
		setPoint((float) loc.getX() + x, (float) loc.getY() + y, (float) loc.getZ() + z);
	}
	public void addPoint(Point3D p) {
		setPoint((float) (loc.getX() + p.getX()), (float) (loc.getY() + p.getY()), (float) (loc.getZ() + p.getZ()));
	}
}