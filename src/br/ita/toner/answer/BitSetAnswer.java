package br.ita.toner.answer;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetAnswer implements Answerable<List<BitSet>> {

	@Override
	public int getAnswer(final List<BitSet> receitas) {
		int n = receitas.size();
		if(n == 0) return -1;
		List<BitSet> suffix = new ArrayList<BitSet>();
		for(int i = 0; i < n; i++) {
			suffix.add(new BitSet());
		}
		suffix.get(n-1).or(receitas.get(n-1));
		for(int i = n - 2; i >= 0; i--) {
			suffix.get(i).or(suffix.get(i+1));
			suffix.get(i).or(receitas.get(i));
		}
		int active = 0, ans = 0;
		BitSet prefix = (BitSet) receitas.get(0).clone();
		ans = active = Math.max(ans, prefix.cardinality());
		for(int i = 1; i < n; i++) {
			int in = 0, out = 0;
			int len = Math.max(receitas.get(i-1).length(), suffix.get(i).length());
			for(int j = 0; j < len; j++) {
				if(receitas.get(i-1).get(j) && !suffix.get(i).get(j)) {
					out++;
				}
			}
			len = Math.max(receitas.get(i).length(), prefix.length());
			for(int j = 0; j < len; j++) {
				if(receitas.get(i).get(j) && !prefix.get(j)) {
					in++;
				}
			}
			active += in;
			active -= out;
			prefix.or(receitas.get(i));
			ans = Math.max(ans, active);
		}
		return ans;
	}

}