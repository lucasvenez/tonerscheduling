package br.ita.toner.algorithm;

import static java.lang.Math.ceil;

import java.util.List;

import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.Helper;
import br.ita.toner.kmeans.KMeans;
import br.ita.toner.pd.Solver;

public class ClusterBacktrack {

	private int clusterSize = 20;
	
	private final KMeans kmeans = new KMeans();
	
	/**
	 * 
	 * @param requests is a set containing all toner mixture requests
	 * @return the order of each request
	 */
	public Individual apply(Individual requests) {
	
		// split sample in k clusters of 20 instances
		List<Individual> clusters = 
				kmeans.getClusters(requests, (int)ceil(requests.getSize() / (double)clusterSize));
		
		SparseMatrix matrix = new SparseMatrix(
				clusters.size(), requests.getSparseMatrix().getNumberOfColumns());
		
		int index = 0;
		
		for (Individual i : clusters) {
			
			/*
			 * FIXME get index to backtrack
			 * sort each clusters using Dynamic Programming
			 */
			Solver solver = new Solver(i.toBitSetList());
			solver.getSolution();
			
			/*
			 * reduce a cluster to a unique instance using or operation 
			 */
			matrix.setBitSet(index++, i.getRepresentativeIndividual());
		}
		
		/*
		 * call this function recursively
		 */				
		return apply(new Individual(matrix, Helper.generateIdentityArray(clusters.size())));		
	}	
}
