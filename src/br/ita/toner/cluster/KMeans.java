package br.ita.toner.cluster;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import br.ita.toner.data.Individual;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class KMeans implements Clusterable<List<BitSet>> {

	private final SimpleKMeans kmeans = new SimpleKMeans();
	
	private final HierarchicalClusterer hCluster = new HierarchicalClusterer();
	
	private Individual requests;
	
	public KMeans(Individual requests) {
		this.requests = requests;
	}
	
	public List<Individual> getClustersFromIndividual(int clusterSize) throws Exception {
		
		Instances instances = requests.toInstances();
	    
		this.kmeans.setPreserveInstancesOrder(true);
		
		this.kmeans.setNumClusters((int)ceil((double)requests.getSize() / (double)clusterSize));
		
	    kmeans.buildClusterer(instances); 
	    
	    List<Individual> l = new ArrayList<Individual>();
	    
	    for (int i = 0; i < kmeans.getNumClusters(); i++) {
	    	
	    	List<Integer> gens = new ArrayList<Integer>();
	    	
	    	for (int k = 0; k < kmeans.getAssignments().length; k++)
	    		if (kmeans.getAssignments()[k] == i)
	    			gens.add(requests.getGens().get(k));
	    	
	    	if (gens.size() > clusterSize)
	    		for (List<Integer> c : reduceCluster(gens, clusterSize))
	    			l.add(new Individual(requests.getSparseMatrix(), c));
	    	
	    	else
	    		l.add(new Individual(requests.getSparseMatrix(), gens));
	    }
	    
	    l.removeIf(individual -> individual.getSize() == 0);
	    
	    return l;
	}
	
	public List<Individual> getClustersFromIndividualH(int clusterSize) throws Exception {
		
		Instances instances = requests.toInstances();
		
		this.hCluster.setNumClusters((int)ceil((double)requests.getSize() / (double)clusterSize));
		
	    hCluster.buildClusterer(instances); 
	    
	    List<Individual> l = new ArrayList<Individual>();
	    
	    for (int i = 0; i < this.hCluster.getNumClusters(); i++) {
	    	
	    	List<Integer> gens = new ArrayList<Integer>();
	    	
	    	for (int k = 0; k < requests.getSize(); k++)
	    		if (this.hCluster.clusterInstance(instances.get(k)) == i)
	    			gens.add(requests.getGens().get(k));
	    	
	    	if (gens.size() > clusterSize)
	    		for (List<Integer> c : reduceCluster(gens, clusterSize))
	    			l.add(new Individual(requests.getSparseMatrix(), c));
	    	
	    	else
	    		l.add(new Individual(requests.getSparseMatrix(), gens));
	    }
	    
	    l.removeIf(individual -> individual.getSize() == 0);
	    
	    return l;
	}
	
	private List<List<Integer>> reduceCluster(List<Integer> gens, int limit) {
		
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < gens.size(); i += limit)
			result.add(gens.subList(i, min(gens.size(), i + limit)));

		return result;
	}

	@Override
	public List<List<BitSet>> getClusters(int clusterSize) {
		
		List<List<BitSet>> result = null;
		
		try {
			 result = this.toBitSetList(this.getClustersFromIndividual(clusterSize));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private List<List<BitSet>> toBitSetList(List<Individual> clusters) {
		
		List<List<BitSet>> result = new ArrayList<List<BitSet>>();
		
		for (Individual i : clusters)
			result.add(i.toBitSetList());
		
		return result;
	}
}
