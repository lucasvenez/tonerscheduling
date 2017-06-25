package br.ita.toner.algorithm;

import java.util.Collections;
import java.util.List;

import br.ita.toner.cluster.KMeans;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.Helper;
import br.ita.toner.pd.DynamicProgrammingSolver;

/**
 * <b>Algorithm</b>
 * <ul>
 * <li>Cluster</li>
 * <li>Sort elements of each cluster</li>
 * <li>Reduce clusters into a unique</li>
 * <li>Call function to order reduction</li>
 * <li>Get sorted reduction and sort clusters</li>
 * <li>Transform cluster into a unique individual</li>
 * <li>Return it</li>
 * </ul>
 */
public class ClusterBacktrack {

	private int clusterSize = 20;
	
	/**
	 * 
	 * @param requests is a set containing all toner mixture requests
	 * @return the order of each request
	 */
	public Individual apply(Individual requests) {
	
		/*
		 * Recursion stop condition
		 */
		if (requests.getSize() <= 20) {
			DynamicProgrammingSolver solver = new DynamicProgrammingSolver(requests.toBitSetList());
			return new Individual(requests.getSparseMatrix(), solver.getSolutionAsList());
		}
		
		/*
		 * split sample in k clusters of 20 instances
		 */
		List<Individual> clusters = null;
		
		try {
			final KMeans kmeans = new KMeans(requests);
			clusters = kmeans.getClustersFromIndividual(this.clusterSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SparseMatrix matrix = new SparseMatrix(
				clusters.size(), requests.getSparseMatrix().getNumberOfColumns());
		
		int index = 0;
		
		for (Individual i : clusters) {
			
			/*
			 * sort each clusters using Dynamic Programming
			 */
			DynamicProgrammingSolver solver = new DynamicProgrammingSolver(i.toBitSetList());
			new Individual(matrix, solver.getSolutionAsList());
			
			/*
			 * reduce a cluster to a unique instance using or operation 
			 */
			matrix.setBitSet(index++, matrix.getNumberOfColumns(), i.getRepresentativeIndividual());
		}
		
		/*
		 * Create individual from matrix and call this function recursively
		 */
		Individual sorted = apply(new Individual(matrix, Helper.generateIdentityArray(clusters.size())));
		
		/*
		 * Sort clusters
		 */
		int l = 0;
		
		for (int k : sorted.getGens())
			Collections.swap(clusters, l++, k);
		
		/*
		 * Transform cluster into a unique individual
		 */
		List<Integer> gens = clusters.get(0).getGens();
		
		for (int i = 1; i < clusters.size(); i++)
			gens.addAll(clusters.get(i).getGens());
				
		return new Individual(requests.getSparseMatrix(), gens);
	}	
}
