package br.ita.toner.algorithm;

import java.util.List;

import br.ita.toner.cluster.KMeans;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.Helper;
import br.ita.toner.pd.Solver;

public class ClusterBacktrack {

	private int clusterSize = 20;
	
	private final KMeans kmeans = new KMeans(this.clusterSize);
	
	/**
	 * 
	 * @param requests is a set containing all toner mixture requests
	 * @return the order of each request
	 */
	public Individual apply(Individual requests) {
	
		/*
		 * FIXME Add recursion stop condition
		 */
		
		/*
		 * split sample in k clusters of 20 instances
		 */
		
		List<Individual> clusters = null;
		
		try {
			clusters = kmeans.getClusters(requests);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clusters.removeIf(individual -> individual.getSize() == 0);
		
		SparseMatrix matrix = new SparseMatrix(
				clusters.size(), requests.getSparseMatrix().getNumberOfColumns());
		
		int index = 0;
		
		for (Individual i : clusters) {
			
			/*
			 * FIXME get new order index to backtrack
			 * sort each clusters using Dynamic Programming
			 */
			Solver solver = new Solver(i.toBitSetList());
			solver.getSolution();
			
			/*
			 * reduce a cluster to a unique instance using or operation 
			 */
			matrix.setBitSet(index++, matrix.getNumberOfColumns(), i.getRepresentativeIndividual());
		}
		
		/*
		 * call this function recursively
		 */				
		return apply(new Individual(matrix, Helper.generateIdentityArray(clusters.size())));		
	}	
}
