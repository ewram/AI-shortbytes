package com.src.main;

import java.util.Random;

import net.objecthunter.exp4j.ExpressionBuilder;

public class GeneticAlgorithm {

    String[] cifers = new String[] {"1","2","3","4","5","6","7","8","9"};
    String[] operators = new String[] {"-","+","*","/"};

    Random ran = new Random();

    private final double goal = 75.5d;
    private final double crossover = 0.7d;
    private final double mutation = 0.10d;
    private int times = 0;
    private boolean running = true;

    Candidate[] bestParents = {new Candidate(), new Candidate(), new Candidate(), new Candidate(), new Candidate(), new Candidate(), new Candidate(), new Candidate()};


    public GeneticAlgorithm() {

    }

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm();

        Candidate[] parents = new Candidate[50];
        
        ga.setupParents(parents);
        ga.darwin(parents);
    }

    public void setupParents(Candidate[] parents) {
        for (int i = 0; i < parents.length; i++) {
        	StringBuilder expression = new StringBuilder();
        	expression.append(cifers[ran.nextInt(cifers.length)]);
        	expression.append(operators[ran.nextInt(operators.length)]);
        	expression.append(cifers[ran.nextInt(cifers.length)]);
        	expression.append(operators[ran.nextInt(operators.length)]);
        	expression.append(cifers[ran.nextInt(cifers.length)]);
        	expression.append(operators[ran.nextInt(operators.length)]);
        	expression.append(cifers[ran.nextInt(cifers.length)]);
        	
        	
            parents[i] = new Candidate(expression.toString());
            evaluate(parents[i]);
        }
    }

    public void darwin(Candidate[] parents) {
    	while (running) {
            //insertion sort
            insertionSort(parents);

            for(int i = 0; i < parents.length-5; i++) {
            	System.out.println(i+" candidate. Fitness: "+ parents[i].getFitness() + " Expression: " + parents[i].getExpression());
            }
            
            Candidate[] children = new Candidate[14];
            Candidate[] breedResult = breed(parents[0], parents[1]);
            children[0] = breedResult[0];
            children[1] = breedResult[1];
            breedResult = breed(parents[0], parents[2]);
            children[2] = breedResult[0];
            children[3] = breedResult[1];
            breedResult = breed(parents[0], parents[3]);
            children[4] = breedResult[0];
            children[5] = breedResult[1];
            breedResult = breed(parents[0], parents[4]);
            children[6] = breedResult[0];
            children[7] = breedResult[1];
            breedResult = breed(parents[0], parents[5]);
            children[8] = breedResult[0];
            children[9] = breedResult[1];
            breedResult = breed(parents[0], parents[6]);
            children[10] = breedResult[0];
            children[11] = breedResult[1];
            breedResult = breed(parents[0], parents[7]);
            children[12] = breedResult[0];
            children[13] = breedResult[1];
           
            System.out.println("Iteration: " + times);
            System.out.println(" ");
            
            parents = new Candidate[children.length];
            parents = children;
            
            times++;
            
        }
    }
    
    public static void insertionSort(Candidate[] persons) {
        int in, out;

        for (out = 1; out < persons.length; out++) {
          Candidate temp = persons[out];
          in = out;

          while (in > 0 && persons[in - 1].getFitness() < temp.getFitness()) {
            persons[in] = persons[in - 1];
            --in;
          }
          persons[in] = temp;
        }
      }
    public void evaluate(Candidate candidate) {
        String expression = candidate.getExpression();

        //        System.out.println("Sequence: " + expression);

        double result = 0;
        
        try {
            result = new ExpressionBuilder(expression).build().evaluate();
        } catch (IllegalArgumentException e) {
            result = -999999.9;
        }
        
        if (result == goal) {
            System.out.println("Found target! Iterations: " + times);
            System.out.println("Expression: " + expression);
            running = false;
        }
        double diff = Math.abs(result - goal);

        
        candidate.setFitness(-diff);
        //        System.out.println("Result" + result);
        //        System.out.println("Diff: " + diff);
        //        System.out.println("Cand. Fitness: " + candidate.getFitness());
    }

    private Candidate[] breed(Candidate parent1, Candidate parent2) {
        String exp1 = parent1.getExpression();
        String exp2 = parent2.getExpression();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String result = "";
        String result2 = "";
        for (int i = 0; i <= exp1.length() - 1; i++) {
        	if(i % 2 == 0) {
        		double cross = ran.nextDouble();
                if(cross > crossover) {
                    sb.append(exp2.charAt(i));
                    sb2.append(exp1.charAt(i));
                } else {
                    sb.append(exp1.charAt(i));
                    sb2.append(exp2.charAt(i));
                }
                double mutate = ran.nextDouble();
                if(mutate > mutation) {
                    //do nothing
                } else {
                    sb.setCharAt(i, cifers[ran.nextInt(cifers.length)].charAt(0));
                    sb2.setCharAt(i, cifers[ran.nextInt(cifers.length)].charAt(0));
                }	
        	} else {
            double cross = ran.nextDouble();
            if(cross > crossover) {
                sb.append(exp2.charAt(i));
                sb2.append(exp1.charAt(i));
            } else {
                sb.append(exp1.charAt(i));
                sb2.append(exp2.charAt(i));
            }
            double mutate = ran.nextDouble();
            if(mutate > mutation) {
                //do nothing
            } else {
                sb.setCharAt(i, operators[ran.nextInt(operators.length)].charAt(0));
                sb2.setCharAt(i, operators[ran.nextInt(operators.length)].charAt(0));
            }
        	}
        }
        result = sb.toString();
        result2 = sb2.toString();
        Candidate res = new Candidate(result);
        Candidate res2 = new Candidate(result2);
        evaluate(res);
        evaluate(res2);
        Candidate [] resList = {res, res2};
        return resList;
    }
}
