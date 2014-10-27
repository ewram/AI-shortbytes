package no.uib.mof077.shortbytes.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tree {
	private List<Person> people;
	private Node rootNode;
	
	public Map<String, Map<String, Integer>> categories = new HashMap<>();

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
//		this.rootNode = new Node(null, null, null, 0, 0, 0);
	}

	public void addPeople(Person... persons) {
		for (Person person : persons) {
			people.add(person);
		}
	}

	public void init() {
		for (String category : Person.getCategories()) {
			categories.put(category, new HashMap<String, Integer>());
			for (String categoryValue : Person.getCategoryValues(category)) {
				categories.get(category).put(categoryValue, 0);
			}
		}
		
		for (Person person : people) {
			for (String category : Person.getCategories()) {
				String pCategoryValue = person.getProperties().get(category);
				Map<String, Integer> categoryValuesCount = categories.get(category);
				int categoryValueCount = categoryValuesCount.get(pCategoryValue);
				categories.get(category).put(pCategoryValue, categoryValueCount+1);
			}
		}
		
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
		return new Node(null, null, category, entropy, 0, categories.size());
	}

	public double caclulateInformationGainCategoryValue(double entropy, String keyCat1, String keyCat2) {
		double infoGain = 0;

		// Get all possible values for the category, e.g. TransportMode => {"Bus", "Car", "Train"}
		String[] cat1Values = Person.getCategoryValues(keyCat1.toLowerCase());
		String[] cat2Values = Person.getCategoryValues(keyCat2.toLowerCase());
		
		// Initialize map to keep track of how many people have each value, e.g. "Bus" => 4, "Car" => 3, "Train" => 3
		Map<String, List<Integer>> cat2ValueCombo = new HashMap<>(); // NEW
		for (int i = 0; i < cat2Values.length; i++) {
			cat2ValueCombo.put(cat2Values[i].toLowerCase(), new ArrayList<Integer>()); // NEW
			
			List<Integer> intsYo = new ArrayList<>();
			for (int j = 0; j < cat1Values.length; j++) {
				intsYo.add(0);
			}
			cat2ValueCombo.put(cat2Values[i].toLowerCase(), intsYo);
		}

		// For each person, count +1 for each category value
		for (Person person : people) {
			String category1Value = person.getProperties().get(keyCat1.toLowerCase()).toLowerCase();
			String category2Value = person.getProperties().get(keyCat2.toLowerCase()).toLowerCase();
			
			for (int i = 0; i < cat1Values.length; i++) {
				if (cat1Values[i].toLowerCase().equals(category1Value)) {
					List<Integer> values = cat2ValueCombo.get(category2Value);
					int count = values.remove(i);
					values.add(i, count+1);
					cat2ValueCombo.put(category2Value, values);
				}
			}
		}
		
		infoGain = calculateCatValueEntropy(entropy, people.size(), cat2ValueCombo);
		
//		for (String categoryValue : catValueCount.keySet()) {
//			double valueCount = catValueCount.get(categoryValue);
//			infoGain += -(valueCount/people.size()) * Tree.calulateEntropy(cat1Values.length, catValueCount);
//		}

		return infoGain;
	}
	
	public static double calculateCatValueEntropy(double totEntropy, int divider, Map<String, List<Integer>> catValueCombo) {
		double entropy = totEntropy;
		for (String key : catValueCombo.keySet()) {
			double catValueEntropy = 0;
			double count = 0;
			List<Integer> list = catValueCombo.get(key);
			for (Integer integer : list) {
				count += integer;
			}
			for (Integer integer : list) {
				if (integer  != 0 || integer != -0) {
					catValueEntropy += -(integer/count) * Math.log(integer/count) / Math.log(2);
				}
			}
			entropy += -(count/divider) * catValueEntropy;
		}
		return entropy;
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
		Tree tree = new Tree();

		tree.init();

		/*########## NEW SHIT ##########*/
		List<Node> nodeCandidates = new ArrayList<>();
		for (String category : tree.categories.keySet()) {
			Node nodeCandidate = Tree.createNode(tree.getPeople().size(), category, tree.categories.get(category));
			System.out.println("Entropy(" + nodeCandidate.getCategory() + ")= " + nodeCandidate.getEntropy());
			nodeCandidates.add(nodeCandidate);
		}
		
		Node totalEntropy = Tree.selectNodeWithHighestEntropy(nodeCandidates);
		System.out.println("Entropy(S)= " + totalEntropy.getEntropy());
		
		nodeCandidates.remove(totalEntropy);
		
		for (Node node : nodeCandidates) {
			double nodeInfoGain = tree.caclulateInformationGainCategoryValue(
					totalEntropy.getEntropy(),
					totalEntropy.getCategory(),
					node.getCategory());
			node.setInfoGain(nodeInfoGain);
			System.out.println("<" + node.getCategory() + "> information gain: " + node.getInfoGain());
		}
		
		Node rootNode = Tree.selectNodeWithHighestEntropy(nodeCandidates);
		tree.setRootNode(rootNode);
		
		/*########## OLD SHIT ##########*/
//		Node genderEntropy = Tree.createNode(tree.getPeople().size(), "gender", tree.genderCategories);
//		Node carsEntropy = Tree.createNode(tree.getPeople().size(), "cars", tree.carsCategories);
//		Node travelCostEntropy = Tree.createNode(tree.getPeople().size(), "travelCost", tree.travelCostCategories);
//		Node transportEntropy = Tree.createNode(tree.getPeople().size(), "transportMode", tree.transportModeCategories);
//		Node incomeEntropy = Tree.createNode(tree.getPeople().size(), "income", tree.incomeCategories);
//
//		System.out.println("<gender> entropy: " + genderEntropy.getEntropy());
//		System.out.println("<cars> entropy: " + carsEntropy.getEntropy());
//		System.out.println("<travel> entropy: " + travelCostEntropy.getEntropy());
//		System.out.println("<income> entropy: " + incomeEntropy.getEntropy());
//		System.out.println("<transport> entropy: " + transportEntropy.getEntropy());
//
//		Node maxNode = selectNodeWithHighestEntropy(genderEntropy, carsEntropy, travelCostEntropy, transportEntropy, incomeEntropy);
//		
//		System.out.println("Max: <" + maxNode.getCategory().toLowerCase() + "> with entropy " + maxNode.getEntropy());
//
//		double genderInfoGain = tree.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), genderEntropy.getCategory());
//		double carsInfoGain = tree.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), carsEntropy.getCategory());
//		double travelCostInfoGain = tree.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), travelCostEntropy.getCategory());
//		double incomeInfoGain = tree.caclulateInformationGainCategoryValue(maxNode.getEntropy(), maxNode.getCategory(), incomeEntropy.getCategory());
//		
//		System.out.println("<gender> info gain: " + genderInfoGain);
//		System.out.println("<cars> info gain: " + carsInfoGain);
//		System.out.println("<travel> info gain: " + travelCostInfoGain);
//		System.out.println("<income> info gain: " + incomeInfoGain);
//		
//		genderEntropy.setEntropy(genderInfoGain);
//		carsEntropy.setEntropy(carsInfoGain);
//		travelCostEntropy.setEntropy(travelCostInfoGain);
//		incomeEntropy.setEntropy(incomeInfoGain);
//		
//		Node rootNode = selectNodeWithHighestEntropy(genderEntropy, carsEntropy, travelCostEntropy, incomeEntropy);
//		tree.setRootNode(rootNode);
		
		System.out.println("Root node: <" + tree.getRootNode().getCategory() + ">, info gain: " + tree.getRootNode().getInfoGain());
	}

	public static Node selectNodeWithHighestEntropy(Node... nodesArgs) {
		List<Node> nodes = new ArrayList<>();
		for (Node node : nodesArgs) {
			nodes.add(node);
		}
		return selectNodeWithHighestEntropy(nodes);
	}
	
	public static Node selectNodeWithHighestEntropy(List<Node> nodes) {
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
