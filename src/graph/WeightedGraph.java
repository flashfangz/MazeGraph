package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

import graph.WeightedGraph.Node;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	
	private ArrayList<Node<V>> verticies;
	 class Node<V>
    {
        V key;
        ArrayList<Adjacency> adjacencies;
        public Node(V vertex)
        {
        	key=vertex;
        	adjacencies=new ArrayList<Adjacency>();
        }
        
    }
	
	 class Adjacency{
		int weight;
		Node<V> node;
		public Adjacency(Node<V> n, int w) {
			node=n;
			weight=w;
		}
		
	}
	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	
	public WeightedGraph() {
		verticies= new ArrayList<Node<V>>();
		observerList=new ArrayList<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if (containsVertex(vertex)) {
			throw new IllegalArgumentException("Vertex already in graph");
		}
		else {
			Node<V> Vertex= new Node<V>(vertex);
			verticies.add(Vertex);
		}
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		for(int i=0;i<verticies.size();i++) {
			if (verticies.get(i).key.equals(vertex)) {
				return true;
			}
		}
		return false;
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if(weight<0) {
			throw new IllegalArgumentException("weight is a negative integer");
		}
		else if(!containsVertex(from)) {
			throw new IllegalArgumentException("From vertex is not found");
		}
		else if(!containsVertex(to)) {
			throw new IllegalArgumentException("To vertex is not found");
		}
		else {
			for(int i=0;i<verticies.size();i++) {
				if (verticies.get(i).key.equals(from)) {
					for(int k=0;k<verticies.size();k++) {
						Node<V> To= verticies.get(k);
						if (verticies.get(k).key.equals(to)) {
							Adjacency e= new Adjacency(To,weight);
							if(!verticies.get(i).adjacencies.contains(e))
								verticies.get(i).adjacencies.add(e);
						}
					
					}
				}
			}
		}
		
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if(!containsVertex(from)) {	
			throw new IllegalArgumentException("From vertex is not found");
		}
		
		else if(!containsVertex(to)) {
			throw new IllegalArgumentException("To vertex is not found");
		}
		
		else if (verticies.size()<2) {
			throw new IllegalArgumentException("Not enough verticies");
		}
		
		else {
			for(int i=0;i<verticies.size();i++) {
				if (verticies.get(i).key.equals(from)) {
					for(int k=0;k<verticies.get(i).adjacencies.size();k++) {
						if(verticies.get(i).adjacencies.get(k).node.key.equals(to)){
							return verticies.get(i).adjacencies.get(k).weight;
						}
					}
					return null;
				}
			}
			return null;
		}
	}
	public Node<V> findNode(V key){
		for(int i=0;i<verticies.size();i++) {
			if(verticies.get(i).key.equals(key)) {
				return verticies.get(i);
			}
		}
		return null;
	}
	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		
		for (int i=0;i<observerList.size();i++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifyBFSHasBegun();
		}
		ArrayList<V> visited=new ArrayList<V>();
		ArrayList<V> searchPath=new ArrayList<V>();
		Node <V> startNode= this.findNode(start);
		visited.add(startNode.key);
		searchPath.add(startNode.key);
		
		for (int k=0;k<observerList.size();k++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(startNode.key);
		}		
		
		if(startNode!=null && end!=null && !startNode.key.equals(end)) {
			BFS(startNode.adjacencies,end, searchPath, visited);
		}
		/*
		for(V element : visited) {
			for (int k=0;k<observerList.size();k++) {
				((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(element);
			}
		}
		*/
		
		for (int i=0;i<observerList.size();i++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifySearchIsOver();
		}
	}
	
	//check node
	public boolean BFS(Node<V>startNode,V end, ArrayList<V> searchPath, ArrayList<V> visited) {
		if(visited.contains(startNode.key)) {
			return false;
		}
		visited.add(startNode.key);	
		
		for (int k=0;k<observerList.size();k++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(startNode.key);
		}
		
		
		if(startNode.key.equals(end)) {
			searchPath.add(startNode.key);
			return true;
		}
		return BFS(startNode.adjacencies,end,searchPath, visited);

	}

	// check node's children
	public boolean BFS(ArrayList<Adjacency> adjacencies,V end, ArrayList<V> searchPath, ArrayList<V> visited) {
		
		if(!adjacencies.isEmpty()) {
			
			for (WeightedGraph<V>.Adjacency element : adjacencies) {
				
				if(visited.contains(element.node.key)) {
					continue;
				}	
				
				for (int k=0;k<observerList.size();k++) {
					((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(element.node.key);
				}
				
				visited.add(element.node.key);
				if(element.node.key.equals(end)) {
					searchPath.add(element.node.key);
					return true;
				}
			}
			ArrayList<Adjacency> children = new ArrayList<Adjacency>();
			for (WeightedGraph<V>.Adjacency element : adjacencies) {
				for (WeightedGraph<V>.Adjacency child : element.node.adjacencies) {
					if(!children.contains(child) && !visited.contains(child.node.key))
						children.add(child);
				}
			}
			if(!children.isEmpty() && BFS(children,end,searchPath, visited)) {
				return true;
			}
		}
		return false;
	}
	

	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		
		for (int i=0;i<observerList.size();i++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifyDFSHasBegun();
		}
		Node <V> startNode= this.findNode(start);
		ArrayList<V> visited=new ArrayList<V>();
		ArrayList<V> searchPath=new ArrayList<V>();
		if(startNode!=null && end!=null && !startNode.key.equals(end)) {
				DFS(startNode,end,searchPath, visited);
		}
		
		
		for(V element : searchPath) {
			for (int k=0;k<observerList.size();k++) {
				((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(element);
			}
		}
		

		for (int i=0;i<observerList.size();i++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifySearchIsOver();
		}
		
	}
	public boolean DFS(Node<V>startNode,V end,ArrayList<V> searchPath, ArrayList<V> visited) {
		if(visited.contains(startNode.key)) {
			return false;
		}
		visited.add(startNode.key);	
		searchPath.add(startNode.key);

		if(startNode!=null && !startNode.key.equals(end)) {
			if(startNode.adjacencies.isEmpty()) {
				searchPath.remove(startNode.key);		
				return false;
			} 
			else {
				for (WeightedGraph<V>.Adjacency element : startNode.adjacencies) {
					if(DFS(element.node,end,searchPath, visited) == true) {
						/*	for (int k=0;k<observerList.size();k++) {
						((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(startNode.key);
					}
					*/
						return true;
					}
				}
				searchPath.remove(startNode.key);		
				return false;
			}
		} 
	/*	for (int k=0;k<observerList.size();k++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(k).notifyVisit(startNode.key);
		}
		*/
		return true;
		
	}
	
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for (int i=0;i<observerList.size();i++) {
			((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifyDijkstraHasBegun();
		}
		
		HashMap<Node<V>, Node<V>> predecessors = new HashMap<Node<V>, Node<V>>();
		HashMap<Node<V>, Integer> nodeWeights = new HashMap<Node<V>, Integer>();
		ArrayList<Node<V>> visited= new ArrayList<Node<V>>();
		ArrayList<Node<V>> calculating= new ArrayList<Node<V>>();
		Node <V> startNode = this.findNode(start);
		if(startNode!=null) {	
			nodeWeights.put(startNode, 0);
			calculating.add(startNode);
			while(calculating.size() > 0) {
				Node<V> node = getClosestNode(calculating, nodeWeights);
				visited.add(node);
				calculating.remove(node);
				for (int i=0;i<observerList.size();i++) {
					((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifyDijkstraVertexFinished(node.key, lookupWeight(node, nodeWeights));
				}

				
		        List<Node<V>> adjacentNodes = getUnvisitedAdjacentNodes(node, visited);
		        for (Node<V> target : adjacentNodes) {
		            if (lookupWeight(target, nodeWeights) > lookupWeight(node, nodeWeights)
		                    + getWeight(node.key, target.key)) {
		            	nodeWeights.put(target, lookupWeight(node, nodeWeights) + getWeight(node.key, target.key));
		                predecessors.put(target, node);
		                calculating.add(target);
		            }
		        }
		    }
		}
		
		ArrayList<V> pathToEnd = new ArrayList<V>();
		Node<V> step = this.findNode(end);
		if(predecessors.containsKey(step)) {
			pathToEnd.add(step.key);
			while (predecessors.get(step) != null) {
				step = predecessors.get(step);
				pathToEnd.add(step.key);
			}
			Collections.reverse(pathToEnd);
			for (int i=0;i<observerList.size();i++) {
				((ArrayList<GraphAlgorithmObserver<V>>) observerList).get(i).notifyDijkstraIsOver(pathToEnd);
			}

		}
		
		
	}
	
	public int lookupWeight(Node<V> node, HashMap<Node<V>, Integer> nodeWeights)  {
		Integer d = nodeWeights.get(node);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }		
	}
	
	
	public Node<V> getClosestNode(ArrayList<Node<V>> nodes, HashMap<Node<V>, Integer> nodeWeights) {
		Node<V> closest = null;
        for (Node<V> node : nodes) {
            if (closest == null) {
            	closest = node;
            } else {
                if (lookupWeight(node, nodeWeights) < lookupWeight(closest, nodeWeights)) {
                	closest = node;
                }
            }
        }
        return closest;		
	}
	
	
    public List<Node<V>> getUnvisitedAdjacentNodes(Node<V> node, ArrayList<Node<V>> visited) {
    	List<Node<V>> adjNodes = new ArrayList<Node<V>>();
    	for(Adjacency adj : node.adjacencies) {
    		if(!visited.contains(adj.node)) {
    			adjNodes.add(adj.node);
    		}
    	}
    	return adjNodes;
    }
	

}
