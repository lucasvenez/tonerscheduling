package br.ita.toner.ga;

import org.junit.Test;

import br.ita.toner.ga.GeneticAlgorithm;
import br.ita.toner.ga.data.DataLoader;
import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;

public class GeneticAlgorithmTest {

	DataLoader loader = new DataLoader();
	
	@Test
	public void test() {
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
						"/home/lucas/workspace/toneis/resources/0.txt");
		
		GeneticAlgorithm ga = new GeneticAlgorithm(matrix);
		
		Individual i = ga.search();
		
		System.out.println(ga.getMaxToner(i, ga.baseMatrix));
	}
}
