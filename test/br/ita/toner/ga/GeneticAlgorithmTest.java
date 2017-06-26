package br.ita.toner.ga;

import org.junit.Test;

import br.ita.toner.answer.IndividualAnswer;
import br.ita.toner.data.DataLoader;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;

public class GeneticAlgorithmTest {

	DataLoader loader = new DataLoader();
	
	@Test
	public void test() {
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
						"/home/lucas/git/tonerscheduling/resources/577.txt");
		
		GeneticAlgorithm ga = new GeneticAlgorithm(matrix);
		
		Individual i = ga.search();
		
		System.out.println(new IndividualAnswer().getAnswer(i));
	}
}
