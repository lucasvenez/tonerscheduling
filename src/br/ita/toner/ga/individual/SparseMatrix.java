package br.ita.toner.ga.individual;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Lucas Venezian Povoa
 *
 */
public class SparseMatrix {
	
	private int numberOfRows;
	
	private int numberOfColumns;
	
	private Set<Integer> elements = new HashSet<Integer>();
	
	public SparseMatrix(int numberOfRows, int numberOfColumns) {
		this.numberOfRows    = numberOfRows;
		this.numberOfColumns = numberOfColumns;
	}
	
	public void addPair(int i, int j) {
		
		this.checkIndexLimits(i, j);
		
		elements.add(this.pairToAtomic(i, j));		
	}
	
	public int getValueAt(int i, int j) {
		
		this.checkIndexLimits(i, j);
		
		return this.elements.contains(this.pairToAtomic(i, j)) ? 1 : 0;
	}
	
	private void checkIndexLimits(int i, int j) {
		
		if (i < 0 || i >= numberOfRows)
			throw new IndexOutOfBoundsException("i should be between 0 and " + this.numberOfRows + " but it has " + i);
		
		if (j < 0 || j >= numberOfColumns)
			throw new IndexOutOfBoundsException("j should be between 0 and " + this.numberOfColumns  + " but it has " + j);
	}
	
	public int[] atomicToPair(int index) {
		return new int[] {index / this.numberOfColumns, index % this.numberOfColumns};
	}
	
	public int pairToAtomic(int i, int j) {
		return i * this.numberOfColumns + j;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + numberOfColumns;
		result = prime * result + numberOfRows;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparseMatrix other = (SparseMatrix) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (numberOfColumns != other.numberOfColumns)
			return false;
		if (numberOfRows != other.numberOfRows)
			return false;
		return true;
	}
	
	public int getNumberOfRows() {
		return this.numberOfRows;
	}
	
	public int getNumberOfColumns() {
		return this.numberOfColumns;
	}
	
	public List<BitSet> toBitSetList() {
		
		List<BitSet> result = new ArrayList<BitSet>();
		
		for (int i = 0; i < this.numberOfRows; i++ )
			result.add(new BitSet());
		
		for (Integer e : this.elements) {
		
			int[] index = this.atomicToPair(e);
			
			result.get(index[0]).set(index[1]);
		}		
		
		return result;
	}
}
