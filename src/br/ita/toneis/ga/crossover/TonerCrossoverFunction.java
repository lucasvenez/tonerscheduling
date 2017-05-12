package br.ita.toneis.ga.crossover;

import static java.lang.Math.floor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ita.toneis.ga.individual.Individual;

public class TonerCrossoverFunction extends CrossoverFunction {

	private final Random random = new Random();
	
	@Override
	public Individual crossover(Individual i, Individual j) {
		
		List<Integer> gens = new ArrayList<Integer>();
		
		for (int l = 0; l < i.getGens().size(); l++)
			gens.add(-1);
		
		int n = (int)floor(i.getGens().size() * super.crossoverPercentage);
		
		int initial = random.nextInt(i.getGens().size());
		
		for (int l = initial; l < initial + n; l++) {
			gens.set(l, i.getGens().get(l));
		}
		
		int m = 0;
		
		for (int l = 0; l < gens.size(); l++) 
			if (gens.get(m) != -1) {
				if (gens.contains(j.getGens().get(l)))
					gens.set(m++, j.getGens().get(l));
			}
			
		
		return new Individual(gens);
	}
}
