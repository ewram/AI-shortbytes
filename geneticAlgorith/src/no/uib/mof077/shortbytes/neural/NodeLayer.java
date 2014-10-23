package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;

public class NodeLayer {
	public ArrayList<Node> nodeList = new ArrayList<>();
	public double learningRate;
	
	public NodeLayer(ArrayList<Node> nodeList, double learningRate) {
		this.nodeList = nodeList;
		this.learningRate = learningRate;
		for (Node node : nodeList) {
			node.setLearningRate(learningRate);
		}
	}
	
	public ArrayList<Node> getNodeList() {
		return this.nodeList;
	}
}