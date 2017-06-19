package br.ita.toner.kmeans;

import java.util.List;

import br.ita.toner.data.Individual;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class KMeans {

	public KMeans() {}
	
	public List<Individual> getClusters(Individual requests, int numberOfClusters) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public List<Individual> getClusters(Instances instances, int numberOfClusters) throws Exception { 	
 
	    SimpleKMeans kmeans = new SimpleKMeans();
	    
	    kmeans.setNumClusters(numberOfClusters);
	    
	    kmeans.buildClusterer(instances); 
 
	    for (int i = 0; i < instances.numInstances(); i++) 
	       System.out.println( instances.instance(i) + " is in cluster " + kmeans.clusterInstance(instances.instance(i)) + 1);
	    
	    return null;
	}
}
