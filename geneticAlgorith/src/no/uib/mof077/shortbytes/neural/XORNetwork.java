package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;

public class XORNetwork {

	private NodeLayer inputLayer;
	private NodeLayer hiddenLayer;
	private NodeLayer outputLayer;

	ArrayList<Connection> connections = new ArrayList<>();

	public XORNetwork() {
		Node input1 = new Node(0, 1);
		Node input2 = new Node(0, 1);
		ArrayList<Node> inputList = new ArrayList<>();
		inputList.add(input1);
		inputList.add(input2);

		Node hidden1 = new Node(0, 1);
		Node hidden2 = new Node(0, 1);
		ArrayList<Node> hiddenList = new ArrayList<>();
		hiddenList.add(hidden1);
		hiddenList.add(hidden2);

		Node output1 = new Node(0, 1);
		ArrayList<Node> outputList = new ArrayList<>();
		outputList.add(output1);

		inputLayer = new NodeLayer(inputList);
		hiddenLayer = new NodeLayer(hiddenList);
		outputLayer = new NodeLayer(outputList);

		addConnections(
				new Connection(input1, hidden1, 1),
				new Connection(input1, hidden2, -1),
				new Connection(input2, hidden1, -1),
				new Connection(input2, hidden2, 1),
				new Connection(hidden1, output1, 1),
				new Connection(hidden2, output1, 1)
				);
	}

	public void addConnections(Connection... link1) {
		for(Connection c : link1) {
			connections.add(c);
		}
	}

	public void sendSignals() {
		int count = 1;

		for(Connection connection : connections) {
			if(connection.getFrom().getValue() >= connection.getFrom().getThreshold()) {
				System.out.println("sending through link " + count +":" + connection.getFrom().getValue()*connection.getWeight());
				connection.getTo().setValue(connection.getTo().getValue() + (connection.getFrom().getValue() * connection.getWeight()));
			}
			count++;
		}
	}

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

		network.inputLayer.nodeList.get(0).setValue(0);
		network.inputLayer.nodeList.get(1).setValue(0);

		network.sendSignals();
		network.printResult();
	}
}
