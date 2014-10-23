package no.uib.mof077.shortbytes.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
	private List<Person> people;
	
	public Tree() {
		people = new ArrayList<>();
		addPeople(
				new Person(Person.Gender.MALE, 0, Person.TravelCost.CHEAP, Person.IncomeLevel.LOW, Person.TransportMode.BUS),
				new Person(Person.Gender.MALE, 1, Person.TravelCost.CHEAP, Person.IncomeLevel.MEDIUM, Person.TransportMode.BUS),
				new Person(Person.Gender.FEMALE, 0, Person.TravelCost.CHEAP, Person.IncomeLevel.LOW, Person.TransportMode.BUS),
				new Person(Person.Gender.MALE, 1, Person.TravelCost.CHEAP, Person.IncomeLevel.MEDIUM, Person.TransportMode.BUS),
				new Person(Person.Gender.FEMALE, 1, Person.TravelCost.EXPENSIVE, Person.IncomeLevel.HIGH, Person.TransportMode.CAR),
				new Person(Person.Gender.MALE, 2, Person.TravelCost.EXPENSIVE, Person.IncomeLevel.MEDIUM, Person.TransportMode.CAR),
				new Person(Person.Gender.FEMALE, 2, Person.TravelCost.EXPENSIVE, Person.IncomeLevel.HIGH, Person.TransportMode.CAR),
				new Person(Person.Gender.FEMALE, 1, Person.TravelCost.CHEAP, Person.IncomeLevel.MEDIUM, Person.TransportMode.TRAIN),
				new Person(Person.Gender.MALE, 0, Person.TravelCost.STANDARD, Person.IncomeLevel.MEDIUM, Person.TransportMode.TRAIN),
				new Person(Person.Gender.FEMALE, 1, Person.TravelCost.STANDARD, Person.IncomeLevel.MEDIUM, Person.TransportMode.TRAIN));
	}
	
	public void addPeople(Person... persons) {
		for (Person person : persons) {
			people.add(person);
		}
	}
	
//	public Tree id3(examples, attributes) {
////		if all examples in same category then
////			return a leaf node with that category
////		if attributes is empty then
////			return a leaf node with the most common category in
////		examples
////		best = Choose-Attribute(examples,attributes)
////		tree = new tree with Best as root attribute test
////		foreach value vi of best example
////			example si = subset of examples with best == vi
////			subtree = ID3(example si, attributes – best)
////			add a branch to tree with best == vi and subtree beneath
////		return tree
//	}
	
	public double calculateEntropy(int divider, Map<String, Integer> categories) {
		double entropy = 0;
		for (String category : categories.keySet()) {
			double categoryValues = categories.get(category);
			entropy += -(categoryValues / divider) * (Math.log(categoryValues/divider) / Math.log(2));
		}
		return entropy;
	}
	
//	public double calculateGenderEntropy() {
//		
//		
//		
//		double maleCounter = 0;
//		double femaleCounter = 0;
//		for (Person person : people) {
//			if (person.getGender() == Person.Gender.MALE) {
//				maleCounter++;
//			} else {
//				femaleCounter++;
//			}
//		}
//		
//		int divider = people.size();
//		
//		// Print variables
//		System.out.println("Divider: " + divider);
//		System.out.println("Male count: " + maleCounter);
//		System.out.println("Female count: " + femaleCounter);
//		
//		// Entropy
//		double maleEntropy = -(maleCounter / divider) * (Math.log(maleCounter/divider) / Math.log(2));
//		double femaleEntropy = -(femaleCounter / divider) * (Math.log(femaleCounter/divider) / Math.log(2));
//		double entropy = maleEntropy + femaleEntropy;
//		
//		// Print entropy
//		System.out.println("Entropy: " + entropy);
//		return entropy;
//	}
	
	public static void main(String[] args) {
		Tree t = new Tree();
		
		Map<String, Integer> genderCategories = new HashMap<>();
		Map<String, Integer> carsCategories = new HashMap<>();
		Map<String, Integer> incomeCategories = new HashMap<>();
		Map<String, Integer> travelCostCategories = new HashMap<>();
		Map<String, Integer> transportModeCategories = new HashMap<>();
		
		genderCategories.put("Male", 0);
		genderCategories.put("Female", 0);
		
		carsCategories.put("0", 0);
		carsCategories.put("1", 0);
		carsCategories.put("2", 0);
		
		incomeCategories.put("Low", 0);
		incomeCategories.put("Med", 0);
		incomeCategories.put("High", 0);
		
		travelCostCategories.put("Cheap", 0);
		travelCostCategories.put("Standard", 0);
		travelCostCategories.put("Expensive", 0);
		
		transportModeCategories.put("Bus", 0);
		transportModeCategories.put("Car", 0);
		transportModeCategories.put("Train", 0);
		
		for (Person person : t.getPeople()) {
			if (person.getGender() == Person.Gender.MALE) {
				genderCategories.put("Male", genderCategories.get("Male") + 1);
			} else {
				genderCategories.put("Female", genderCategories.get("Female") + 1);
			}
			
			if (person.getIncomeLevel() == Person.IncomeLevel.LOW) {
				incomeCategories.put("Low", incomeCategories.get("Low") + 1);
			} else if (person.getIncomeLevel() == Person.IncomeLevel.MEDIUM) {
				incomeCategories.put("Med", incomeCategories.get("Med") + 1);
			} else {
				incomeCategories.put("High", incomeCategories.get("High") + 1);
			}
			
			if (person.getNumCars() == 0) {
				carsCategories.put("0", carsCategories.get("0") + 1);
			} else if (person.getNumCars() == 1) {
				carsCategories.put("1", carsCategories.get("1") + 1);
			} else {
				carsCategories.put("2", carsCategories.get("2") + 1);
			}
			
			if (person.getTransportMode() == Person.TransportMode.BUS) {
				transportModeCategories.put("Bus", transportModeCategories.get("Bus") + 1);
			} else if (person.getTransportMode() == Person.TransportMode.CAR) {
				transportModeCategories.put("Car", transportModeCategories.get("Car") + 1);
			} else {
				transportModeCategories.put("Train", transportModeCategories.get("Train") + 1);
			}
			
			if (person.getTravelCost() == Person.TravelCost.CHEAP) {
				travelCostCategories.put("Cheap", travelCostCategories.get("Cheap") + 1);
			} else if (person.getTravelCost() == Person.TravelCost.STANDARD) {
				travelCostCategories.put("Standard", travelCostCategories.get("Standard") + 1);
			} else {
				travelCostCategories.put("Expensive", travelCostCategories.get("Expensive") + 1);
			}
		}
		
		double genderEntropy = t.calculateEntropy(t.getPeople().size(), genderCategories);
		double carsEntropy = t.calculateEntropy(t.getPeople().size(), carsCategories);
		double travelCostEntropy = t.calculateEntropy(t.getPeople().size(), travelCostCategories);
		double transportEntropy = t.calculateEntropy(t.getPeople().size(), transportModeCategories);
		double incomeEntropy = t.calculateEntropy(t.getPeople().size(), incomeCategories);
		
		System.out.println("Gender entropy: " + genderEntropy);
		System.out.println("Cars entropy: " + carsEntropy);
		System.out.println("Travel entropy: " + travelCostEntropy);
		System.out.println("Income entropy: " + incomeEntropy);
		System.out.println("Transport entropy: " + transportEntropy);
		
		
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}
}
