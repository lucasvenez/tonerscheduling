package br.ita.toner.ga.individual;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lucas Venezian Povoa
 */
public class Individual implements Comparable<Individual> {

	private List<Integer> gens = new ArrayList<Integer>();
	
	private double fitness = Double.MAX_VALUE;
	
	public Individual(int numberOfRows) {
		for (int i = 0; i < numberOfRows; i++)
			gens.add(i);
	}
	
	public Individual(List<Integer> gens) {
		for(int i : gens)
			this.gens.add(i);
	}
	
	public void permuteGens(int i, int j) {
		int tmp = gens.get(i);
		gens.set(i, gens.get(j));
		gens.set(j, tmp);
	}
	
	public List<Integer> getGens() {
		return this.gens;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public int compareTo(Individual individual) {
		return (int)(this.fitness - individual.getFitness());
	}
}
