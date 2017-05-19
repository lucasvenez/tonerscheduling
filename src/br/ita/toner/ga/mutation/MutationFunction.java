package br.ita.toner.ga.mutation;

import br.ita.toner.ga.individual.Individual;

public abstract class MutationFunction {

	protected double mutationProbability = 0.5;
	
	public abstract void mutation(Individual individual);

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
}
