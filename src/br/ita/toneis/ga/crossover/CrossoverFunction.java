package br.ita.toneis.ga.crossover;

import br.ita.toneis.ga.individual.Individual;

public abstract class CrossoverFunction {

	protected double crossoverPercentage = 0.1;
	
	public double getCrossoverPercentage() {
		return crossoverPercentage;
	}

	public void setCrossoverPercentage(double crossoverPercentage) {
		this.crossoverPercentage = crossoverPercentage;
	}

	public abstract Individual crossover(Individual i, Individual j);
}
