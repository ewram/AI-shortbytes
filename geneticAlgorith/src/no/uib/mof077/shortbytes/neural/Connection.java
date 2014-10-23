package no.uib.mof077.shortbytes.neural;

public class Connection {
	private double weight;
	private Node from;
	private Node to;
	
	public Connection(Node from, Node to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		from.getToConnections().add(this);
		to.getFromConnections().add(this);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}
}
