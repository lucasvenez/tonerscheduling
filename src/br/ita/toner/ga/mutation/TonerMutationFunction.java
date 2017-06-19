package br.ita.toner.ga.mutation;

import java.util.List;
import java.util.Random;

import br.ita.toner.data.Individual;

public class TonerMutationFunction extends MutationFunction {

	private final Random random = new Random();
	
	@Override
	public void mutation(Individual individual) {
		
		if (random.nextDouble() <= super.mutationProbability) {
			
			List<Integer> gens = individual.getGens();
			
			int i = random.nextInt(gens.size());
			
			int j = random.nextInt(gens.size());
			
			while (i == j) j = random.nextInt(gens.size());
			
			int tmp = gens.get(i);
			
			gens.set(i, gens.get(j));
			
			gens.set(j, tmp);			
		}
	}

}
