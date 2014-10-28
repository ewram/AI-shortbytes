package no.uib.mof077.shortbytes.decisiontree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
	private Node parent;
	private Map<String, Node> children;
	private List<Person> people;
	private String category;
	private double entropy;
	private double infoGain;
	private double total;
	
	public Node(Node parent, Map<String, Node> children, String category, double entropy, double infoGain, double total) {
		this.parent = parent;
		this.children = children;
		this.people = new ArrayList<>();
		this.category = category;
		this.entropy = entropy;
		this.infoGain = infoGain;
		this.total = total;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Map<String, Node> getChildren() {
		return children;
	}

	public void setChildren(Map<String, Node> children) {
		this.children = children;
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public double getInfoGain() {
		return infoGain;
	}

	public void setInfoGain(double infoGain) {
		this.infoGain = infoGain;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
