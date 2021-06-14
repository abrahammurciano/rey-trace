package geometries;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import primitives.LineSegment;
import util.EfficientIterator;
import util.CompleteWeightedGraph;


/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class GeometryList implements Intersectible, Iterable<Geometry> {

	private Set<Intersectible> intersectibles = new HashSet<>();
	private Boundary boundary = Boundary.EMPTY;

	/**
	 * Construct a collection of geometries given an array of {@link Intersectible}s or given any number of
	 * {@link Intersectible}s.
	 *
	 * @param geometries The {@link GeometryList}s to initialise the collection to.
	 */
	public GeometryList(Geometry... geometries) {
		add(geometries);
	}

	private GeometryList(Boundary boundary, Intersectible... intersectibles) {
		for (Intersectible intersectible : intersectibles) {
			this.intersectibles.add(intersectible);
		}
		this.boundary = boundary; // #efficient
	}

	/**
	 * Add a single {@link Geometry} to the collection.
	 *
	 * @param geometry The {@link Geometry} to add.
	 */
	private void add(Intersectible geometry) {
		intersectibles.add(geometry);
		boundary = boundary.union(geometry.boundary());
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
		geometries.forEach(intersectibles::add);
		boundary = boundary.union(geometries.boundary());
	}

	/**
	 * Restructures the internal structure of the geometries for optimal ray tracing. This method should be called after
	 * all the geometries have been added, but before the ray tracing process begins.
	 */
	public void optimize() {
		GeometryList finites = intersectibles.stream().filter(g -> g.boundary().isFinite()).collect(GeometryList::new,
			GeometryList::add, GeometryList::add);
		if (finites.intersectibles.size() == intersectibles.size()) {
			constructHierarchy();
		} else {
			finites.forEach(intersectibles::remove);
			finites.constructHierarchy();
			intersectibles.add(finites);
		}
	}

	private void constructHierarchy() {
		if (intersectibles.size() <= 2) {
			return;
		}
		CompleteWeightedGraph<Intersectible, Boundary> G =
			new CompleteWeightedGraph<>(intersectibles, (i1, i2) -> i1.boundary().union(i2.boundary()));
		CompleteWeightedGraph<Intersectible, Boundary>.Edge minEdge;
		while (G.size() > 2) {
			minEdge = G.extract();
			G.add(new GeometryList(minEdge.weight, minEdge.vertex1, minEdge.vertex2));
		}
		minEdge = G.extract();
		intersectibles = new HashSet<>();
		intersectibles.add(minEdge.vertex1);
		intersectibles.add(minEdge.vertex2);
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		if (!boundary().intersects(line)) {
			return Collections.emptyList();
		}
		List<Intersection> result = new LinkedList<>();
		for (Intersectible intersectible : intersectibles) {
			result.addAll(intersectible.intersect(line));
		}
		return result;
	}

	@Override
	public Boundary boundary() {
		return boundary;
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
}
