package br.ita.toner.ga.crossover;

import static java.lang.Math.floor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ita.toner.ga.individual.Individual;

public class TonerCrossoverFunction extends CrossoverFunction {

	private final Random random = new Random();
	
	@Override
	public Individual crossover(Individual i, Individual j) {
		
		int size = i.getGens().size();
		
		List<Integer> gens = new ArrayList<Integer>();
		
		for (int l = 0; l < i.getGens().size(); l++) gens.add(-1);
		
		int n = (int)floor(i.getGens().size() * super.crossoverPercentage);

		int initial = random.nextInt(i.getGens().size());
		
		for (int l = initial; l < initial + n; l++) gens.set(l % size, i.getGens().get(l % size));
		
		for (int l = 0, m = 0; l < gens.size();) {
			
			if (gens.get(l) == -1) {
				if (!gens.contains(j.getGens().get(m)))
					gens.set(l++, j.getGens().get(m));
				
				m++;
			} else l++;
		}
		
		return new Individual(gens);
	}
}
