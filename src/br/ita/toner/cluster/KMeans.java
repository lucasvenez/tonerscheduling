package br.ita.toner.cluster;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import br.ita.toner.data.Individual;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class KMeans implements Clusterable<List<BitSet>> {

	private final SimpleKMeans kmeans = new SimpleKMeans();
	
	private Individual requests;
	
	public KMeans(Individual requests) {
		this.requests = requests;
	}
	
	public List<Individual> getClustersFromIndividual(int clusterSize) throws Exception {
		
		Instances instances = requests.toInstances();
	    
		this.kmeans.setNumClusters(clusterSize);
		
	    kmeans.buildClusterer(instances); 
 
	    List<Individual> l = new ArrayList<Individual>(kmeans.getNumClusters());
	    
	    for (int i = 0; i < kmeans.getNumClusters(); i++)
	    	l.add(new Individual(requests.getSparseMatrix()));
	    
	    for (int i = 0; i < instances.numInstances(); i++)
	    	l.get(kmeans.clusterInstance(instances.instance(i))).getGens().add(requests.getGens().get(i));
	    
	    l.removeIf(individual -> individual.getSize() == 0);
	    
	    return l;
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
