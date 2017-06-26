package br.ita.toner.backtrack;

import org.junit.Test;

import br.ita.toner.answer.IndividualAnswer;
import br.ita.toner.data.DataLoader;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;

public class ClusterBacktrackTest {

	DataLoader loader = new DataLoader();
	
	@Test
	public void test() {
		
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix("/home/lucas/git/tonerscheduling/resources/577.txt");
		
		ClusterBacktrack backtrack = new ClusterBacktrack();
		
		Individual i = backtrack.sort(matrix);
		
		System.out.println(new IndividualAnswer().getAnswer(i));	
	}
}
