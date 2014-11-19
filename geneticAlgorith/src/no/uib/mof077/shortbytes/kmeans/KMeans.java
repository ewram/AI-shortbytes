package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {

	private List<Vector3> vectors;
	private List<Cluster> clusters;
	private final static double DISTANCE = 100000000;
	private final static double ACCEPTABLE_ERROR = 0.0;

	public KMeans(int clusters, int vectors, int maxVectorDimensionValue) {
		this.vectors = new ArrayList<>();
		this.clusters = new ArrayList<>();
		Random rand = new Random();
		addClusters(clusters);
		addVectors(maxVectorDimensionValue, vectors);
		for (Cluster c : this.clusters) {
			int center = rand.nextInt(this.vectors.size() - 1);
			Vector3 vec = this.vectors.get(center);
			c.setMean(new Vector3(vec.getX(), vec.getY(), vec.getZ()));
		}
	}

	public void placeVectors() {
		Cluster nearestCluster = null;
		for (Vector3 v : this.vectors) {
			double distance = DISTANCE;
			for (Cluster c : this.clusters) {
				double clusterDistance = calculateEucledian(v, c.getMean());
				if (clusterDistance < distance) {
					distance = clusterDistance;
					nearestCluster = c;
				}
			}
			nearestCluster.getMembers().add(v);
		}
	}

	public double calculateEucledian(Vector3 value, Vector3 target) {
		double dX = Math.pow((target.getX() - value.getX()), 2);
		double dY = Math.pow((target.getY() - value.getY()), 2);
		double dZ = Math.pow((target.getZ() - value.getZ()), 2);

		double distance = Math.sqrt(dX + dY + dZ);
		return distance;
	}

	public void addVector(Vector3... vec) {
		for (Vector3 vector : vec) {
			this.vectors.add(vector);
		}
	}

	public void addVectors(int maxRand, int amount) {
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			this.vectors.add(new Vector3(rand.nextInt(maxRand)
					+ rand.nextDouble(), rand.nextInt(maxRand)
					+ rand.nextDouble(), rand.nextInt(maxRand)
					+ rand.nextDouble()));
		}
	}

	public void addCluster(Cluster... clu) {
		for (Cluster cluster : clu) {
			this.clusters.add(cluster);
		}
	}

	public void addClusters(int amount) {
		for (int i = 0; i < amount; i++) {
			this.clusters.add(new Cluster());
		}
	}

	public void evaluateMean() {
		for (Cluster c : this.clusters) {
			c.evaluateMean();
		}
	}

	public void clearClusters() {
		for (Cluster c : this.clusters) {
			c.getMembers().clear();
		}
	}

	public List<Vector3> getVectors() {
		return this.vectors;
	}

	public void setVectors(List<Vector3> vectors) {
		this.vectors = vectors;
	}

	public List<Cluster> getClusters() {
		return this.clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public static void main(String[] args) {
		KMeans means = new KMeans(10, 1000, 10000);
		long startTime = System.nanoTime();
		String info = "";
		for (int i = 0; i < means.getClusters().size(); i++) {
			Cluster c = means.getClusters().get(i);
			info += "Cluster " + (i + 1) + " mean = " + c.getMean() + "\n";
		}

		// Print out info
		System.out.println("" + "Clusters: " + means.getClusters().size()
				+ "\n" + "Vectors: " + means.getVectors().size() + "\n" + info);

		double difference = ACCEPTABLE_ERROR + 1;
		int count = 0;
		while (difference > ACCEPTABLE_ERROR) {
			means.clearClusters();
			means.placeVectors();
			means.evaluateMean();

			for (Cluster c : means.getClusters()) {
				difference += means.calculateEucledian(c.getOldMean(),
						c.getMean());
			}
			difference /= means.getClusters().size();
			// System.out.println(difference);
			count++;
		}
		long endTime = System.nanoTime();
		System.out.println();
		System.out.println("Number of Iterations: " + count);
		System.out.println("Number of milliseconds: " + (endTime - startTime)
				/ 1000000);
		System.out.println();
		for (int i = 0; i < means.getClusters().size(); i++) {
			System.out.println("Cluster " + (i + 1) + " member count= "
					+ means.getClusters().get(i).getMembers().size()
					+ ". Mean = " + means.getClusters().get(i).getMean());
		}
	}
}
