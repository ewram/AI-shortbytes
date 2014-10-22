package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;

public class NodeLayer {
	public ArrayList<Node> nodeList = new ArrayList<>();
	
	public NodeLayer(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	public ArrayList<Node> getNodeList() {
		return this.nodeList;
	}
}