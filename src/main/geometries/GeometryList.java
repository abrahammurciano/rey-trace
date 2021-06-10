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
		// first flatten geometries (to bring all infinite geometries to top level)
		// then put all finite geometries in a sub-geometries (use g.border().isFinite() to determine which ones to add)
		// then construct that one with the constructHierarchy recursive helper function
	}

	private void constructHierarchy() {
		// TODO: implement
		// this is where the fun begins
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
