package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

/**
 * A collection of {@link Intersectible}s which aggregates the intersections between rays and all its elements.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Geometries implements Intersectible {

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

	@Override
	public List<Intersection> intersect(Ray ray, double maxSquareDistance) {
		// TODO: limit by distance
		List<Intersection> result = new LinkedList<>();
		for (Intersectible intersectible : intersectibles) {
			result.addAll(intersectible.intersect(ray));
		}
		return result;
	}
}
