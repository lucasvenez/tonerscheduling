package br.ita.toner.pd.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.ita.toner.data.TestCase;
import br.ita.toner.ga.data.DataLoader;
import br.ita.toner.pd.DynamicProgrammingSolver;

public class DynamicProgrammingSolverTest {
	@Test
	public void testIntegerOutput() {
		DataLoader loader = new DataLoader();
		
		TestCase input = loader.loadFileAsBitsetList("/home/aajjbb/Devel/tonerscheduling/resources/1.txt");
		
		DynamicProgrammingSolver solver = new DynamicProgrammingSolver(input);	

		assertEquals(5, solver.getSolutionAsInt());
	}
	
//	@Test
//	public void testListOutput() {
//		DataLoader loader = new DataLoader();
//		
//		TestCase input = loader.loadFileAsBitsetList("/home/aajjbb/Devel/tonerscheduling/resources/1.txt");
//		
//		DynamicProgrammingSolver solver = new DynamicProgrammingSolver(input);	
//		
//		List<Integer> order = solver.getSolutionAsList();
//		List<Integer> right_output = new ArrayList<>();
//		
//		right_output.add(0);
//		right_output.add(1);
//		right_output.add(3);
//		right_output.add(4);
//		right_output.add(5);
//		right_output.add(6);
//		right_output.add(7);
//		right_output.add(8);
//		right_output.add(9);
//		right_output.add(10);
//		right_output.add(2);
//		
//		assertEquals(right_output, order);
//	}
}
