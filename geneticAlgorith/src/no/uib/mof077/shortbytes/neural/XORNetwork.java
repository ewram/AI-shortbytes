package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;

public class XORNetwork {

	private NodeLayer inputLayer;
	private NodeLayer hiddenLayer;
	private NodeLayer outputLayer;

	ArrayList<Connection> connections = new ArrayList<>();

	public XORNetwork() {
		
		// Create two input nodes with a 0 value and 1 threshold
		Node input1 = new Node(0, 1);
		Node input2 = new Node(0, 1);
		// Add input nodes to inputList
		ArrayList<Node> inputList = new ArrayList<>();
		inputList.add(input1);
		inputList.add(input2);

		// Create two hidden nodes with a 0 value and 1 threshold
		Node hidden1 = new Node(0, 1);
		Node hidden2 = new Node(0, 1);
		// Add hidden nodes to hiddenList
		ArrayList<Node> hiddenList = new ArrayList<>();
		hiddenList.add(hidden1);
		hiddenList.add(hidden2);

		// Create one output node with a 0 value and 1 threshold
		Node output1 = new Node(0, 1);
		// Add output node to outputList
		ArrayList<Node> outputList = new ArrayList<>();
		outputList.add(output1);

		// Create NodeLayers from input-, hidden- and outputLayer
		this.inputLayer = new NodeLayer(inputList);
		this.hiddenLayer = new NodeLayer(hiddenList);
		this.outputLayer = new NodeLayer(outputList);

		// Create Connections between nodes
		addConnections(
				new Connection(input1, hidden1, 1),
				new Connection(input1, hidden2, -1),
				new Connection(input2, hidden1, -1),
				new Connection(input2, hidden2, 1),
				new Connection(hidden1, output1, 1),
				new Connection(hidden2, output1, 1)
				);
	}

	/**
	 * Helper method to add connections to the connection list
	 * @param link1
	 */
	private void addConnections(Connection... link1) {
		for(Connection c : link1) {
			this.connections.add(c);
		}
	}

	/**
	 * Sends signals through all connections based on if the from Node sends above its threshold
	 * the value is multiplied with the weight, and added to the to Node
	 */
	public void sendSignals() {
		int count = 1;

		for(Connection connection : this.connections) {
			if(connection.getFrom().getValue() >= connection.getFrom().getThreshold()) {
				System.out.println("sending through link " + count +":" + connection.getFrom().getValue()*connection.getWeight());
				connection.getTo().setValue(connection.getTo().getValue() + (connection.getFrom().getValue() * connection.getWeight()));
			}
			count++;
		}
	}

	/**
	 * prints the result of the XOR output
	 */
	public void printResult() {
		for(Node n : outputLayer.getNodeList()) {
			if(n.getValue() >= n.getThreshold()) {
				System.out.println("XOR: true");
			} else {
				System.out.println("XOR: false");
			}
		}
	}

	public static void main(String[] args) {
		XORNetwork network = new XORNetwork();

		network.inputLayer.nodeList.get(0).setValue(1);
		network.inputLayer.nodeList.get(1).setValue(0);

		network.sendSignals();
		network.printResult();
	}
}
