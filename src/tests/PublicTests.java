package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import graph.GraphAlgorithmObserver;
import graph.WeightedGraph;

public class PublicTests {

	@Test
	public void testAddVertexAndContainsVertex() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		assertTrue(graph.containsVertex("A"));
		assertTrue(graph.containsVertex("B"));
		assertTrue(graph.containsVertex("C"));
		assertTrue(graph.containsVertex("D"));
		assertFalse(graph.containsVertex("E"));
	}
	
	@Test
	public void testAddEdgeAndGetWeight() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "C", 2);
		graph.addEdge("A", "D", 3);
		graph.addEdge("B", "C", 4);
		graph.addEdge("D", "C", 5);
		assertTrue(graph.getWeight("A", "B") == 1);
		assertTrue(graph.getWeight("B", "A") == null);
		assertTrue(graph.getWeight("A", "C") == 2);
		assertTrue(graph.getWeight("A", "D") == 3);
		assertTrue(graph.getWeight("B", "C") == 4);
		assertTrue(graph.getWeight("D", "C") == 5);
		boolean caught = false;
		try {
			graph.getWeight("X",  "A");
		} catch (IllegalArgumentException e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			graph.getWeight("A", "X");
		} catch (IllegalArgumentException e) {
			caught = true;
		}
		assertTrue(caught);
		assertTrue(graph.getWeight("B", "D") == null);
	}
	@Test
	public void test1() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("0");
		graph.addVertex("1");
		graph.addVertex("2");
		graph.addVertex("3");
		graph.addVertex("4");
		graph.addVertex("5");
		graph.addVertex("6");
		graph.addVertex("7");
		graph.addVertex("8");
		graph.addEdge("0", "1", 4);
		graph.addEdge("0", "7", 8);
		graph.addEdge("1", "2", 8);
		graph.addEdge("2", "3", 7);
		graph.addEdge("3", "4", 9);
		
		graph.addEdge("7", "8",7);
		graph.addEdge("7", "6", 1);
		graph.addEdge("2", "8", 2);
		graph.addEdge("8", "6", 6);
		graph.addEdge("6", "5", 2);
		graph.addEdge("5", "4", 10);
		
		graph.addEdge("3", "5", 14);
		graph.addEdge("2", "5", 4);
		graph.addEdge("1", "7", 11);
		graph.DoDijsktra("0", "4");
	}
}