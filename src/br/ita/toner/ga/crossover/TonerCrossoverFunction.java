package br.ita.toner.ga.crossover;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import java.util.List;
import java.util.Random;

import br.ita.toner.ga.Helper;
import br.ita.toner.ga.individual.Individual;

public class TonerCrossoverFunction extends CrossoverFunction {

	private final Random random = new Random();
	
	@Override
	public Individual crossover(Individual i, Individual j) {
		
		
		for (int m : i.getGens())
			System.out.print(m + ",");
		
		System.out.println();
		
		
		int size = i.getGens().size();
		
		List<Integer> gens = Helper.generateEmptySet(size);
		
		int initial = random.nextInt(i.getGens().size());
		
		int n = min(size / 2, (int)floor(i.getGens().size() * super.crossoverPercentage));
		
		for (int l = initial; l < initial + n; l++) gens.set(l % size, i.getGens().get(l % size));
		
		for (int l = 0, m = 0; l < gens.size();) {
			
			if (gens.get(l) == -1) {
				if (!gens.contains(j.getGens().get(m)))
					gens.set(l++, j.getGens().get(m));
				
				m++;
			} else l++;
		}
		
		for (int m : gens)
			System.out.print(m + ",");
		
		System.out.println();		
		
		return new Individual(gens);
	}
}
