package br.ita.toner.graph;

import org.junit.Test;

import br.ita.toner.answer.IndividualAnswer;
import br.ita.toner.data.DataLoader;
import br.ita.toner.data.Graph;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;

public class GraphTest {

	DataLoader loader = new DataLoader();
	
	@Test
	public void test() {
		
		int files[] = new int[] { 1364, 786, 779, 955, 962, 1131, 1139, 336, 350, 587, 581, 621, 603, 714, 711, 842,
				830, 1010, 1017 };

		for (int f : files) {
		
			long start = System.currentTimeMillis();
			
			SparseMatrix matrix = 
					loader.loadFileAsSparseMatrix("/home/lucas/git/tonerscheduling/resources/" + f + ".txt");
			
			Graph graph = new Graph(matrix);
			
			Individual i = new GraphSolver().getAnswer(graph);
			
			System.out.println(new IndividualAnswer().getAnswer(i));
			
			System.out.println(System.currentTimeMillis() - start);
		}
	}

}
