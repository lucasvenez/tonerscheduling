package br.ita.toner.pd;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import br.ita.toner.data.TestCase;

public class DynamicProgrammingSolver {
	public final int MAX_N = 20;
	public final int MAX_M = 1000;

	private int N;
	private int M;
	private int largestPossibleAnswer;
	private List<BitSet> receitas;
	private short dp[][][];
	private List<Integer> orderList;

	public DynamicProgrammingSolver(TestCase test) {
		this.receitas = test.getReceitas();
		this.N = test.getN();
		this.M = test.getM() + 1;

		this.largestPossibleAnswer = this.M;

		this.orderList = new ArrayList<>();

		if (this.N > MAX_N || this.M > MAX_M) {
			throw new RuntimeException("O tamanho da entrada nao e compativel com uma solucao por PD");
		}
		
		this.dp = new short[(1 << this.N)][this.M][this.largestPossibleAnswer];
	}

	public int getSolutionAsInt() {
		for (int i = 0; i < this.dp.length; i++) {
			for (int j = 0; j < this.dp[i].length; j++) {
				for (int k = 0; k < this.largestPossibleAnswer; k++) {
					this.dp[i][j][k] = -1;
				}
			}
		}

		return (int) func(0, (short) 0, (short) 0);
	}

	public List<Integer> getSolutionAsList() {
		this.orderList = new ArrayList<>();

		for (int i = 0; i < this.dp.length; i++) {
			for (int j = 0; j < this.dp[i].length; j++) {
				for (int k = 0; k < this.largestPossibleAnswer; k++) {
					this.dp[i][j][k] = -1;
				}
			}
		}

		func(0, (short) 0, (short) 0);
		retrievePath(0, 0, 0);

		return this.orderList;
	}

	private short func(int mask, short now, short best) {
		if (mask == (1 << this.N) - 1) {
			return best;
		} else {
			if (this.dp[mask][now][best] == -1) {
				this.dp[mask][now][best] = Short.MAX_VALUE;

				for (int i = 0; i < this.N; i++) {
					if ((mask & (1 << i)) == 0) {
						// Quatidade de valores a serem adicionados aos 'toneis
						// ativos'
						short to_add = 0;
						// Quatidade de valores a serem removidos dos 'toneis
						// ativos'
						short to_remove = 0;

						// used = mascara com todos os toneis das receitas já
						// usadas
						// not_used = mascara com todos os toneis das receitas
						// ainda nao usadas
						BitSet used = new BitSet();
						BitSet not_used = new BitSet();

						for (int j = 0; j < this.N; j++) {
							if (i == j)
								continue;
							if ((mask & (1 << j)) != 0) {
								used.or(this.receitas.get(j));
							} else {
								not_used.or(this.receitas.get(j));
							}
						}

						short jokers = 0;

						for (int j = 0; j < this.M; j++) {
							int cntSeen = 0;

							// Se a mistura i possuir um ingrediente ainda não
							// usado, incrementar to_add
							if (this.receitas.get(i).get(j) && !used.get(j)) {
								to_add += 1;

								cntSeen += 1;
							}

							if (this.receitas.get(i).get(j) && !not_used.get(j)) {
								// Se a mistura i possuir um elemento que nunca
								// mais sera usado, incrementar to_remove
								to_remove += 1;

								cntSeen += 1;
							}

							if (cntSeen == 2) {
								jokers += 1;

								to_add -= 1;
								to_remove -= 1;
							}
						}

						short next_now = (short) (now + to_add);

						// Maior valor já encontrado de toneis ativos
						short next_best = (short) Math.max(best, next_now + jokers);

						this.dp[mask][now][best] = (short) Math.min(this.dp[mask][now][best],
								func(mask | (1 << i), (short) (next_now - to_remove), (short) next_best));
					}
				}
			}

			return dp[mask][now][best];
		}
	}

	private void retrievePath(int mask, int now, int best) {
		if (mask == (1 << N) - 1) {
			return;
		} else {
			int call_answer = Integer.MAX_VALUE;

			int selected_index = -1;

			int call_mask = 0;
			int call_now = 0;
			int call_best = 0;

			for (int i = 0; i < N; i++) {
				if ((mask & (1 << i)) == 0) {
					// Quatidade de valores a serem adicionados aos 'toneis
					// ativos'
					short to_add = 0;
					// Quatidade de valores a serem removidos dos 'toneis
					// ativos'
					short to_remove = 0;

					// used = mascara com todos os toneis das receitas já usadas
					// not_used = mascara com todos os toneis das receitas ainda
					// nao usadas
					BitSet used = new BitSet(this.M);
					BitSet not_used = new BitSet(this.M);

					for (int j = 0; j < N; j++) {
						if (i == j)
							continue;
						if ((mask & (1 << j)) > 0) {
							used.or(receitas.get(j));
						} else {
							not_used.or(receitas.get(j));
						}
					}

					short jokers = 0;

					for (int j = 0; j < M; j++) {
						int cntSeen = 0;

						// Se a mistura i possuir um ingrediente ainda não
						// usado, incrementar to_add
						if (this.receitas.get(i).get(j) && !used.get(j)) {
							to_add += 1;

							cntSeen += 1;
						}

						if (receitas.get(i).get(j) && !not_used.get(j)) {
							// Se a mistura i possuir um elemento que nunca mais
							// sera usado, incrementar to_remove
							to_remove += 1;

							cntSeen += 1;
						}

						if (cntSeen == 2) {
							jokers += 1;

							to_add -= 1;
							to_remove -= 1;
						}
					}

					short next_now = (short) (now + to_add);

					// Maior valor já encontrado de toneis ativos
					short next_best = (short) Math.max(best, next_now + jokers);

					short current = (short) func(mask | (1 << i), (short) (next_now - to_remove), (short) next_best);

					if (call_answer > current) {
						call_answer = current;

						selected_index = i;

						call_mask = mask | (1 << i);
						call_now = next_now - to_remove;
						call_best = next_best;
					}
				}
			}

			this.orderList.add(selected_index);

			retrievePath(call_mask, call_now, call_best);
		}
	}
}
