package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
	
	private List<Vector3> vectors;
	private List<Category> categories;
	
	
	public KMeans() {
		this.vectors = new ArrayList<>();
		this.categories = new ArrayList<>();
		Random rand = new Random();
		addCategories(2);
		addVectors(80, 1000);
		for(Category c : this.categories) {
			int center = rand.nextInt(this.vectors.size()-1);
			Vector3 vec = this.vectors.get(center);
			c.setCenter(new Vector3(vec.getX(),vec.getY(),vec.getZ()));
			c.getMembers().add(vec);
		}
	}
	
	public double calculateEucledian(Vector3 value, Vector3 target) {
		double dX = (target.getX()-value.getX()) * (target.getX()-value.getX());
		double dY = (target.getY()-value.getY()) * (target.getY()-value.getY());
		double dZ = (target.getZ()-value.getZ()) * (target.getY()-value.getY());
		
		double distance = Math.sqrt(dX+dY+dZ);
		return distance;
	}
	
	public void addVector(Vector3... vec) {
		for (Vector3 vector : vec) {
			vectors.add(vector);
		}
	}
	
	public void addVectors(int maxRand, int amount) {
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			vectors.add(new Vector3((double)(rand.nextInt(maxRand) + rand.nextDouble()), (double)(rand.nextInt(maxRand) + rand.nextDouble()), (double)(rand.nextInt(maxRand) + rand.nextDouble())));
		}
	}
	
	public void addCategory(Category... cat) {
		for (Category category : cat) {
			categories.add(category);
		}
	}
	
	public void addCategories(int amount) {
		for (int i = 0; i < amount; i++) {
			categories.add(new Category());
		}
	}
	
	public void evaluateCenter() {
		for(Category c : this.categories) {
			c.evaluateCenter();
		}
	}

	public List<Vector3> getVectors() {
		return vectors;
	}

	public void setVectors(List<Vector3> vectors) {
		this.vectors = vectors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public static void main(String[] args) {
		KMeans means = new KMeans();
		int count = 1;
		for(Vector3 vec : means.getVectors()) {
			System.out.println("Vector: " +count);
			System.out.println(vec);
			count++;
		}
		
		
		double eucledian = 0;
		final double acceptance = 0.05d;
		double difference = acceptance + 1;
		
		while (!(difference < acceptance)) {
			int catCount = 1;
			for(Category c : means.getCategories()) {
				System.out.println(""
						+ "Category: " + catCount + "\n"
						+ "Center: " + c.getCenter().getX() +" " +c.getCenter().getY() + " " + c.getCenter().getZ() + "\n"
						+ "Members of category: " + c.getMembers().toString() + "\n"
						);
				int memberCount = 1;
				for(Vector3 vector : c.getMembers()) {
					System.out.println("Member: " + memberCount );
					System.out.println(vector.getX());
					System.out.println(vector.getY());
					System.out.println(vector.getZ() + "\n");
					memberCount++;
				}
				catCount++;
			}
			means.evaluateCenter();
			double newEucledian = means.calculateEucledian(new Vector3(0, 0, 0), new Vector3(2,2,2));
			difference = Math.abs(newEucledian-eucledian);
			System.out.println(""
					+ "Old eucledian distance = " + eucledian + "\n"
					+ "New eucledian distance = " + newEucledian + "\n"
					+ "Difference = " + difference + "\n"
					);
			
			eucledian = newEucledian;
		}
		
	}
}
