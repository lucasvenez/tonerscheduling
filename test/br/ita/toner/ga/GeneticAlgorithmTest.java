package br.ita.toner.ga;

import org.junit.Test;

import br.ita.toner.data.DataLoader;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.GeneticAlgorithm;

public class GeneticAlgorithmTest {

	DataLoader loader = new DataLoader();
	
	@Test
	public void test() {
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
						"C:/Users/Lucas Venezian Povoa/git/tonerscheduling/resources/512.txt");
		
		GeneticAlgorithm ga = new GeneticAlgorithm(matrix);
		
		Individual i = ga.search();
		
		System.out.println(ga.getMaxToner(i, ga.baseMatrix));
	}
}
