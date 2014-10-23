package no.uib.mof077.shortbytes.neural;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private float value;
	private float threshold;
	private float learningRate;
	private List<Connection> fromConnections;
	private List<Connection> toConnections;
	
	public Node(int value, int threshold) {
		this.setValue(value);
		this.setThreshold(threshold);
		this.fromConnections = new ArrayList<>();
		this.toConnections = new ArrayList<>();
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public float getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(float learningRate) {
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