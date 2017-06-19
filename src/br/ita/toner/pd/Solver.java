package br.ita.toner.pd;

import java.util.BitSet;
import java.util.List;

public class Solver {
	
	private int N;
	
	private int M;
	
	private List<BitSet> receitas;
	
	private int dp[][];
	
	private final int inputLimit = 10;

	public Solver(List<BitSet> receitas) {
	
		this.receitas = receitas;
		
		this.N = receitas.size();
		
		this.M = receitas.get(0).length();

		if (this.N > this.inputLimit)
			throw new RuntimeException("O tamanho da entrada nao e compativel com uma solucao por PD");

		this.dp = new int[(1 << N)][M];
	}

	public int getSolution() {
		
		for (int i = 0; i < this.dp.length; i++)
			for (int j = 0; j < this.dp[i].length; j++)
				this.dp[i][j] = -1;

		return func(0, 0, 0);
	}

	private int func(int mask, int now, int best) {
		
		if (mask == (1 << N) - 1)
			return best;
		
		if (dp[mask][now] == -1) {
			
			dp[mask][now] = Integer.MAX_VALUE;

			for (int i = 0; i < N; i++)
				
				if ((mask & (1 << i)) == 0) {
					
					/* 
					 * Proximo valor de toneis em uso.
					 */
					int next_now = now;
					
					/* Quatidade de valores a serem adicionados aos 'toneis
					 * ativos
					 */
					int to_add = 0;
					
					/* 
					 * Quatidade de valores a serem removidos dos 'toneis ativos'
					 */
					int to_remove = 0;

					/* used = mascara com todos os toneis das receitas já
					 * usadas
					 * not_used = mascara com todos os toneis das receitas
					 * ainda nao usadas
					 */ 
					BitSet used = new BitSet(this.M);
					BitSet not_used = new BitSet(this.M);

					/*
					 * Coringas são tintas que são usadas apenas por uma
					 * receita, ou seja, não serão incluidas no next_now
					 */
					int jokers = 0;

					for (int j = 0; j < N; j++) {
						if (i == j)
							continue;
						if ((mask & (1 << j)) > 0) {
							used.or(receitas.get(j));
						} else {
							not_used.or(receitas.get(j));
						}
					}

					for (int j = 0; j < M; j++) {
						int cntJoker = 0;

						/*
						 * Se a mistura i possuir um ingrediente ainda não
						 * usado, incrementar to_add
						 */
						if (receitas.get(i).get(j) && !used.get(j)) {
							to_add += 1;
							cntJoker += 1;
						}

						if (receitas.get(i).get(j) && !used.get(j)) {
							
							/*
							 * Se a mistura i possuir um elemento que nunca
							 * mais sera usado, incrementar to_remove
							 */
							to_remove += 1;
							cntJoker += 1;
						}

						if (cntJoker == 2) {
							jokers += 1;
							to_add -= 1;
							to_remove -= 1;
						}
					}

					next_now = now + to_add - to_remove;

					/*
					 *  Maior valor já encontrado de toneis ativos
					 */
					int next_best = Math.max(best, next_now + jokers);

					dp[mask][now] = Math.min(dp[mask][now], func(mask | (1 << i), next_now, next_best));
				}
			}

		return dp[mask][now];
	}
}
