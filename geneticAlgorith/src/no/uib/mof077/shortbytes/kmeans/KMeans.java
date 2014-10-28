package no.uib.mof077.shortbytes.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
	
	private List<Vector3> vectors;
	private List<Category> categories;
	
	
	public KMeans() {
		vectors = new ArrayList<>();
		categories = new ArrayList<>();
		Random rand = new Random();
		this.addCategory(new Category(), new Category(), new Category());
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
		for(Category c : this.categories) {
			int center = rand.nextInt(vectors.size()-1);
			Vector3 vec = vectors.get(center);
			c.setCenter(new Vector3(vec.getX(),vec.getY(),vec.getZ()));
			c.getMembers().add(vec);
		}
	}
	
	public void addVector(Vector3... vec) {
		for (Vector3 vector : vec) {
			vectors.add(vector);
		}
	}
	
	public void addCategory(Category... cat) {
		for (Category category : cat) {
			categories.add(category);
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
			System.out.println(vec.getX());
			System.out.println(vec.getY());
			System.out.println(vec.getZ());
			System.out.println();
			count++;
		}
		int catCount = 1;
		for(Category c : means.getCategories()) {
			System.out.println("Category: " + catCount);
			System.out.println("Center: " + c.getCenter().getX() +" " +c.getCenter().getY() + " " + c.getCenter().getZ());
			System.out.println("Members of category: " + catCount);
			int memberCount = 1;
			for(Vector3 vector : c.getMembers()) {
				System.out.println("Member: " + memberCount );
				System.out.println(vector.getX());
				System.out.println(vector.getY());
				System.out.println(vector.getZ());
				System.out.println();
				memberCount++;
			}
			catCount++;
		}
		means.evaluateCenter();
	}
}
