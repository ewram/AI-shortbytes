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
	
	public static void main(String[] args) {
		Tree t = new Tree();
		
		Map<String, Integer> genderCategories = new HashMap<>();
		Map<String, Integer> carsCategories = new HashMap<>();
		Map<String, Integer> incomeCategories = new HashMap<>();
		Map<String, Integer> travelCostCategories = new HashMap<>();
		Map<String, Integer> transportModeCategories = new HashMap<>();
		
		String male, female, zero, one, two, low, med, high, cheap, standard, expensive, bus, car, train;
		male = Person.Gender.MALE.name();
		female = Person.Gender.FEMALE.name();
		zero = "0";
		one = "1";
		two = "2";
		low = Person.IncomeLevel.LOW.name();
		med = Person.IncomeLevel.MEDIUM.name();
		high = Person.IncomeLevel.HIGH.name();
		cheap = Person.TravelCost.CHEAP.name();
		standard = Person.TravelCost.STANDARD.name();
		expensive = Person.TravelCost.EXPENSIVE.name();
		bus = Person.TransportMode.BUS.name();
		car = Person.TransportMode.CAR.name();
		train = Person.TransportMode.TRAIN.name();
		
		
		genderCategories.put(male, 0);
		genderCategories.put(female, 0);
		
		carsCategories.put(zero, 0);
		carsCategories.put(one, 0);
		carsCategories.put(two, 0);
		
		incomeCategories.put(low, 0);
		incomeCategories.put(med, 0);
		incomeCategories.put(high, 0);
		
		travelCostCategories.put(cheap, 0);
		travelCostCategories.put(standard, 0);
		travelCostCategories.put(expensive, 0);
		
		transportModeCategories.put(bus, 0);
		transportModeCategories.put(car, 0);
		transportModeCategories.put(train, 0);
		
		for (Person person : t.getPeople()) {
			
			String gender, cars, income, travelCost, transportMode;
			
			if (person.getGender() == Person.Gender.MALE) {
				gender = male;
			} else {
				gender = female;
			}
			
			if (person.getIncomeLevel() == Person.IncomeLevel.LOW) {
				income = low;
			} else if (person.getIncomeLevel() == Person.IncomeLevel.MEDIUM) {
				income = med;
			} else {
				income = high;
			}
			
			if (person.getNumCars() == 0) {
				cars = zero;
			} else if (person.getNumCars() == 1) {
				cars = one;
			} else {
				cars = two;
			}
			
			if (person.getTransportMode() == Person.TransportMode.BUS) {
				transportMode = bus;
			} else if (person.getTransportMode() == Person.TransportMode.CAR) {
				transportMode = car;
			} else {
				transportMode = train;
			}
			
			if (person.getTravelCost() == Person.TravelCost.CHEAP) {
				travelCost = cheap;
			} else if (person.getTravelCost() == Person.TravelCost.STANDARD) {
				travelCost = standard;
			} else {
				travelCost = expensive;
			}
			
			genderCategories.put(gender, genderCategories.get(gender) + 1);
			incomeCategories.put(income, incomeCategories.get(income) + 1);
			carsCategories.put(cars, carsCategories.get(cars) + 1);
			transportModeCategories.put(transportMode, transportModeCategories.get(transportMode) + 1);
			travelCostCategories.put(travelCost, travelCostCategories.get(travelCost) + 1);
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
