package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;

public class NeuralNetwork {
	
	private ArrayList<Node> inNodes = new ArrayList<>();
	private ArrayList<Node> hiddenNodes = new ArrayList<>();
	private ArrayList<Node> outNodes = new ArrayList<>();
	
	
	public NeuralNetwork() {
		
	}

	public void addNode(Node node, ArrayList<Node> list) {
		list.add(node);
	}
	
	public void removeNode(Node node, ArrayList<Node> list) {
		list.remove(node);
	}
}
