package br.ita.toner.ga.mutation;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import br.ita.toner.data.Individual;
import br.ita.toner.ga.mutation.MutationFunction;
import br.ita.toner.ga.mutation.TonerMutationFunction;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TonerMutationFunctionTest {

	@Test
	public void test() {
		
		final List<Integer> gens = new ArrayList<Integer>();
		
		gens.add(1); gens.add(0); gens.add(2);
		
		Individual i = new Individual(null, gens);
		
		MutationFunction mf = new TonerMutationFunction();
		
		mf.setMutationProbability(1.0);
		
		mf.mutation(i);
		
		assertThat(gens, is(not(i.getGens())));
	}
}
