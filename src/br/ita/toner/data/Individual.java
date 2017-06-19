package br.ita.toner.data;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Lucas Venezian Povoa
 */
public class Individual implements Comparable<Individual> {

	private List<Integer> requestsIDs = new ArrayList<Integer>();
	
	private double fitness = Double.MAX_VALUE;
	
	private SparseMatrix matrix;
	
	/**
	 * 
	 * @param matrix
	 */
	public Individual(SparseMatrix matrix) {
		
		this.matrix = matrix;
		
		for (int i = 0; i < this.matrix.getNumberOfRows(); i++)
			requestsIDs.add(i);
	}
	
	/**
	 * 
	 * @param matrix
	 * @param gens
	 */
	public Individual(SparseMatrix matrix, List<Integer> gens) {
		
		this.matrix = matrix;
		
		for(int i : gens)
			this.requestsIDs.add(i);
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 */
	public void permuteGens(int i, int j) {

		int tmp = requestsIDs.get(i);
		
		requestsIDs.set(i, requestsIDs.get(j));
		
		requestsIDs.set(j, tmp);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getGens() {
		return this.requestsIDs;
	}

	/**
	 * 
	 * @return
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * 
	 * @param fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(Individual individual) {
		return (int)(this.fitness - individual.getFitness());
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMaxNumberOfWaitingToners() {

		int maxNumberOfWaitingToners = 0;
		
		final Set<Integer> notRequestedToner = new HashSet<Integer>();

		for (int j = 0; j < matrix.getNumberOfColumns(); j++) {

			int distance = 0;

			boolean hasToner = false;

			if (!notRequestedToner.contains(j)) {
				
				for (int i = 0; i < matrix.getNumberOfRows(); i++) {

					int value = matrix.getValueAt(this.getGens().get(i), j);

					if (value == 1 && !hasToner) {
						hasToner = true;
					} else if (value == 1 && hasToner) {
						maxNumberOfWaitingToners += distance;
						distance = 0;
					} else if (value == 0 && hasToner) {
						distance++;
					}
				}
			}
			
			if (distance == 0.0)
				notRequestedToner.add(j);
		}
		
		return maxNumberOfWaitingToners;
	}

	/**
	 * 
	 * @return
	 */
	public SparseMatrix getSparseMatrix() {
		return this.matrix;
	}
	
	/**
	 * 
	 * @return
	 */
	public BitSet getRepresentativeIndividual() {
		
		BitSet bits = new BitSet();
		
		for (int i = 0; i < this.requestsIDs.size(); i++) {
			
			BitSet currentBits = new BitSet();
			
			for (int j = 0; j < matrix.getNumberOfColumns(); j++)
				if (matrix.getValueAt(this.requestsIDs.get(i), j) > 0)
					currentBits.set(j);
			
			bits.or(currentBits);
		}
		
		return bits;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return this.requestsIDs.size();
	}

	public List<BitSet> toBitSetList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
 