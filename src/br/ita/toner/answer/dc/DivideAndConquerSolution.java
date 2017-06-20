package br.ita.toner.answer.dc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import br.ita.toner.answer.Answer;
import br.ita.toner.answer.BitSetAnswer;

public class DivideAndConquerSolution implements  Answer<List<BitSet>> {
	public static final int DP_MAX_VALUE = 23;
	
	@Override
	public int getAnswer(List<BitSet> receitas) {
		List<Integer> answer = recursiveDivision(receitas);
		
		List<BitSet> answer_as_bitset = new ArrayList<>();
		
		for (Integer position: answer) {
			answer_as_bitset.add(receitas.get(position));
		}
		
		BitSetAnswer answerer = new BitSetAnswer();
		
		return answerer.getAnswer(answer_as_bitset);
	}
	
	private List<Integer> recursiveDivision(List<BitSet> receitas) {
		//WIP
		return null;
//		if (receitas.size() < DP_MAX_VALUE) {
//			 DynamicProgrammingSolver solver = new DynamicProgrammingSolver(receitas);
//			 
//			 List<Integer> order_list = solver.getSolutionAsList();		
//			 
//			 return order_list;
//		} else {
//			int buckets_size = (int) Math.ceil(receitas.size() / DP_MAX_VALUE);
//			Random random = new Random();
//			
//			List<List<BitSet> > buckets = new ArrayList<>(buckets_size); 
//			
//			//Inicialmente os elementos formam buckets de forma aleatoria.
//			//Deve ser implementada alguma funcao de similaridade ao contrario de uma aleatoria
//			
//			for (int i = 0; i < receitas.size(); i++) {
//				int selected_index = random.nextInt(receitas.size());
//				
//				BitSet item = receitas.get(selected_index);
//				
//				receitas.remove(selected_index);
//				
//				buckets.get(i % buckets.size()).add(item);
//			}
//			
//			List<List<BitSet>> buckets_answers = new ArrayList<>(buckets_size);
//			
//		
//		}
	}
}
