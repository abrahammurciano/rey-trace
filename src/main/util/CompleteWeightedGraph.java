package util;

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * This class represents a complete weighted graph and provides an interface to add vertices and extract the edge with
 * minimal weight.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class CompleteWeightedGraph<V, W extends Comparable<W>> {
	private final HashSet<Edge> edges = new HashSet<>();
	private final HashSet<V> vertices = new HashSet<>();
	private final BiFunction<V, V, W> getWeight;

	/**
	 * Weighted graph constructor.
	 *
	 * @param getWeight A lambda function that recieved two V's and returns a W. Represents the weight of an edge
	 *                  between two vertices.
	 */
	public CompleteWeightedGraph(BiFunction<V, V, W> getWeight) {
		this.getWeight = getWeight;
	}

	public CompleteWeightedGraph(Iterable<V> vertices, BiFunction<V, V, W> getWeight) {
		this(getWeight);
		for (V vertex : vertices) {
			add(vertex);
		}
	}

	public void add(V newVertex) {
		for (V vertex : vertices) {
			edges.add(new Edge(vertex, newVertex)); // no edges with self
		}
		vertices.add(newVertex);
	}

	/**
	 * Remove the edge with the minimum weight and return it.
	 * Assumes queue is not empty.
	 *
	 * @return The minumum weighted edge.
	 */
	public Edge extract() {
		Edge min = edges.stream().min((e1, e2) -> e1.weight.compareTo(e2.weight)).get();
		edges.removeIf(e -> e.contains(min.vertex1) || e.contains(min.vertex2));
		return min;
	}

	/**
	 * Get the size of the graph
	 *
	 * @return the number of vertices in the graph
	 */
	public int size() {
		return vertices.size();
	}

	/**
	 * This class represents a weighted edge of the graph. It contains two vertices (whose order doesnt matter) and a
	 * weight between them.
	 */
	public class Edge {
		public final V vertex1;
		public final V vertex2;
		public final W weight;

		Edge(V vertex1, V vertex2) {
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.weight = getWeight.apply(vertex1, vertex2);

		}

		public boolean contains(V vertex) {
			return vertex.equals(vertex1) || vertex.equals(vertex2);
		}

		@Override
		public int hashCode() {
			return System.identityHashCode(vertex1) * System.identityHashCode(vertex2);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof CompleteWeightedGraph.Edge)) {
				return false;
			}
			CompleteWeightedGraph<?, ?>.Edge other = (CompleteWeightedGraph<?, ?>.Edge) obj;
			return (vertex1 == other.vertex1 && vertex2 == other.vertex2)
				|| (vertex1 == other.vertex2 && vertex2 == other.vertex1);
		}
	}
}
