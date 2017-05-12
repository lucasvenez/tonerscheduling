package br.ita.toneis.ga.data;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ita.toneis.ga.individual.SparseMatrix;

public class DataLoaderTest {

	@Test
	public void test() {
			
		DataLoader loader = new DataLoader();
			
		SparseMatrix matrix = 
				loader.loadFileAsSparseMatrix(
					"/home/lucas/workspace/toneis/resources/1.txt");
		
		assertEquals(1, matrix.getValueAt(0, 2));
		
		assertEquals(1, matrix.getValueAt(5, 1));
		
		assertEquals(0, matrix.getValueAt(0, 1));
		
		assertEquals(0, matrix.getValueAt(0, 0));
	}
}
