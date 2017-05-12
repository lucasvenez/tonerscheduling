package br.ita.toneis.ga.fitness;

import br.ita.toneis.ga.individual.Individual;
import br.ita.toneis.ga.individual.SparseMatrix;

public interface FitnessFunction {
	
	public double calculateFitness(Individual individual, SparseMatrix matrix);

}
