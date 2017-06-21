package br.ita.toner.cluster;

import java.util.List;

public interface Clusterable<T> {
	public List<T> getClusters(int clusterSize); 
}
