package br.ita.toner.ga.individual;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ita.toner.data.Individual;
import br.ita.toner.ga.Helper;
import br.ita.toner.ga.Population;

public class PopulationTest {

	@Test
	public void randomPopulationFromIndividualTest() {
		
		Population population = new Population(null, null, null, null);
		
		population.generateRandomPopulationFromIndividual(
						new Individual(null, Helper.generateIdentityArray(10)), 5);
		
		assertEquals(6, population.getSize());
	}
}
