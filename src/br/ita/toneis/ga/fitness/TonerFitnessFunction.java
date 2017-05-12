package br.ita.toneis.ga.fitness;

import br.ita.toneis.ga.individual.Individual;
import br.ita.toneis.ga.individual.SparseMatrix;

public class TonerFitnessFunction implements FitnessFunction {

	@Override
	public double calculateFitness(Individual individual, SparseMatrix matrix) {
		
		int fitness = 0;
		
		for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
			
			int distance = 0;
			
			boolean hasToner = false;
			
			for (int i = 0; i < matrix.getNumberOfColumns(); i++) {
				
				int value = matrix.getValueAt(individual.getGens().get(i), j);
				
				if (value == 1 && !hasToner) {
					hasToner = true;
				} else if (value == 1 && hasToner)  {
					fitness += distance;
					distance = 0;
				} else if (value == 0 && !hasToner) {
					distance++;
				}
			}
		}
		
		individual.setFitness(fitness);
		
		return fitness;
	}
}
