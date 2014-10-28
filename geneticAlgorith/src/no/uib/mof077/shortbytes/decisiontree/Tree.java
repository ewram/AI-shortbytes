package no.uib.mof077.shortbytes.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
	private List<Person> people;
	private Node classCategory;
	
	public Map<String, Map<String, Integer>> categories = new HashMap<>();

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
	
	public List<Person> getPeopleWithCategoryValue(String category, String categoryValue) {
		List<Person> peopleWithCategoryValue = new ArrayList<>();
		for (Person person : this.people) {
			if (person.getProperties().get(category) != null &&
					person.getProperties().get(category).equals(categoryValue)) {
				peopleWithCategoryValue.add(person);
			}
		}
		return peopleWithCategoryValue;
	}
	
	public static List<Person> getPeopleWithCategoryValue(List<Person> people, String category, String categoryValue) {
		List<Person> peopleWithCategoryValue = new ArrayList<>();
		for (Person person : people) {
			if (person.getProperties().get(category) != null &&
					person.getProperties().get(category).equals(categoryValue)) {
				peopleWithCategoryValue.add(person);
			}
		}
		return peopleWithCategoryValue;
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
	}

	public static Node createNode(int divider, String category, Map<String, Integer> categories) {
		double entropy = Tree.calculateEntropy(divider, categories);
		Map<String, Node> children = new HashMap<>();
		for (String categoryValue : Person.getCategoryValues(category)) {
			children.put(categoryValue, null);
		}
		return new Node(null, children, category, entropy, 0, categories.size());
	}
	
	public static Map<String, Integer> getCategoryMap(Node node) {
		Map<String, Integer> categoryMap = new HashMap<>();
		for (String categoryValue : node.getChildren().keySet()) {
			categoryMap.put(categoryValue, 0);
		}
		for (Person person : node.getPeople()) {
			for (String categoryValue : node.getChildren().keySet()) {
				if (person.getProperties().get(node.getCategory()).equals(categoryValue)) {
					categoryMap.put(categoryValue, categoryMap.get(categoryValue)+1);
				}
			}
			
		}
		return categoryMap;
	}
	
	public double calculateCategoryValueInformationGain(double entropy, String category1, String category1Value, String category2) {

		// Get all possible values for the category, e.g. TransportMode => {"Bus", "Car", "Train"}
		String[] cat1Values = Person.getCategoryValues(category1.toLowerCase());
		String[] cat2Values = Person.getCategoryValues(category2.toLowerCase());
		
		// Initialize map to keep track of how many people have each value, e.g. "Bus" => 4, "Car" => 3, "Train" => 3
		Map<String, List<Integer>> cat2ValueCombo = new HashMap<>(); // NEW
		for (int i = 0; i < cat2Values.length; i++) {
			cat2ValueCombo.put(cat2Values[i].toLowerCase(), new ArrayList<Integer>()); // NEW
			
			List<Integer> arrayOfCoocurrances = new ArrayList<>();
			for (int j = 0; j < cat1Values.length; j++) {
				arrayOfCoocurrances.add(0);
			}
			cat2ValueCombo.put(cat2Values[i].toLowerCase(), arrayOfCoocurrances);
		}

		// For each person, count +1 for each category value
		List<Person> relevantPeople = getPeopleWithCategoryValue(category1, category1Value);
		for (Person person : relevantPeople) {
			String category2Value = person.getProperties().get(category2.toLowerCase()).toLowerCase();
			
			for (int i = 0; i < cat1Values.length; i++) {
				if (cat1Values[i].toLowerCase().equals(category1Value)) {
					List<Integer> values = cat2ValueCombo.get(category2Value);
					int count = values.remove(i);
					values.add(i, count+1);
					cat2ValueCombo.put(category2Value, values);
				}
			}
		}
		
		double infoGain = calculateCatValueEntropy(entropy, relevantPeople.size(), cat2ValueCombo);

		return infoGain;
	}

	public double calculateCategoryInformationGain(double entropy, String keyCat1, String keyCat2) {

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
		
		double infoGain = calculateCatValueEntropy(entropy, people.size(), cat2ValueCombo);

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

	public static double calculateEntropy(int divider, Map<String, Integer> categoryValues) {
		double entropy = 0;
		for (String categorValue : categoryValues.keySet()) {
			double categoryValueCount = categoryValues.get(categorValue);
			entropy += -(categoryValueCount / divider) * (Math.log(categoryValueCount/divider) / Math.log(2));
		}
		return entropy;
	}

	public static void main(String[] args) {
		Tree tree = new Tree();

		tree.init();

		List<Node> nodeCandidates = new ArrayList<>();
		for (String category : tree.categories.keySet()) {
			Node nodeCandidate = Tree.createNode(tree.getPeople().size(), category, tree.categories.get(category));
			System.out.println("Entropy(" + nodeCandidate.getCategory() + ")= " + nodeCandidate.getEntropy());
			nodeCandidates.add(nodeCandidate);
		}
		
		Node totalEntropy = Tree.selectNodeWithHighestEntropy(nodeCandidates);
		System.out.println("Entropy(S)= " + totalEntropy.getEntropy());
		
		nodeCandidates.remove(totalEntropy);
		
		// This is where the magic starts
		Tree.createTree(tree, totalEntropy.getEntropy(), totalEntropy, nodeCandidates);
		// This is where the magic ends
		
		
	}
	
	public static double calculateEntropy(double divider, double[] values) {
		double entropy = 0;
		for (double d : values) {
			entropy += -(d / divider) * (Math.log(d/divider) / Math.log(2));
		}
		return entropy;
	}
	
	// TODO Something recursive to create a friggin' tree, yo
	public static void createTree(Tree tree, double totalEntropy, Node parentNode, List<Node> nodes) {
		
		if (tree.getRootNode() == null) {// IF no rootNode in tree: select one
			for (Node node : nodes) {
				double nodeInfoGain = tree.calculateCategoryInformationGain(
						totalEntropy,
						parentNode.getCategory(),
						node.getCategory());
				node.setInfoGain(nodeInfoGain);
				System.out.println("<" + node.getCategory() + "> information gain: " + node.getInfoGain());
			}
			
			Node rootNode = Tree.selectNodeWithHighestInfoGain(nodes);
			nodes.remove(rootNode);
			tree.setRootNode(rootNode);
			System.out.println("Root node: <" + tree.getRootNode().getCategory() + ">, info gain: " + tree.getRootNode().getInfoGain());
			Tree.createTree(tree, totalEntropy, rootNode, nodes);
		} else { // ELSE for each categoryValue of node, create a new branch by selecting one of the remaining nodes
			if (nodes.isEmpty()) {
				return;
			} else {
				for (String categoryValue : parentNode.getChildren().keySet()) {
					List<Node> nodeBranchNodes = Tree.duplicateListOfNodes(nodes);
					for (Node node : nodeBranchNodes) {
						List<Person> people = Tree.getPeopleWithCategoryValue(parentNode.getPeople(), parentNode.getCategory(), categoryValue);
						node.setPeople(people);
						double entropy = calculateEntropy(people.size(), getCategoryMap(node));
						node.setEntropy(entropy);
						double nodeInfoGain = tree.calculateCategoryValueInformationGain(
								node.getEntropy(),
								parentNode.getCategory(),
								categoryValue,
								node.getCategory());
						node.setInfoGain(nodeInfoGain);
						System.out.println("<" + node.getCategory() + "> information gain on " + parentNode.getCategory() + "=" + categoryValue + ": "+ node.getInfoGain());
					}
					
					Node nodeBranch = Tree.selectNodeWithHighestInfoGain(nodes);
					nodeBranchNodes.remove(nodeBranch);
					parentNode.getChildren().put(categoryValue, nodeBranch);
					System.out.println("Branch node: <" + nodeBranch.getCategory() + ">, on " + parentNode.getCategory() + "=" + categoryValue + ". Info gain: " + nodeBranch.getInfoGain());
					Tree.createTree(tree, nodeBranch.getEntropy(), nodeBranch, nodeBranchNodes);
				}
			}
		}
	}
	
	public static List<Node> duplicateListOfNodes(List<Node> nodes) {
		List<Node> newList = new ArrayList<>();
		for (Node node : nodes) {
			newList.add(new Node(node.getParent(), node.getChildren(), node.getCategory(), node.getEntropy(), node.getInfoGain(), node.getTotal()));
		}
		return newList;
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
	
	public static Node selectNodeWithHighestInfoGain(List<Node> nodes) {
		Node max = null;
		for (Node node : nodes) {
			if (max == null) {
				max = node;
			} else if (node.getInfoGain() > max.getInfoGain()){
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
		return classCategory;
	}

	public void setRootNode(Node rootNode) {
		this.classCategory = rootNode;
	}
}
