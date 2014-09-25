package no.uib.mof077.shortbytes.neural;

public class Node {
	private boolean send;
	private int value;
	
	public Node(boolean send, int value) {
		this.setSend(send);;
		this.setValue(value);
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
}
