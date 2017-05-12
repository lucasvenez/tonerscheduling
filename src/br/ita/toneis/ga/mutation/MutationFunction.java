package br.ita.toneis.ga.mutation;

import br.ita.toneis.ga.individual.Individual;

public abstract class MutationFunction {

	protected double mutationProbability = 0.05;
	
	public abstract void mutation(Individual individual);

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
}
