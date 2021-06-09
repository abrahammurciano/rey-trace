package geometries;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import primitives.LineSegment;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;

/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Geometries implements Intersectible, Iterable<Geometry> {

	private List<Intersectible> intersectibles = new LinkedList<>();
	private BoundingBox border;

	/**
	 * Construct a collection of geometries given an array of {@link Intersectible}s or given any number of
	 * {@link Intersectible}s.
	 *
	 * @param intersectibles The {@link Intersectible}s to initialise the collection to.
	 */
	public Geometries(Intersectible... intersectibles) {
		add(intersectibles);
	}

	/**
	 * Add a single {@link Intersectible} to the collection.
	 *
	 * @param intersectible The {@link Intersectible} to add.
	 */
	public void add(Intersectible intersectible) {
		intersectibles.add(intersectible);
		border = border.union(intersectible.border());
	}

	/**
	 * Add many {@link Intersectible}s to the collection.
	 *
	 * @param intersectibles The {@link Intersectible}s to add.
	 */
	public void add(Intersectible... intersectibles) {
		for (Intersectible intersectible : intersectibles) {
			add(intersectible);
		}
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

	private void flatten() {
		// TODO: implement (use geometries iterator)
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
	public class GeometriesIterator implements Iterator<Geometry> {

		private Deque<Iterator<Intersectible>> iterators;
		private Geometry next;

		/**
		 * Construct an iterator which iterates over the geometries in the given {@link Geometries}.
		 *
		 * @param geometries The collection of geometries to iterate over.
		 */
		GeometriesIterator(Geometries geometries) {
			this.iterators = new ArrayDeque<>();
			iterators.add(geometries.intersectibles.iterator());
			calcNext();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * Assigns the next geometry to fetch to the field {@code next}. If there are no more geometries, it assigns
		 * {@code null}.
		 */
		private void calcNext() {
			if (iterators.isEmpty()) {
				next = null;
				return;
			}
			Iterator<Intersectible> top = iterators.peek();
			if (top.hasNext()) {
				Intersectible node = top.next();
				if (node instanceof Geometry) { // node is a leaf
					next = (Geometry) node;
				} else { // intermediate node
					iterators.add(((Geometries) node).intersectibles.iterator());
					calcNext();
				}
			} else {
				iterators.pop();
				calcNext();
			}
		}

		@Override
		public Geometry next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more geometries in this Geometries.");
			}
			Geometry result = next;
			calcNext();
			return result;
		}

	}
}
