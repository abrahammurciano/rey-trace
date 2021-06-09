package geometries;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import primitives.LineSegment;

/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Geometries implements Intersectible, Iterable<Geometry> {

	private List<Intersectible> intersectibles = new LinkedList<>();

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

	private void constructHierarchy() {
		// TODO: implement
		// first flatten geometries (to bring all infinite geometries to top level)
		// then put all finite geometries in a sub-geometries (use g.border().isFinite())
		// then construct that one with a recursive helper function
	}

	private void flatten() {
		// TODO: implement (use geometries iterator)
	}

	private void constructHierarchyHelper() {
		// TODO: implement
		// this is where the fun begins
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		List<Intersection> result = new LinkedList<>();
		for (Intersectible intersectible : intersectibles) {
			result.addAll(intersectible.intersect(line));
		}
		return result;
	}

	@Override
	public BoundingBox border() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Geometry> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
