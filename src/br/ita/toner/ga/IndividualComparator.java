package br.ita.toner.ga;

import java.util.Comparator;

import br.ita.toner.ga.individual.Individual;

public class IndividualComparator implements Comparator<Individual> {

	@Override
	public int compare(Individual i, Individual j) {
		return (int)(i.getFitness() - j.getFitness());
	}
}
