package geometries;

import primitives.Point;
import primitives.Util;
import primitives.NormalizedVector;
import primitives.Ray;

/**
 * This class represents a cylinder, which is a three-dimensional tube with a height, and closed disks on either end.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Cylinder implements Geometry {
	private double height;

	private Tube tube;
	private Plane base;
	private Plane lid;

	/**
	 * This constructs a Cylinder.
	 *
	 * @param ray    The ray that makes up the center of the Cylinder.
	 * @param radius A positive double that represents the radius.
	 * @param height A positive double that represents the height of the Cylinder.
	 * @throws IllegalArgumentException if the radius is zero or the height is not positive.
	 */
	public Cylinder(Ray ray, double radius, double height) {
		tube = new Tube(ray, radius);
		if (Util.isZero(height) || height < 0) { // if height is 0 it's a disk
			throw new IllegalArgumentException("Error: Height must be a positive number.");
		}
		this.height = height;
		base = new Plane(ray.source(), direction().reversed());
		lid = new Plane(ray.source().add(direction().scale(height)), direction());
	}

	/**
	 * Gets the axis {@link NormalizedVector} of the {@link Cylinder}.
	 *
	 * @return The axis {@link NormalizedVector} of the {@link Cylinder}.
	 */
	public NormalizedVector direction() {
		return tube.direction();
	}

	/**
	 * This function returns the normal vector relative to the {@link Point} p. p is assumed to be on the surface of the
	 * Cylinder. Anything else is undefined behavior.
	 *
	 * @param p The point at which to find the normal vector.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		if (lid.contains(p)) {
			return lid.normal(p);
		}
		if (base.contains(p)) {
			return base.normal(p);
		}
		return tube.normal(p);
	}
}
