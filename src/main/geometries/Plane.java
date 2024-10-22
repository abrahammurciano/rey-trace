package geometries;

import java.util.Collections;
import java.util.List;

import math.compare.DoubleCompare;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.ZeroVectorException;

/**
 * A {@link Plane} is a flat two dimensional surface in three dimensional space
 * which goes off to infinity in all directions.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Plane extends InfiniteGeometry {
	/** A point on the plane. */
	public final Point point;
	/** The normal vector of the plane. */
	public final NormalizedVector normal;

	/**
	 * This constructor accepts a point on the plane and a vector perpendicular to
	 * the plane.
	 *
	 * @param material The {@link Material} the plane is made from.
	 * @param point    A point on the plane.
	 * @param normal   A normalized vector perpendicular to the plane.
	 */
	public Plane(Material material, Point point, NormalizedVector normal) {
		super(material);
		this.point = point;
		this.normal = normal;
	}

	/**
	 * This constructor accepts three distinct points on the plane.
	 *
	 * @param material The {@link Material} the plane is made from.
	 * @param p1       A point on the plane.
	 * @param p2       A point on the plane.
	 * @param p3       A point on the plane.
	 * @throws IllegalArgumentException if the three points are on a single line.
	 */
	public Plane(Material material, Point p1, Point p2, Point p3) {
		super(material);
		try {
			this.normal = p1.nonZeroVectorTo(p2).cross(p2.nonZeroVectorTo(p3)).normalized();
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
	boolean contains(Point p) {
		// If the vector from p to another point is on the plane dot product the normal is zero (the vectors are
		// perpendicular) then the point is on the plane.
		return DoubleCompare.eq(normal.dot(point.vectorTo(p)), 0);
	}

	@Override
	public NormalizedVector normal(Point p) {
		return normal;
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		double ray_dot_normal = line.direction.dot(normal);
		if (DoubleCompare.eq(ray_dot_normal, 0)) {
			return Collections.emptyList(); // ray is parallel to plane
		}
		double distance = (line.start.vectorTo(point)).dot(normal) / ray_dot_normal;
		Point intersectionPoint = line.travel(distance);
		return intersectionPoint != null ? List.of(intersection(intersectionPoint)) : Collections.emptyList();
	}

}
