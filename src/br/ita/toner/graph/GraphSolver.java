package br.ita.toner.graph;

import br.ita.toner.data.Graph;
import br.ita.toner.data.Individual;

public class GraphSolver {

	public Individual getAnswer(Graph graph) {
		
		return new Individual(graph.getSparseMatrix(), graph.walk());
	}

}
