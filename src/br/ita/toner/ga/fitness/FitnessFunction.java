package br.ita.toner.ga.fitness;

import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;

public interface FitnessFunction {
	
	public double calculateFitness(Individual individual, SparseMatrix matrix);

}
