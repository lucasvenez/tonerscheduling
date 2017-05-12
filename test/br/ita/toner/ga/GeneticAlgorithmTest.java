package br.ita.toner.ga;

import org.junit.Test;

import br.ita.toner.ga.GeneticAlgorithm;
import br.ita.toner.ga.data.DataLoader;
import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;

public class GeneticAlgorithmTest {

	@Test
	public void test() {
	
		DataLoader loader = new DataLoader();
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
					"/home/lucas/workspace/toneis/resources/938.txt");
		
		GeneticAlgorithm ga = new GeneticAlgorithm(matrix);
		
		Individual i = ga.search();
		
		for (int ind : i.getGens())
			System.out.print(ind + " ");		
	}
}
