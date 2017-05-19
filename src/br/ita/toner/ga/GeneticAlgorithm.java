package br.ita.toner.ga;

import java.util.ArrayList;
import java.util.List;

import br.ita.toner.ga.crossover.TonerCrossoverFunction;
import br.ita.toner.ga.fitness.TonerFitnessFunction;
import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;
import br.ita.toner.ga.mutation.TonerMutationFunction;

public class GeneticAlgorithm {

	protected int generations = 5;
	
	protected int numberOfIndividuals = 100;

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
		
		this.bestestIndividual = new Individual(gens);

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
	
	public int getMaxToner(Individual individual, SparseMatrix matrix) {
		
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
