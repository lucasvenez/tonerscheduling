import org.junit.Test;

import br.ita.toneis.ga.GeneticAlgorithm;
import br.ita.toneis.ga.data.DataLoader;
import br.ita.toneis.ga.individual.Individual;
import br.ita.toneis.ga.individual.SparseMatrix;

public class GeneticAlgorithmTest {

	@Test
	public void test() {
	
		DataLoader loader = new DataLoader();
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
					"/home/lucas/workspace/toneis/resources/397.txt");
		
		GeneticAlgorithm ga = new GeneticAlgorithm(matrix);
		
		Individual i = ga.search();
		
		for (int ind : i.getGens())
			System.out.print(ind + " ");		
	}
}
