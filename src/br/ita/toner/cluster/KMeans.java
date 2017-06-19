package br.ita.toner.cluster;

import java.util.ArrayList;
import java.util.List;

import br.ita.toner.data.Individual;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class KMeans {

	private final SimpleKMeans kmeans = new SimpleKMeans();
	
	public KMeans(int numberOfClusters) {
		
		try {
			this.kmeans.setNumClusters(numberOfClusters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Individual> getClusters(Individual requests) throws Exception {
		
		Instances instances = requests.toInstances();
	    
	    kmeans.buildClusterer(instances); 
 
	    List<Individual> l = new ArrayList<Individual>(kmeans.getNumClusters());
	    
	    for (int i = 0; i < kmeans.getNumClusters(); i++)
	    	l.add(new Individual(requests.getSparseMatrix()));
	    
	    for (int i = 0; i < instances.numInstances(); i++)
	    	l.get(kmeans.clusterInstance(instances.instance(i))).getGens().add(requests.getGens().get(i));
	    
	    return l;
	}	
}
