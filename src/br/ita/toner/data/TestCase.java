package br.ita.toner.data;

import java.util.BitSet;
import java.util.List;

public class TestCase {
	private int N;
	private int M;
	private List<BitSet> receitas;
	
	public TestCase(int n, int m, List<BitSet> receitas) {
		super();
		this.N = n;
		this.M = m;
		this.receitas = receitas;
	}
	public int getN() {
		return N;
	}
	public int getM() {
		return M;
	}
	public List<BitSet> getReceitas() {
		return receitas;
	}
	
	
}
