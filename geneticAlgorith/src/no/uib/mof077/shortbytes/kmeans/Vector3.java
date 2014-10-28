package no.uib.mof077.shortbytes.kmeans;

public class Vector3 {
	
	private double x;
	private double y;
	private double z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		String string = "";
		string += "x=" + this.x + ", ";
		string += "y=" + this.y + ", ";
		string += "z=" + this.z;
		return string;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
