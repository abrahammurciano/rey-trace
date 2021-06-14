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
	 * Construct a complete weighted graph which uses the given function to compute weghts of edges between vertices.
	 *
	 * @param getWeight A function which computes the weight between two given vertices.
	 */
	public CompleteWeightedGraph(BiFunction<V, V, W> getWeight) {
		this.getWeight = getWeight;
	}

	/**
	 * Construct a complete weighted graph with the given vertices and the given function which calculates the weights
	 * between them.
	 *
	 * @param vertices  The vertices to add to the graph.
	 * @param getWeight A function which computes the weight between two given vertices.
	 */
	public CompleteWeightedGraph(Iterable<V> vertices, BiFunction<V, V, W> getWeight) {
		this(getWeight);
		for (V vertex : vertices) {
			add(vertex);
		}
	}

	/**
	 * Add a vertex to the complete graph, adding an edge to each existing vertex and calculating the weight of each of
	 * these.
	 *
	 * @param vertex The vertex to add to the graph.
	 */
	public void add(V vertex) {
		for (V existing : vertices) {
			edges.add(new Edge(existing, vertex)); // no edges with self
		}
		vertices.add(vertex);
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
		/** One of the vertices of the edge. */
		public final V vertex1;
		/** The other vertex of the edge. */
		public final V vertex2;
		/** The weight of the edge. */
		public final W weight;

		Edge(V vertex1, V vertex2) {
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.weight = getWeight.apply(vertex1, vertex2);

		}

		/**
		 * Checks if the given vertex is one of the vertices of the edge.
		 *
		 * @param vertex The vertex to check.
		 * @return true if the vertex is one of the vertices of the edge, or false otherwise.
		 */
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
