package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private Vector3 mean;
	private List<Vector3> members;
	private Vector3 oldMean;
	
	public Cluster() {
		this.oldMean = null;
		this.mean = null;
		this.members = new ArrayList<>();
	}

	public Vector3 getMean() {
		return mean;
	}

	public void setMean(Vector3 center) {
		this.mean = center;
	}

	public List<Vector3> getMembers() {
		return members;
	}

	public void setMembers(List<Vector3> members) {
		this.members = members;
	}

	public void evaluateMean() {
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
		
		this.setOldMean(this.getMean());
		
		this.setMean(new Vector3(avgX, avgY, avgZ));
	}

	public Vector3 getOldMean() {
		return oldMean;
	}

	public void setOldMean(Vector3 oldMean) {
		this.oldMean = oldMean;
	}
	
	public boolean checkMeanDiffrence() {
		return mean.equals(oldMean);
	}
}
