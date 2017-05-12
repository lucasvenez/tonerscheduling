package br.ita.toneis.ga.individual;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ita.toneis.ga.individual.SparseMatrix;

public class SparseMatrixTest {

	private final SparseMatrix matrix = new SparseMatrix(4, 4);
	
	@Test
	public void atomicToPairTest() {
		
		assertArrayEquals(new int[] {1, 2}, matrix.atomicToPair(6));
		
		assertArrayEquals(new int[] {2, 1}, matrix.atomicToPair(9));
		
		assertArrayEquals(new int[] {2, 3}, matrix.atomicToPair(11));
	}
	
	@Test
	public void pairToAtomicTest() {

		assertEquals(0, matrix.pairToAtomic(0, 0));
		
		assertEquals(1, matrix.pairToAtomic(0, 1));
		
		assertEquals(4, matrix.pairToAtomic(1, 0));
		
		assertEquals(9, matrix.pairToAtomic(2, 1));		
	}
}
