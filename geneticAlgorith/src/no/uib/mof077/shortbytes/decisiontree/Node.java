package no.uib.mof077.shortbytes.decisiontree;

import java.util.Map;

public class Node {
	private Node parent;
	private Map<String, Node> children;
	private String category;
	private double entropy;
	private double total;
	
	public Node(Node parent, Map<String, Node> children, String category, double entropy, double total) {
		this.parent = parent;
		this.children = children;
		this.category = category;
		this.entropy = entropy;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
