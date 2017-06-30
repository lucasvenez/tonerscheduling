package br.ita.toner.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Lucas Venezian Povoa
 *
 */
public class Graph {
	
	private List<Integer> nodes = new ArrayList<Integer>();

	private Map<Integer, Integer[]> edges = new HashMap<Integer, Integer[]>();

	private SparseMatrix matrix;

	private Map<Integer, List<Integer>> nodeNeighboor = new HashMap<Integer, List<Integer>>();

	private final Set<Integer> visitedNodes = new HashSet<Integer>();

	private final Map<Integer, List<Integer>> twins = new HashMap<Integer, List<Integer>>();

	public Graph(SparseMatrix matrix) {

		this.matrix = matrix;
		
		List<BitSet> b = matrix.toBitSetList();

		int i = 0;

		for (; i < b.size() - 1; i++) {

			nodes.add(b.get(i).cardinality());

			BitSet currentAnd = (BitSet) b.get(i).clone();

			BitSet currentXor = (BitSet) b.get(i).clone();

			for (int j = i + 1; j < b.size(); j++) {

				currentAnd.and(b.get(j));

				currentXor.xor(b.get(j));

				int cardinalityAnd = currentAnd.cardinality();

				int cardinalityXor = currentXor.cardinality();

				this.edges.put(pairToAtomic(i, j), new Integer[] { cardinalityAnd, cardinalityXor });

				this.edges.put(pairToAtomic(j, i), new Integer[] { cardinalityAnd, cardinalityXor });

				if (cardinalityAnd > 0) {

					for (int k : new int[] { 0, 1 })
						if (nodeNeighboor.containsKey(k == 0 ? i : j))
							nodeNeighboor.get(k == 0 ? i : j).add(k == 0 ? j : i);
						else
							nodeNeighboor.put(k == 0 ? i : j, new ArrayList<Integer>(Arrays.asList(k == 0 ? j : i)));
				}

				if (cardinalityXor == 0) {

					if (!this.twins.containsKey(i))
						this.twins.put(i, new ArrayList<Integer>());

					this.twins.get(i).add(j);
				}
			}
		}

		nodes.add(b.get(i).cardinality());
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param weight
	 */
	private void validateInput(int i, int j, int cardinalityAnd, int cardinalityXor) {

		if (i >= this.matrix.getNumberOfRows() || i < 0)
			throw new IndexOutOfBoundsException(
					"i index should be between 0 and " + (this.matrix.getNumberOfRows() - 1));

		if (j >= this.matrix.getNumberOfRows() || j < 0)
			throw new IndexOutOfBoundsException(
					"j index should be between 0 and " + (this.matrix.getNumberOfRows() - 1));

		if (cardinalityAnd > 0 && cardinalityAnd <= this.matrix.getNumberOfColumns())
			throw new IndexOutOfBoundsException(
					"cardinalityAnd should be between 1 and " + this.matrix.getNumberOfColumns());

		if (cardinalityXor >= 0 && cardinalityAnd <= this.matrix.getNumberOfColumns())
			throw new IndexOutOfBoundsException(
					"cardinalityXor should be between 0 and " + this.matrix.getNumberOfColumns());
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param weight
	 */
	public void addEdge(int i, int j, int cardinalityAnd, int cardinalityXor) {

		validateInput(i, j, cardinalityAnd, cardinalityXor);

		int atomic = pairToAtomic(i, j);

		if (edges.containsKey(atomic))
			throw new InvalidParameterException("An edge between nodes i and j already exists.");

		edges.put(atomic, new Integer[] { cardinalityAnd, cardinalityXor });
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param weight
	 * @return
	 */
	public Integer[] replaceEdge(int i, int j, int cardinalityAnd, int cardinalityXor) {

		validateInput(i, j, cardinalityAnd, cardinalityXor);

		int atomic = pairToAtomic(i, j);

		return edges.put(atomic, new Integer[] { cardinalityAnd, cardinalityXor });
	}

	/**
	 * 
	 * @param i
	 * @param weight
	 * @return
	 */
	public Integer setNodeWeight(int i, int weight) {

		if (weight <= 0)
			throw new InvalidParameterException("Node weight should be greater than 0.");

		return this.nodes.set(i, weight);
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public Integer[] getEdgeWeight(int i, int j) {
		return this.edges.get(pairToAtomic(i, j));
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Integer getNodeWeight(int i) {
		return this.nodes.get(i);
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
	 * @param i
	 * @param j
	 * @return
	 */
	private int pairToAtomic(int i, int j) {
		return i * this.matrix.getNumberOfRows() + j;
	}

	public List<Integer> getNodesWithMaxCardinality() {

		List<Integer> result = new ArrayList<Integer>(Arrays.asList(0));

		int max = nodes.get(0);

		for (int i = 1; i < nodes.size(); i++)

			if (max < this.nodes.get(i)) {

				max = this.nodes.get(i);

				result.clear();

				result.add(i);

			} else if (max == this.nodes.get(i)) {
				result.add(i);
			}

		return result;
	}

	/**
	 * Get nodes with max cardinality For each retrieved node do - get parent
	 * nodes with
	 * 
	 * @return
	 */
	public List<Integer> walk() {

		this.visitedNodes.clear();

		List<Integer> result = new ArrayList<Integer>();

		List<Integer> nodesWithMaxCardinality;

		do {

			nodesWithMaxCardinality = this.getUnvisitedNodesWithMaxCardinality();

			for (int i : nodesWithMaxCardinality) {

				if (!this.visitedNodes.contains(i)) {

					this.visitedNodes.add(i);
					result.add(i);

					List<Integer> tmp = this.getUnvisitedTwins(i);

					this.visitedNodes.addAll(tmp);
					result.addAll(tmp);

					tmp = this.getUnvisitedNeightboorsWithMinWeight(i);

					this.visitedNodes.addAll(tmp);
					result.addAll(tmp);
				}
			}

		} while (this.visitedNodes.size() < this.nodes.size());

		return result;
	}

	private List<Integer> getUnvisitedNeightboorsWithMinWeight(int i) {

		List<Integer> result = new ArrayList<Integer>(this.getNeightboorsWithMinWeight(i));

		result.removeAll(this.visitedNodes);

		return result;
	}

	private List<Integer> getUnvisitedTwins(int i) {

		List<Integer> result = new ArrayList<Integer>();

		if (this.twins.containsKey(i)) {

			result.addAll(this.twins.get(i));

			result.removeAll(this.visitedNodes);
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	private List<Integer> getUnvisitedNodesWithMaxCardinality() {
		
		List<Integer> result = new ArrayList<Integer>();
		
		List<Integer> nodes = this.getRequestsIDs();
		
		nodes.removeAll(this.visitedNodes);
		
		int max = this.nodes.get(nodes.get(0));
		
		result.add(nodes.get(0));
		
		nodes.remove(0);
		
		for (int i : nodes) {
			
			if (max < this.nodes.get(i)) {
				
				result.clear();
				
				max = this.nodes.get(i);
				
				result.add(i);				
			
			} else if (max == this.nodes.get(i)) {
				result.add(i);
			}
		}

		return result;
	}

	private List<Integer> getNeightboorsWithMinWeight(int node) {

		List<Integer> neightboor = new ArrayList<Integer>();

		List<Integer> result = new ArrayList<Integer>();
		
		if (this.nodeNeighboor.containsKey(node)) {

			neightboor.addAll(this.nodeNeighboor.get(node));

			result.add(neightboor.get(0));
			
			Integer[] w = this.edges.get(pairToAtomic(node, neightboor.get(0)));

			double min = this.fitness(w);

			for (int i = 1; i < neightboor.size(); i++) {

				w = this.edges.get(pairToAtomic(node, neightboor.get(i)));

				double current = this.fitness(w);

				if (min < current) {

					min = current;

					result.clear();

					result.add(neightboor.get(i));

				} else if (min == current) {
					result.add(neightboor.get(i));
				}
			}
		}

		return result;
	}
	
	private double fitness(Integer[] w) {
		return w[1];
	}

	private List<Integer> getRequestsIDs() {
		
		List<Integer> result = new ArrayList<Integer>();
		
		
		for (int i = 0; i < matrix.getNumberOfRows(); i++)
			result.add(i);
		
		return result;
	}
}
