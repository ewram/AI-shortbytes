package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;
import java.util.Random;

public class XORNetwork {

	private NodeLayer inputLayer;
	@SuppressWarnings("unused")
	private NodeLayer hiddenLayer;
	private NodeLayer outputLayer;

	ArrayList<Connection> connections = new ArrayList<>();

	public XORNetwork() {

		// Create two input nodes with a 0 value and 1 threshold
		Node input1 = new Node(0, 0);
		Node input2 = new Node(0, 0);
		// Add input nodes to inputList
		ArrayList<Node> inputList = new ArrayList<>();
		inputList.add(input1);
		inputList.add(input2);

		// Create two hidden nodes with a 0 value and 1 threshold
		Node hidden1 = new Node(0, 0);
		Node hidden2 = new Node(0, 0);
		// Add hidden nodes to hiddenList
		ArrayList<Node> hiddenList = new ArrayList<>();
		hiddenList.add(hidden1);
		hiddenList.add(hidden2);

		// Create one output node with a 0 value and 1 threshold
		Node output1 = new Node(0, 0);
		// Add output node to outputList
		ArrayList<Node> outputList = new ArrayList<>();
		outputList.add(output1);

		// Create NodeLayers from input-, hidden- and outputLayer
		this.inputLayer = new NodeLayer(inputList, 0.2f);
		this.hiddenLayer = new NodeLayer(hiddenList, 0.5f);
		this.outputLayer = new NodeLayer(outputList, 0.2f);

		// Create Connections between nodes
		addConnections(new Connection(input1, hidden1, rand()), new Connection(
				input1, hidden2, rand()), new Connection(input2, hidden1,
				rand()), new Connection(input2, hidden2, rand()),
						new Connection(hidden1, output1, rand()), new Connection(
								hidden2, output1, rand()));
	}

	public static float rand() {
		Random rand = new Random();
		return rand.nextInt(20) - 10 + rand.nextFloat();
	}

	/**
	 * Helper method to add connections to the connection list
	 *
	 * @param link1
	 */
	private void addConnections(Connection... link1) {
		for (Connection c : link1) {
			this.connections.add(c);
		}
	}

	/**
	 * Sends signals through all connections based on if the from Node sends
	 * above its threshold the value is multiplied with the weight, and added to
	 * the to Node
	 */
	public void sendSignals() {
		int count = 1;

		for (Connection connection : this.connections) {
			System.out.println("sending through link " + count + ":"
					+ connection.getFrom().getValue() * connection.getWeight());
			connection.getTo().setValue(
					connection.getTo().getValue()
							+ (connection.getFrom().getValue() * connection
									.getWeight()));
			count++;
		}
	}

	/**
	 * prints the result of the XOR output
	 */
	public double[] printResult() {
		double[] results = new double[this.outputLayer.getNodeList().size()];
		int index = 0;
		for (Node n : this.outputLayer.getNodeList()) {
			results[index] = n.getValue();
			if (n.getValue() >= n.getThreshold()) {
				System.out.println("XOR: true");
			} else {
				System.out.println("XOR: false");
			}
			index++;
		}
		return results;
	}

	public void runBackPropagation(Node output, double error) {
		if (output.getFromConnections().size() < 1) {
			return;
		} else {
			for (Connection c : output.getFromConnections()) {
				// l * e * xi
				double l = c.getFrom().getLearningRate();
				double e = error;
				double xi = c.getWeight();
				System.out.println("l = " + l + ", e = " + e + ", xi = " + xi);
				double dwi = l * e * xi;
				System.out.println("dwi = " + dwi);
				c.setWeight(dwi);

				runBackPropagation(c.getFrom(), error);
			}
		}
	}

	public double calculateError(double output, double expected) {
		double error = output * (1 - output) * (expected - output);
		return Math.abs(error);
	}

	public static void main(String[] args) {
		XORNetwork network = new XORNetwork();

		for (int i = 0; i < 100; i++) {
			double totalError = network.calculateTotalError();
			System.out.println("Total error = " + totalError);
			network.runBackPropagation(
					network.outputLayer.getNodeList().get(0), totalError);
		}

	}

	private double calculateTotalError() {
		// TEST DATA
		// true
		double expected1 = 1.0d;
		double inputex11 = 1.0d;
		double inputex12 = 0;

		// true
		double expected2 = 1.0d;
		double inputex21 = 0.0d;
		double inputex22 = 1.0d;

		// false
		double expected3 = 0.0d;
		double inputex31 = 0.0d;
		double inputex32 = 0.0d;

		// false
		double expected4 = 0.0d;
		double inputex41 = 1.0d;
		double inputex42 = 1.0d;

		double totalError = 0;

		this.inputLayer.nodeList.get(0).setValue(inputex11);
		this.inputLayer.nodeList.get(1).setValue(inputex12);
		this.sendSignals();
		double result = this.printResult()[0];
		double error = this.calculateError(result, expected1);

		totalError += error;

		this.inputLayer.nodeList.get(0).setValue(inputex21);
		this.inputLayer.nodeList.get(1).setValue(inputex22);
		this.sendSignals();
		result = this.printResult()[0];
		error = this.calculateError(result, expected2);

		totalError += error;
		this.inputLayer.nodeList.get(0).setValue(inputex31);
		this.inputLayer.nodeList.get(1).setValue(inputex32);
		this.sendSignals();
		result = this.printResult()[0];
		error = this.calculateError(result, expected3);

		totalError += error;
		this.inputLayer.nodeList.get(0).setValue(inputex41);
		this.inputLayer.nodeList.get(1).setValue(inputex42);
		this.sendSignals();
		result = this.printResult()[0];
		error = this.calculateError(result, expected4);

		totalError += error;

		return totalError;
	}
}
