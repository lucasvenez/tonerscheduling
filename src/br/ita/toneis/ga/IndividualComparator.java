package br.ita.toneis.ga;

import java.util.Comparator;

import br.ita.toneis.ga.individual.Individual;

public class IndividualComparator implements Comparator<Individual> {

	@Override
	public int compare(Individual i, Individual j) {
		return (int)(i.getFitness() - j.getFitness());
	}
}
