package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
	
	private List<Vector3> vectors;
	private Vector3 center1;
	private Vector3 center2;
	
	
	public KMeans() {
		vectors = new ArrayList<>();
		Random rand = new Random();
		this.addVector(
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat())),
				new Vector3((float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()), (float)(rand.nextInt(9) + rand.nextFloat()))
				);
		int center1 = rand.nextInt(vectors.size()-1);
		int center2 = rand.nextInt(vectors.size()-1);
		this.center1 = vectors.get(center1);
		this.center2 = vectors.get(center2);
	}
	
	public void addVector(Vector3... vec) {
		for (Vector3 vector : vec) {
			vectors.add(vector);
		}
	}
	
	public static void main(String[] args) {
		KMeans means = new KMeans();
		List <Vector3>list = means.getVectors();
		int count = 1;
		for(Vector3 vec : list) {
			System.out.println("Vector: " +count);
			System.out.println(vec.getX());
			System.out.println(vec.getY());
			System.out.println(vec.getZ());
			System.out.println();
			count++;
		}

		System.out.println("Center1");
		System.out.println(means.getCenter1().getX());
		System.out.println(means.getCenter1().getY());
		System.out.println(means.getCenter1().getZ());
		System.out.println();
		System.out.println("Center2");
		System.out.println(means.getCenter2().getX());
		System.out.println(means.getCenter2().getY());
		System.out.println(means.getCenter2().getZ());
	}

	public List<Vector3> getVectors() {
		return vectors;
	}

	public void setVectors(List<Vector3> vectors) {
		this.vectors = vectors;
	}

	public Vector3 getCenter1() {
		return center1;
	}

	public void setCenter1(Vector3 center1) {
		this.center1 = center1;
	}

	public Vector3 getCenter2() {
		return center2;
	}

	public void setCenter2(Vector3 center2) {
		this.center2 = center2;
	}
}
