package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private double value;
	private double threshold;
	private double learningRate;
	private List<Connection> fromConnections;
	private List<Connection> toConnections;
	
	public Node(int value, int threshold) {
		this.setValue(value);
		this.setThreshold(threshold);
		this.fromConnections = new ArrayList<>();
		this.toConnections = new ArrayList<>();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public List<Connection> getFromConnections() {
		return fromConnections;
	}

	public void setFromConnections(List<Connection> fromConnections) {
		this.fromConnections = fromConnections;
	}

	public List<Connection> getToConnections() {
		return toConnections;
	}

	public void setToConnections(List<Connection> toConnections) {
		this.toConnections = toConnections;
	}
	
}