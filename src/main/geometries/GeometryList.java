package geometries;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import primitives.LineSegment;
import util.EfficientIterator;


/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class GeometryList implements Boundable, Iterable<Geometry> {

	private List<Intersectible> intersectibles = new LinkedList<>();
	private BoundingBox border;

	/**
	 * Construct a collection of geometries given an array of {@link Intersectible}s or given any number of
	 * {@link Intersectible}s.
	 *
	 * @param geometries The {@link GeometryList}s to initialise the collection to.
	 */
	public GeometryList(Geometry... geometries) {
		add(geometries);
	}

	private GeometryList() {}

	/**
	 * Add a single {@link Geometry} to the collection.
	 *
	 * @param geometry The {@link Geometry} to add.
	 */
	public void add(Geometry geometry) {
		intersectibles.add(geometry);
		border = border.union(geometry.border());
	}

	/**
	 * Add many {@link Geometry}s to the collection.
	 *
	 * @param geometries The {@link Geometry}s to add.
	 */
	public void add(Geometry... geometries) {
		for (Geometry geometry : geometries) {
			add(geometry);
		}
	}

	/**
	 * Add all the {@link Geometry}s in the given {@link GeometryList} to the collection.
	 *
	 * @param geometries The {@link GeometryList} whose elements to add.
	 */
	public void add(GeometryList geometries) {
		for (Geometry geometry : geometries) {
			intersectibles.add(geometry);
		}
		border = border.union(geometries.border());
	}

	/**
	 * Restructures the internal structure of the geometries for optimal ray tracing. This method should be called after
	 * all the geometries have been added, but before the ray tracing process begins.
	 */
	public void optimize() {
		// TODO: implement
		// move all finite geometries to a sub-geometries (use g.border().isFinite() to determine which ones to add)
		finiteGeometries.constructHierarchy();
		this.intersectibles.add(finiteGeometries);
	}

	private void constructHierarchy() {
		// TODO: implement
		// this is where the fun begins
                // An overview of an algorithm might go as follows
                // 1. For each unordered pair of geometries {a,b} calculate the SA of the Union of their bounding boxes
                // 2. Store these values in some data structure (we'd have to find/make one to efficiently support our operations, possibly some sort of graph)
                // 3.1. Remove the pair {a,b} with the smallest bounding box.
                // 3.2. Remove all pairs {x,a} or {x,b} (i.e. all paid which contain a or b)
                // 4. Make a GeometryList c which contains a and b (note, we can pass the already computed bounding box of a and b into a private constructor so it won't need to recompute it). 
                // 5. For each remaining Intersectible x add the pair {c,x} to the data structure and compute the bounding box of c union X.
                // 6. Repeat until there are two Intersectibles i1 and 12 left.
                // 7. this.intersectibles.add(i1, i2)
				//
		WeightedGraph<Boundable, BoundingBox> G = new WeightedGraph<Boundable, BoundingBox>(
				(a,b) -> a.border().union(b.border()),
				(a,b) -> new GeometryList().add(/*WHAT DO I ADD HERE*/);

		for (Intersectible i : intersectibles) {
			G.add(i);

		}
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		if (!border().intersects(line)) {
			return Collections.emptyList();
		}
		List<Intersection> result = new LinkedList<>();
		for (Intersectible intersectible : intersectibles) {
			result.addAll(intersectible.intersect(line));
		}
		return result;
	}

	@Override
	public BoundingBox border() {
		return border;
	}

	@Override
	public Iterator<Geometry> iterator() {
		return new GeometriesIterator(this);
	}

	/**
	 * Iterates over the leaves of the geometries hierarchy as if they were a flat collection of {@link Geometry}s.
	 */
	public class GeometriesIterator extends EfficientIterator<Geometry> {

		private Deque<Iterator<Intersectible>> iterators;

		/**
		 * Construct an iterator which iterates over the geometries in the given {@link GeometryList}.
		 *
		 * @param geometries The collection of geometries to iterate over.
		 */
		GeometriesIterator(GeometryList geometries) {
			this.iterators = new ArrayDeque<>();
			iterators.add(geometries.intersectibles.iterator());
			setNext();
		}

		@Override
		protected void setNext() {
			if (iterators.isEmpty()) {
				hasNext = false;
			} else {
				Iterator<Intersectible> top = iterators.peek();
				if (top.hasNext()) {
					Intersectible node = top.next();
					if (node instanceof Geometry) { // node is a leaf
						next = (Geometry) node;
					} else { // intermediate node
						iterators.add(((GeometryList) node).intersectibles.iterator());
						setNext();
					}
				} else {
					iterators.pop();
					setNext();
				}
			}
		}

	}

	// V should be Comparable to itself
	// In our case V will be a something that extends Boundable and W will be the Bounding Box that bounds both of the Boundable objects
	private class WeightedGraph<V, W extends Comparable<W>> {
		private HashSet<Edge<V,W>> edges;
		private HashSet<V> vertices;
		BiFunction<V, V, W> getWeight;
		BinaryOperator<V> joinVertices; // takes 2 V's and returns a new V that is the 'union' of them.



		/**
		 * Weighter graph ctor
		 * @param getWeight A lambda function that recieved two V's and returns a W. Represents the weight of an edge between two vertices.
		 * @param joinVertices A lambda function tha recieves two V's and returns a V. Represents the 'union' of two vertices.
		 */
		WeightedGraph(BiFunction<V, V, W> getWeight, BinaryOperator<V> joinVertices) {
			this.getWeight = getWeight;
			this.joinVertices = joinVertices;
			this.edges = new HashSet<Edge<V,W>>();
			this.vertices = new HashSet<V>();
		}

		void add(V newVertex) {
			for(V vertex : vertices) {
				edges.add(newEdge(vertex, newVertex)); // no edges with self
			}
			vertices.add(newVertex);
		}

		// creates a new Edge
		private Edge<V,W> newEdge(V vertex1, V vertex2) {
			return new Edge<V,W>(vertex1, vertex2, getWeight.apply(vertex1, vertex2));
		}

		/**
		 * Remove the edge with the minimum weight, join the 2 vertices that connected that edge,
		 * and connect that new vertex to each of the old vertices in the graph
		 * Assumes queue is not empty.
		 *
		 * @return The new vertex that was created during this opertation
		 */
		V extract() {
			Iterator<Edge<V,W>> i = edges.iterator();
			Edge<V,W> min, next;
			min = i.next(); // initialize it
			while (i.hasNext()) {
				next = i.next();
				if (min.weight.compareTo(next.weight) > 0) { // not backwards?
					min = next;
				}
			}
			final Edge<V,W> realMin = min; // bc no captures

			edges.removeIf(e -> {
				return e.vertex1 == realMin.vertex1 || e.vertex1 == realMin.vertex2
					|| e.vertex2 == realMin.vertex1 || e.vertex2 == realMin.vertex1;
			});

			V ret = joinVertices.apply(min.vertex1, min.vertex2);
			add(ret); // into the hashSet
			return ret;
		}

		int size() {
			return vertices.size();
		}

		// this class is really more of a struct. No logic in it, just a group of data.
		private class Edge<VE,WE> {
			private VE vertex1;
			private VE vertex2;
			private WE weight;
			Edge(VE vertex1, VE vertex2, WE weight) {
				this.vertex1 = vertex1;
				this.vertex2 = vertex2;
				this.weight = weight;

			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + getEnclosingInstance().hashCode();
				result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
				result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
				result = prime * result + ((weight == null) ? 0 : weight.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Edge other = (Edge) obj;
				if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
					return false;
				if (vertex1 == null) {
					if (other.vertex1 != null)
						return false;
				} else if (!vertex1.equals(other.vertex1))
					return false;
				if (vertex2 == null) {
					if (other.vertex2 != null)
						return false;
				} else if (!vertex2.equals(other.vertex2))
					return false;
				if (weight == null) {
					if (other.weight != null)
						return false;
				} else if (!weight.equals(other.weight))
					return false;
				return true;
			}

			private WeightedGraph getEnclosingInstance() {
				return WeightedGraph.this;
			}
		}
	}
}
