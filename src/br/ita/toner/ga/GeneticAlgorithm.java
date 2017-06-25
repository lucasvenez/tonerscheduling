package br.ita.toner.ga;

import java.util.List;

import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.crossover.TonerCrossoverFunction;
import br.ita.toner.ga.fitness.TonerFitnessFunction;
import br.ita.toner.ga.mutation.TonerMutationFunction;

public class GeneticAlgorithm {

	protected int generations = 40;
	
	protected int numberOfIndividuals = 5;

	protected SparseMatrix baseMatrix;

	protected Population population;
	
	protected Individual bestestIndividual;

	public GeneticAlgorithm(SparseMatrix baseMatrix) {
		this.baseMatrix = baseMatrix;
		this.population = 
			new Population(this, 
				new TonerFitnessFunction(), 
				new TonerCrossoverFunction(),
				new TonerMutationFunction());
	}

	public Individual search() {

		List<Integer> gens = Helper.generateIdentityArray(this.baseMatrix.getNumberOfRows());
		
		this.bestestIndividual = new Individual(this.baseMatrix, gens);

		this.population.generateRandomPopulationFromIndividual(this.bestestIndividual, this.numberOfIndividuals);

		this.population.calculateFitness();

		this.population.selectBestestIndividuals(this.numberOfIndividuals);

		int i = 0;

		do {
			
			this.population.crossover();

			this.population.mutation();

			this.population.calculateFitness();

			this.population.selectBestestIndividuals(this.numberOfIndividuals);

		} while (++i < generations);

		return this.bestestIndividual;
	}

	public SparseMatrix getBaseMatrix() {
		return this.baseMatrix;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public Individual getBestestIndividual() {
		return this.bestestIndividual;
	}

	public void setBestestIndividual(Individual individual) {
		this.bestestIndividual = individual;		
	}
	
	public void printMatrix() {
		
		for (int i : this.bestestIndividual.getGens()) {
		
			for (int j = 0; j < this.baseMatrix.getNumberOfColumns(); j++)
				System.out.println(this.baseMatrix.getValueAt(i, j) + " ");
			
			System.out.println();	
		}
	}
}
