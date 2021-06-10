package unit.geometries.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import geometries.Intersection;
import primitives.Point;

/**
 * Helper class for geometry tests.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PointExtractor {
	/**
	 * Extracts the intersection points from a list of {@link Intersection}s
	 *
	 * @param intersections The list of {@link Intersection}s to extract the points from.
	 * @return A set of {@link Points} in the intersections.
	 */
	public static Set<Point> extractPoints(List<Intersection> intersections) {
		return intersections.stream().map(gp -> gp.point).collect(Collectors.toSet());
	}
}
