package br.ita.toner.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* Each paint can be visualized as a node in a graph,
 * then, recipes with sharing paints will create an edge between these paint.
 */
public class PaintGraphCluster implements Clusterable<List<BitSet>> {
	
	//graph
	private List<Integer> graph[];
	
	//for each color, store the recipes assigned
	private Set<Integer> recipesPerPaint[];
	
	//visited for paints
	private boolean visitedPaint[];
	
	//visited for recipes
	private boolean visitedRecipe[];
	
	//receitas
	protected List<BitSet> receitas;
	
	//alocating memory for global variables
	public PaintGraphCluster(List<BitSet> receitas, int paints) {
		this.receitas = receitas;
		graph = (List<Integer>[]) new List[paints];
		recipesPerPaint = (Set<Integer>[]) new Set[paints];
		for(int i = 0; i < paints; i++) {
			graph[i] = new LinkedList<>();
			recipesPerPaint[i] = new HashSet<>();
		}
		visitedPaint = new boolean[paints];
		visitedRecipe = new boolean[receitas.size()];
		
		//initializing graph and sorting
		makeGraph(receitas, paints);
		sortGraph(paints);
	}
	
	//for each recipe, creating a 'click' for the active paints
	private void makeGraph(List<BitSet> receitas, int paints) {
		for(int i = 0; i < receitas.size(); i++) {
			List<Integer> activePaints = new ArrayList<>();
			BitSet current = receitas.get(i);
			for(int j = 0; j < paints; j++) {
				if(current.get(j)) {
					activePaints.add(j);
					recipesPerPaint[j].add(i);
				}
			}
			for(int j = 0; j < activePaints.size(); j++) {
				for(int k = j + 1; k < activePaints.size(); k++) {
					int u = activePaints.get(j);
					int v = activePaints.get(k);
					graph[u].add(v);
					graph[v].add(u);
				}
			}
			activePaints.clear();
		}
	}
	
	//sorting the visit order by lowest neighboor degree
	private void sortGraph(int paints){
		for(int i = 0; i < paints; i++) {
			graph[i].sort(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					if(graph[o1].size() == graph[o2].size()) {
						return 0;
					}else {
						return graph[o1].size() < graph[o2].size() ? -1 : 1;
					}
				}
			});
		}
	}
	
	//
	private class PaintVisitOrder implements Comparable<PaintVisitOrder>{
		private int paint;
		private int degree;
		
		public PaintVisitOrder(int paint, int degree) {
			this.paint = paint;
			this.degree = degree;
		}
		
		@Override
		public int compareTo(PaintVisitOrder o) {
			if(degree == o.degree) {
				return 0;
			}else {
				return degree < o.degree ? -1 : 1;
			}
		}
	}
	
	private void dfs(int currentPaint, List<Integer> recipeOrder) {
		visitedPaint[currentPaint] = true;
		for(Integer recipe : recipesPerPaint[currentPaint]) {
			if(!visitedRecipe[recipe]) {
				recipeOrder.add(recipe);
				visitedRecipe[recipe] = true;
			}
		}
		for(Integer childPaint : graph[currentPaint]) {
			if(!visitedPaint[childPaint]) {
				dfs(childPaint, recipeOrder);
			}
		}
	}
	
	@Override
	public List<List<BitSet>> getClusters(int clusterSize) {
		List<PaintVisitOrder> order = new ArrayList<PaintVisitOrder>();
		for(int i = 0; i < graph.length; i++) {
			order.add(new PaintVisitOrder(i, graph[i].size()));
		}
		Collections.sort(order);
		List<Integer> recipeOrder = new LinkedList<>();
		for(int i = 0; i < order.size(); i++) {
			int currentPaint = order.get(i).paint;
			if(!visitedPaint[currentPaint]) {
				dfs(currentPaint, recipeOrder);
			}
		}
		List<List<BitSet>> answer = new ArrayList<>();
		int index = 0;
		while(index < recipeOrder.size()) {
			List<BitSet> currentCluster = new ArrayList<BitSet>();
			while(index < recipeOrder.size() && currentCluster.size() < clusterSize) {
				BitSet selectedRecipe = (BitSet) receitas.get(recipeOrder.get(index)).clone();
				currentCluster.add(selectedRecipe);
				index++;
			}
			answer.add(currentCluster);
		}
		return answer;
	}
}
