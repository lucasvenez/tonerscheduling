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

		int files[] = new int[] { 1364, 786, 779, 955, 962, 1131, 1139, 336, 350, 587, 581, 621, 603, 714, 711, 842,
				830, 1010, 1017 };

		for (int f : files) {

			SparseMatrix matrix = loader.loadFileAsSparseMatrix("/home/lucas/git/tonerscheduling/resources/" + f + ".txt");

			ClusterBacktrack backtrack = new ClusterBacktrack();

			Individual i = backtrack.sort(matrix);

			System.out.println(new IndividualAnswer().getAnswer(i));
		}
	}
}
