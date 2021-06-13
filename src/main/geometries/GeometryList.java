package geometries;

import util.EfficientIterator;
import java.util.LinkedList;
import java.util.List;
import primitives.LineSegment;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;

/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class GeometryList implements Intersectible, Iterable<Geometry> {

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
		// 2. Store these values in some data structure (we'd have to find/make one to efficiently support our
		// operations, possibly some sort of graph)
		// 3.1. Remove the pair {a,b} with the smallest bounding box.
		// 3.2. Remove all pairs {x,a} or {x,b} (i.e. all paid which contain a or b)
		// 4. Make a GeometryList c which contains a and b (note, we can pass the already computed bounding box of a and
		// b into a private constructor so it won't need to recompute it).
		// 5. For each remaining Intersectible x add the pair {c,x} to the data structure and compute the bounding box
		// of c union X.
		// 6. Repeat until there are two Intersectibles i1 and 12 left.
		// 7. this.intersectibles.add(i1, i2)
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
}
