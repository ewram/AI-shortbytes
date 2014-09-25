package no.uib.mof077.shortbytes.genetics;

import java.util.Random;

import net.objecthunter.exp4j.ExpressionBuilder;

public class Main {
	
	
	public static double goal = 75.5;
	private String[] validNumbers = {"1","2","3","4","5","6","7","8","9","-","+","/","*"};
	
	private Candidate[] parents = new Candidate[4];
	private Candidate[] children = new Candidate[4];
	private Random ran = new Random();
	
	private double crossover = 0.7;
	private double mutation = 0.01;
	
	public Main() {
		initParents();
		}

	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}
	
	public void run() {
		calculateResult(parents[0]);
		calculateResult(parents[1]);
		calculateResult(parents[2]);
		calculateResult(parents[3]);
		
		System.out.println("PARENTS");
		for(Candidate c : parents) {
			System.out.println("The expression: " + c.getExpression());
			System.out.println("The fitness is " + c.getFitness());
			System.out.println();
		}
		Candidate parent1 = new Candidate("");
        Candidate parent2 = new Candidate("");
		for (int i = 0; i < parents.length; i++) {
            if(parents[i].getFitness() > parent1.getFitness()) {
                parent1 = parents[i];
            } else if (parents[i].getFitness() > parent2.getFitness()) {
                parent2 = parents[i];
            }
        }
		children[0] = breed(parent1, parent2);
		children[1] = breed(parent1, parent2);
		children[2] = breed(parent1, parent2);
		children[3] = breed(parent1, parent2);
		System.out.println("CHILDREN");
		for(Candidate c : children) {
			System.out.println("The expression: " + c.getExpression());
			System.out.println("The fitness is " + c.getFitness());
			System.out.println();
		}
	}

	private Candidate breed(Candidate parent1, Candidate parent2) {
		String exp1 = parent1.getExpression();
		String exp2 = parent2.getExpression();
		StringBuilder sb = new StringBuilder();
		String result = "";
		for (int i = 0; i <= exp1.length() - 1; i++) {
			double cross = ran.nextDouble();
			if(cross > crossover) {
				sb.append(exp2.charAt(i));
			} else {
				sb.append(exp1.charAt(i));
			}
			double mutate = ran.nextDouble();
			if(mutate > mutation) {
				//do nothing
			} else {
				sb.setCharAt(i, validNumbers[ran.nextInt(validNumbers.length)].charAt(0));
			}
		}
		result = sb.toString();
		Candidate res = new Candidate(result);
		calculateResult(res);
		return res;
	}

	public void calculateResult(Candidate exp) {
      
      double result = 0;
      
      try {
          result = new ExpressionBuilder(exp.getExpression())
          .build()
          .evaluate();
      } catch (IllegalArgumentException e) {
          result = -99999999999999.9;
      }
      finally{
      if(result > goal || result < goal) {
    	  double diff = Math.abs(result - Main.goal);
    	  exp.setFitness(-diff);
      } else {
    	  System.out.println("Congratulation mofo, you did it.");
      }
      }
  }
	private void initParents() {
		// TODO Auto-generated method stub
		parents[0] =  new Candidate(validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)]) ;
		parents[1] =  new Candidate(validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)]) ;
		parents[2] =  new Candidate(validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)]) ;
		parents[3] =  new Candidate(validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)] + 
				validNumbers[ran.nextInt(validNumbers.length)]) ;
	}
}


