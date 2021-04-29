package geometries;

import primitives.Point;
import primitives.Ray;
import util.DoubleCompare;
import primitives.ZeroVectorException;
import java.util.Collections;
import java.util.List;
import primitives.NormalizedVector;

/**
 * A {@link Plane} is a flat two dimensional surface in three dimensional space
 * which goes off to infinity in all directions.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Plane implements Geometry {

	public final Point point;
	public final NormalizedVector normal;

	/**
	 * This constructor accepts a point on the plane and a vector perpendicular to
	 * the plane.
	 *
	 * @param point  A point on the plane.
	 * @param normal A normalized vector perpendicular to the plane.
	 */
	public Plane(Point point, NormalizedVector normal) {
		this.point = point;
		this.normal = normal;
	}

	/**
	 * This constructor accepts three distinct points on the plane.
	 *
	 * @param p1 A point on the plane.
	 * @param p2 A point on the plane.
	 * @param p3 A point on the plane.
	 * @throws IllegalArgumentException if the three points are on a single line.
	 */
	public Plane(Point p1, Point p2, Point p3) {
		try {
			this.normal = p1.vectorTo(p2).cross(p2.vectorTo(p3)).normalized();
		} catch (ZeroVectorException e) {
			throw new IllegalArgumentException("Error: The three points must not be on the same line.");
		}
		this.point = p1;
	}

	/**
	 * Checks if the given point is on the plane.
	 *
	 * @param p The point to check.
	 * @return Whether or not the given point is on the plane.
	 */
	public boolean contains(Point p) {
		// If the vector from p to another point is on the plane dot product the normal
		// is zero (the
		// vectors are perpendicular) then the point is on the plane.
		try {
			return DoubleCompare.eq(normal.dot(point.vectorTo(p)), 0);
		} catch (ZeroVectorException e) {
			return true; // if p equals the plane's defining point vectorTo will throw
		}
	}

	@Override
	public NormalizedVector normal(Point p) {
		return normal;
	}

	@Override
	public List<Point> intersect(Ray ray) {
		double ray_dot_normal = ray.direction.dot(normal);
		if (DoubleCompare.eq(ray_dot_normal, 0)) {
			return Collections.emptyList(); // ray is parallel to plane
		}
		double distance;
		try {
			distance = (ray.source.vectorTo(point)).dot(normal) / ray_dot_normal;
		} catch (ZeroVectorException __) {
			return Collections.emptyList(); // plane and ray start at same point
		}
		if (DoubleCompare.leq(distance, 0)) {
			return Collections.emptyList(); // pane is behind the ray
		}
		return List.of(ray.travel(distance));
	}

}
