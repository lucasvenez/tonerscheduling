package br.ita.toneis.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ita.toneis.ga.crossover.CrossoverFunction;
import br.ita.toneis.ga.fitness.FitnessFunction;
import br.ita.toneis.ga.individual.Individual;
import br.ita.toneis.ga.mutation.MutationFunction;

/**
 * 
 * @author Lucas Venezian Povoa
 *
 */
public class Population {

	private final List<Individual> individuals = new ArrayList<Individual>();
	
	private GeneticAlgorithm ga;
	
	private FitnessFunction fitness;
	
	private CrossoverFunction crossover;
	
	private MutationFunction mutation;

	public Population(GeneticAlgorithm geneticAlgorithm, FitnessFunction fitness,
			CrossoverFunction crossover, MutationFunction mutation) {
		this.ga        = geneticAlgorithm;
		this.fitness   = fitness;
		this.crossover = crossover;
		this.mutation  = mutation;
	}

	public List<Individual> getPopulation() {
		return this.individuals;
	}

	public void addIndividual(Individual individual) {
		this.individuals.add(individual);
	}

	/**
	 * 
	 * @param individual
	 * @return
	 */
	public boolean removeIndividual(Individual individual) {
		return this.individuals.remove(individual);
	}

	/**
	 * 
	 * @param individual
	 * @param numberOfIndividuals
	 */
	public void generateRandomPopulationFromIndividual(Individual individual, int numberOfIndividuals) {
		
		individuals.clear();
		
		individuals.add(individual);
		
		for (int i = 0; i < numberOfIndividuals; i++) {

			List<Integer> newGens = new ArrayList<Integer>();
			
			for (int g : individual.getGens())
				newGens.add(g);
			
			Collections.shuffle(newGens);
			
			individuals.add(new Individual(newGens));
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Individual> getIndividuals() {
		return this.individuals;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return this.individuals.size();
	}

	/**
	 * 
	 */
	public void crossover() {
		for (int i = 0; i < this.individuals.size() - 1; i++)
			for (int j = i + 1; j < this.individuals.size(); j++)
				this.crossover.crossover(this.individuals.get(i), this.individuals.get(j));		
	}

	/**
	 * 
	 */
	public void calculateFitness() {
		for (Individual i : this.individuals)
			this.fitness.calculateFitness(i, this.ga.getBaseMatrix());
	}

	/**
	 * 
	 */
	public void mutation() {
		for (Individual i : this.individuals)
			this.mutation.mutation(i);
	}

	/**
	 * 
	 * @param numberOfIndividuals
	 */
	public void selectBestestIndividuals(int numberOfIndividuals) {
		
		double best = this.ga.getBestestIndividual().getFitness();
		
		int bestIndex = -1;
		
		for (int i = 0; i < individuals.size(); i++)
			if (best < individuals.get(i).getFitness()) {
				bestIndex = i;
				best = individuals.get(i).getFitness();
			}

		if (bestIndex > -1)
			this.ga.setBestestIndividual(individuals.get(bestIndex));		
	}
}
