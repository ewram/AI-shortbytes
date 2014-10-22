package no.uib.mof077.shortbytes.neural;
public class Node {
	private int value;
	private int threshold;
	
	public Node(int value, int threshold) {
		this.setValue(value);
		this.setThreshold(threshold);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}