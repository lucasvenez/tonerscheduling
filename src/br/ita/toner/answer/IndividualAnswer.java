package br.ita.toner.answer;

import java.util.ArrayList;
import java.util.List;

import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;

public class IndividualAnswer implements Answerable<Individual> {

	@Override
	public int getAnswer(Individual individual) {
		
		SparseMatrix matrix = individual.getSparseMatrix();
		
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
}