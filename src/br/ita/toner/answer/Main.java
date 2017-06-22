package br.ita.toner.answer;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import br.ita.toner.cluster.PaintGraphCluster;

/*
 * @Test
 * Helper class to test PaintGraphCluster and BitSetAnswer
 */
public class Main {
	
	private static Scanner scanner;

	public static void main(String[] args) {
		
		int n, m;
		String tmp;
		scanner = new Scanner(System.in);
		n = scanner.nextInt();
		m = scanner.nextInt();
		List<BitSet> recipes = new Vector<BitSet>(n);
		for(int i = 0; i < n; i++) {
			tmp = scanner.next();
			BitSet bitSet = new BitSet();
			for(int j = 0; j < m; j++) {
				boolean bit = (tmp.charAt(j) == '1');
				bitSet.set(j, bit);
			}
			recipes.add(bitSet);
		}
		
		PaintGraphCluster pgc = new PaintGraphCluster(recipes, m);
		List<List<BitSet>> ans = pgc.getClusters(recipes.size());
		
		System.out.println("Answer before clusterizing = " + new BitSetAnswer().getAnswer(recipes));
		
		System.out.println("There are " + ans.size() + " clusters.");
		//Just one cluster, then get(0)
		System.out.println("Answer after clusterizing, on cluster[0] = " + new BitSetAnswer().getAnswer(ans.get(0)));
		System.out.println();
		
		//printing the recipes on clusters:
		for(int i = 0; i < ans.size(); i++) {
			System.out.println("Cluster["+i+"]:");
			for(BitSet recipe : ans.get(i)) {
				for(int j = 0; j < m; j++) {
					System.out.print(recipe.get(j) ? '1' : '0');
				}
				System.out.println();
			}
		}
	}
}
