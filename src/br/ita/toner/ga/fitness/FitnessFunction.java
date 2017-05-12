package br.ita.toner.ga.fitness;

import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;

public interface FitnessFunction {
	
	public double calculateFitness(Individual individual, SparseMatrix matrix);

}
