package br.ita.toner.answer;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;

public class BitSetAnswer implements Answer<List<BitSet>> {

	@Override
	public int getAnswer(List<BitSet> receitas) {
		int n = receitas.size();
		if(n == 0) return -1;
		
		List<BitSet> cumulativeOr = new Vector<BitSet>(n);
		for(int i = 0; i < n; i++) {
			cumulativeOr.add(new BitSet());
		}
		cumulativeOr.get(n-1).or(receitas.get(n-1));
		for(int i = n - 2; i >= 0; i--) {
			cumulativeOr.get(i).or(cumulativeOr.get(i+1));
			cumulativeOr.get(i).or(receitas.get(i));
		}
		int ans = 0;
		BitSet currentMask = receitas.get(0);
		ans = Math.max(ans, currentMask.cardinality());
		for(int i = 1; i < n; i++) {
			currentMask.and(cumulativeOr.get(i));
			currentMask.or(cumulativeOr.get(i));
			ans = Math.max(ans, currentMask.cardinality());
		}
		return ans;
	}

}