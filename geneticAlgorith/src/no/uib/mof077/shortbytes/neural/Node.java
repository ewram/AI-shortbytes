package no.uib.mof077.shortbytes.neural;

public class Node {
	private boolean send;
	private int value;
	private float weight;
	
	public Node(boolean send, int value, float weight) {
		this.setSend(send);;
		this.setValue(value);
		this.setWeight(weight);
		
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}
