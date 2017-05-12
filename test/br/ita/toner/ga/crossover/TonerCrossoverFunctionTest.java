package br.ita.toner.ga.crossover;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.ita.toner.ga.crossover.TonerCrossoverFunction;
import br.ita.toner.ga.individual.Individual;

public class TonerCrossoverFunctionTest {

	@Test
	public void test() {
		
		TonerCrossoverFunction cross = new TonerCrossoverFunction();
		
		List<Integer> g1 = new ArrayList<Integer>();
		g1.add(2); g1.add(0); g1.add(3); g1.add(1);
		
		List<Integer> g2 = new ArrayList<Integer>();
		g2.add(3); g2.add(1); g2.add(0); g2.add(2);
		
		cross.crossoverPercentage = .1;
		
		for (int i : cross.crossover(new Individual(g1), new Individual(g2)).getGens())
			System.out.print(i + " ");
		
		System.out.println();		
	}
}
