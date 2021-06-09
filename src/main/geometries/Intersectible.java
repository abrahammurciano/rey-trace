package geometries;

import java.util.List;
import primitives.LineSegment;

/**
 * This interface applies to objects which can be intersected with a ray to obtain the points of intersection.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface Intersectible {

	/**
	 * Calculates all the intersections between a given {@link LineSegment} and this object.
	 *
	 * @param line The {@link LineSegment} to check for intersections.
	 * @return A list of all the intersections with the given {@link LineSegment}.
	 */
	public List<Intersection> intersect(LineSegment line);

	public BoundingBox border();

}
