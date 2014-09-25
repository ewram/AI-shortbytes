package no.uib.mof077.shortbytes.genetics;

public class Candidate {
	private String expression;
	private double fitness;
	
	public Candidate(String exp) {
		this.setExpression(exp);
		this.setFitness(-99999999999999.9);
	}
	
	public Candidate() {
		this.setExpression("9*9*9*9");
		this.setFitness(-99999999999999.9);
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
}
