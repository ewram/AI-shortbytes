package no.uib.mof077.shortbytes.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
	private List<Person> people;
	private Node rootNode;

	public Map<String, Integer> genderCategories = new HashMap<>();
	public Map<String, Integer> carsCategories = new HashMap<>();
	public Map<String, Integer> incomeCategories = new HashMap<>();
	public Map<String, Integer> travelCostCategories = new HashMap<>();
	public Map<String, Integer> transportModeCategories = new HashMap<>();

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
		this.rootNode = new Node(null, null, null, 0, 0);
	}

	public void addPeople(Person... persons) {
		for (Person person : persons) {
			people.add(person);
		}
	}

	public void init() {
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

		for (Person person : people) {

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
	}

	public static Node createNode(int divider, String category, Map<String, Integer> categories) {
		double entropy = Tree.calulateEntropy(divider, categories);
		return new Node(null, null, category, entropy, categories.size());
	}

	public double caclulateInformationGainCategoryValue(double entropy, String keyCat1, String keyCat2) {
		double infoGain = entropy;
//		double keyCat1Range = Person.countCategoryValues(keyCat1);
//		double keyCat2Range = Person.countCategoryValues(keyCat2);
//		double overlap = 0;

		// Get all possible values for the category, e.g. TransportMode => {"Bus", "Car", "Train"}
		String[] categoryValues = Person.getCategoryValues(keyCat2.toLowerCase());
		
		// Initialize map to keep track of how many people have each value, e.g. "Bus" => 4, "Car" => 3, "Train" => 3
		Map<String, Integer> catValueCount = new HashMap<>();
		for (int i = 0; i < categoryValues.length; i++) {
			catValueCount.put(categoryValues[i].toLowerCase(), 0);
		}

		// For each person, count +1 for each category value
		for (Person person : people) {
			String categoryValue = person.getProperties().get(keyCat2.toLowerCase()).toLowerCase();
			catValueCount.put(categoryValue, catValueCount.get(categoryValue)+1);
		}
		
		for (String categoryValue : catValueCount.keySet()) {
			double valueCount = catValueCount.get(categoryValue);
			infoGain += -(valueCount/people.size()) * Tree.calulateEntropy(people.size(), catValueCount);
		}

		return infoGain;
	}



	public static double calulateEntropy(int divider, Map<String, Integer> categories) {
		double entropy = 0;
		for (String catValue : categories.keySet()) {
			double categoryValues = categories.get(catValue);
			entropy += -(categoryValues / divider) * (Math.log(categoryValues/divider) / Math.log(2));
		}
		return entropy;
	}

	public static void main(String[] args) {
		Tree t = new Tree();

		t.init();

		Node genderEntropy = Tree.createNode(t.getPeople().size(), "gender", t.genderCategories);
		Node carsEntropy = Tree.createNode(t.getPeople().size(), "cars", t.carsCategories);
		Node travelCostEntropy = Tree.createNode(t.getPeople().size(), "travelCost", t.travelCostCategories);
		Node transportEntropy = Tree.createNode(t.getPeople().size(), "transportMode", t.transportModeCategories);
		Node incomeEntropy = Tree.createNode(t.getPeople().size(), "income", t.incomeCategories);

		System.out.println("<gender> entropy: " + genderEntropy.getEntropy());
		System.out.println("<cars> entropy: " + carsEntropy.getEntropy());
		System.out.println("<travel> entropy: " + travelCostEntropy.getEntropy());
		System.out.println("<income> entropy: " + incomeEntropy.getEntropy());
		System.out.println("<transport> entropy: " + transportEntropy.getEntropy());

		Node maxNode = selectNodeWithHighestEntropy(genderEntropy, carsEntropy, travelCostEntropy, transportEntropy, incomeEntropy);
		
		System.out.println("Max: <" + maxNode.getCategory().toLowerCase() + "> with entropy " + maxNode.getEntropy());

		double genderInfoGain = t.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), genderEntropy.getCategory());
		double carsInfoGain = t.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), carsEntropy.getCategory());
		double travelCostInfoGain = t.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), travelCostEntropy.getCategory());
		double incomeInfoGain = t.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), incomeEntropy.getCategory());
		
		System.out.println("<gender> info gain: " + genderInfoGain);
		System.out.println("<cars> info gain: " + carsInfoGain);
		System.out.println("<travel> info gain: " + travelCostInfoGain);
		System.out.println("<income> info gain: " + incomeInfoGain);
		
		genderEntropy.setEntropy(genderInfoGain);
		carsEntropy.setEntropy(carsInfoGain);
		travelCostEntropy.setEntropy(travelCostInfoGain);
		incomeEntropy.setEntropy(incomeInfoGain);
		
		Node rootNode = selectNodeWithHighestEntropy(genderEntropy, carsEntropy, travelCostEntropy, incomeEntropy);
		t.setRootNode(rootNode);
		
		System.out.println("Root node: <" + t.getRootNode().getCategory() + ">, info gain: " + t.getRootNode().getEntropy());
	}

	public static Node selectNodeWithHighestEntropy(Node... nodes) {
		Node max = null;
		for (Node node : nodes) {
			if (max == null) {
				max = node;
			} else if (node.getEntropy() > max.getEntropy()){
				max = node;
			}
		}
		return max;
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}
}
