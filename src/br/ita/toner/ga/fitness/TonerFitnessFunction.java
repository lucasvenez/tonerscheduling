package br.ita.toner.ga.fitness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;

public class TonerFitnessFunction implements FitnessFunction {

	private final Set<Integer> notRequestedToner = new HashSet<Integer>();

	@Override
	public double calculateFitness(Individual individual, SparseMatrix matrix) {

		int max = 0;
		
		List<Integer> minList = new ArrayList<Integer>();
		
		for (int i = 0; i < matrix.getNumberOfColumns(); i++)
			minList.add(-1);
		
		List<Integer> maxList = new ArrayList<Integer>();
		
		for (int i = 0; i < matrix.getNumberOfColumns(); i++)
			maxList.add(-1);		
		
		for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
			for (int i = 0; i < individual.getGens().size(); i++) {
				if (matrix.getValueAt(individual.getGens().get(i), j) == 1) {
					minList.set(j, i);
					break;
				}
			}
		}
		
		for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
			for (int i = individual.getGens().size() - 1; i >= 0 ; i--) {
				if (matrix.getValueAt(individual.getGens().get(i), j) == 1) {
					maxList.set(j, i);
					break;
				}
			}
		}
		
		for (int i = 0; i < individual.getGens().size(); i++) {
			
			int currentMax = 0;
			
			for (int j = 0; j < matrix.getNumberOfColumns(); j++)
				if (i >= minList.get(j) && i <= maxList.get(j))
					currentMax++;
			
			if (currentMax > max)
				max = currentMax;
		}
		
		return max;
	}	
	
	public double calculateFitness2(Individual individual, SparseMatrix matrix) {

		int fitness = 0;

		for (int j = 0; j < matrix.getNumberOfColumns(); j++) {

			int distance = 0;

			boolean hasToner = false;

			if (!notRequestedToner.contains(j)) {
				
				for (int i = 0; i < matrix.getNumberOfRows(); i++) {

					int value = matrix.getValueAt(individual.getGens().get(i), j);

					if (value == 1 && !hasToner) {
						hasToner = true;
					} else if (value == 1 && hasToner) {
						fitness += distance;
						distance = 0;
					} else if (value == 0 && hasToner) {
						distance++;
					}
				}
			}
			
			if (distance == 0.0)
				notRequestedToner.add(j);
		}

		individual.setFitness(fitness);

		return fitness;
	}
}
