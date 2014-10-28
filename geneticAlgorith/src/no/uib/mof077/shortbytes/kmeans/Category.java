package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private Vector3 center;
	private List<Vector3> members;
	
	public Category() {
		this.center = new Vector3(0, 0, 0);
		this.members = new ArrayList<>();
	}

	public Vector3 getCenter() {
		return center;
	}

	public void setCenter(Vector3 center) {
		this.center = center;
	}

	public List<Vector3> getMembers() {
		return members;
	}

	public void setMembers(List<Vector3> members) {
		this.members = members;
	}

	public void evaluateCenter() {
		float totalX = 0;
		float totalY = 0;
		float totalZ = 0;
		for(Vector3 vector : this.members) {
			totalX += vector.getX();
			totalY += vector.getY();
			totalZ += vector.getZ();
		}
		float avgX = totalX / members.size();
		float avgY = totalY / members.size();
		float avgZ = totalZ / members.size();
		
		this.setCenter(new Vector3(avgX, avgY, avgZ));
	}
}
