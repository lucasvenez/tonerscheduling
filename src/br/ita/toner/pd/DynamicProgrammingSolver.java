package br.ita.toner.pd;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class DynamicProgrammingSolver {
	public final int MAX_N = 20;
	public final int MAX_M = 1000;
	
	private int N;
	private int M;
	private List<BitSet> receitas;
	private int dp[][];
	private List<Integer> orderList;
	
	public DynamicProgrammingSolver(List<BitSet> receitas) {
		this.receitas = receitas;
		this.N = receitas.size();
		this.M = receitas.get(0).size();
		
		//Isto e feito por que o bitset do Java possui tamanho variado
		for (int i = 0; i < this.receitas.size(); i++) {
			this.M = Math.max(this.M, this.receitas.get(i).size());
		}
		
		this.orderList = new ArrayList<>();
		
		if (this.N > MAX_N) {
			throw new RuntimeException("O tamanho da entrada nao e compativel com uma solucao por PD");
		}
		
		this.dp = new int[(1 << N)][M];
	}
	
	public int getSolutionAsInt() {
		for (int i = 0; i < this.dp.length; i++) {
			for (int j = 0; j < this.dp[i].length; j++) {
				this.dp[i][j] = -1;
			}
		}
		
		return func(0, 0, 0);
	}
	
	public List<Integer> getSolutionAsList() {
		this.orderList = new ArrayList<>();
		
		for (int i = 0; i < this.dp.length; i++) {
			for (int j = 0; j < this.dp[i].length; j++) {
				this.dp[i][j] = -1;
			}
		}
		
		func(0, 0, 0);
		retrievePath(0, 0, 0);
		
		return this.orderList;
	}
	
	private int func(int mask, int now, int best) {
	    if (mask == (1 << N) - 1) {
	        return best;
	    } else {
	        if (dp[mask][now] == -1) {
	        	dp[mask][now] = Integer.MAX_VALUE;

	            for (int i = 0; i < N; i++) {
	                if ((mask & (1 << i)) == 0) {
	                    //Proximo valor de toneis em uso.
	                    int next_now = now;                    
	                    // Quatidade de valores a serem adicionados aos 'toneis ativos'
	                    int to_add = 0;
	                    // Quatidade de valores a serem removidos dos 'toneis ativos'
	                    int to_remove = 0;
	                    
	                    //used = mascara com todos os toneis das receitas já usadas
	                    //not_used = mascara com todos os toneis das receitas ainda nao usadas
	                    BitSet used = new BitSet(this.M);
	                    BitSet not_used = new BitSet(this.M);

	                    for (int j = 0; j < N; j++) {
	                        if (i == j) continue;
	                        if ((mask & (1 << j)) > 0) {
	                            used.or(receitas.get(j));
	                        } else {
	                            not_used.or(receitas.get(j));
	                        }
	                    }

	                    for (int j = 0; j < M; j++) {
	                        //Se a mistura i possuir um ingrediente ainda não usado, incrementar to_add
	                        if (this.receitas.get(i).get(j) && !used.get(j)) {
	                            to_add += 1;
	                        }

	                        if (receitas.get(i).get(j) && !not_used.get(j)) {
	                            //Se a mistura i possuir um elemento que nunca mais sera usado, incrementar to_remove
	                            to_remove += 1;
	                        }
	                    }

	                    next_now = now + to_add;

	                    //Maior valor já encontrado de toneis ativos
	                    int next_best = Math.max(best, next_now);

	                    dp[mask][now] = Math.min(dp[mask][now], func(mask | (1 << i), next_now - to_remove, next_best));
	                }
	            }
	        }
	        
	        return dp[mask][now];
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
                    //Proximo valor de toneis em uso.
                    int next_now = now;                    
                    // Quatidade de valores a serem adicionados aos 'toneis ativos'
                    int to_add = 0;
                    // Quatidade de valores a serem removidos dos 'toneis ativos'
                    int to_remove = 0;
                    
                    //used = mascara com todos os toneis das receitas já usadas
                    //not_used = mascara com todos os toneis das receitas ainda nao usadas
                    BitSet used = new BitSet(this.M);
                    BitSet not_used = new BitSet(this.M);

                    for (int j = 0; j < N; j++) {
                        if (i == j) continue;
                        if ((mask & (1 << j)) > 0) {
                            used.or(receitas.get(j));
                        } else {
                            not_used.or(receitas.get(j));
                        }
                    }

                    for (int j = 0; j < M; j++) {
                        //Se a mistura i possuir um ingrediente ainda não usado, incrementar to_add
                        if (this.receitas.get(i).get(j) && !used.get(j)) {
                            to_add += 1;
                        }

                        if (receitas.get(i).get(j) && !not_used.get(j)) {
                            //Se a mistura i possuir um elemento que nunca mais sera usado, incrementar to_remove
                            to_remove += 1;
                        }
                    }

                    next_now = now + to_add;

                    //Maior valor já encontrado de toneis ativos
                    int next_best = Math.max(best, next_now);

                    int current = func(mask | (1 << i), next_now - to_remove, next_best);
                    
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
