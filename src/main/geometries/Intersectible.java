package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * This interface applies to objects which can be intersected with a ray to
 * obtain the points of intersection.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface Intersectible {
	public List<Point> intersect(Ray ray);
}
