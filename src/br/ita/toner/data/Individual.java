package br.ita.toner.data;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

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
		
		List<BitSet> bitSetList = new ArrayList<BitSet>();
		
		for (int i : this.requestsIDs) {
			
			BitSet bitSet = new BitSet();
			
			for (int j = 0; j < this.matrix.getNumberOfColumns(); j++)
				
				bitSet.set(j, this.matrix.getBooleanValueAt(i, j));
				
		}
		
		return bitSetList;
	}

	public Instances toInstances() {
		
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		for (int i = 0; i < this.matrix.getNumberOfColumns(); i++)
			attributes.add(new Attribute("color" + i));
		
		Instances instances = new Instances("requests", attributes, this.matrix.getNumberOfColumns());
		
		for (int i = 0; i < this.requestsIDs.size(); i++) {
			
			Instance instance = new SparseInstance(this.matrix.getNumberOfColumns());
			
			for (int j = 0; j < this.matrix.getNumberOfColumns(); j++)
				instance.setValue(attributes.get(j), this.matrix.getValueAt(i, j));
			
			instances.add(instance);
		}
		
		return instances;
	}	
}
 