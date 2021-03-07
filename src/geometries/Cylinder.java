package geometries;

import primitives.Point;
import primitives.Util;
import primitives.NormalizedVector;
import primitives.Ray;

/**
 * This class represents a cylinder, which is a three-dimensional tube with a height, and closed
 * disks on either end.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Cylinder extends Tube {
	private double height;

	// Stored for efficiency
	private Plane bottom;
	private Plane top;

	/**
	 * This constructs a Cylinder.
	 *
	 * @param ray    The ray that makes up the center of the Cylinder.
	 * @param radius A positive double that represents the radius.
	 * @param height A positive double that represents the height of the Cylinder. Even if passed as
	 *               a negative number it is stored as a positive number.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Cylinder(Ray ray, double radius, double height) {
		super(ray, radius);
		if (Util.isZero(height)) { // this is a disk
			throw new IllegalArgumentException("Error: Height must be a non-zero number.");
		}
		this.height = Math.abs(height);
		bottom = new Plane(ray.source(), direction());
		top = new Plane(ray.source().add(direction().scale(height)), direction());
	}

	/**
	 * This function returns the normal vector relative to the {@link Point} p. p is assumed to be
	 * on the surface of the Cylinder. Anything else is undefined behavior.
	 *
	 * @param p The point at which to find the normal vector.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		if (top.contains(p)) {
			return direction();
		}
		if (bottom.contains(p)) {
			return direction().reverse().normalized();
		}
		return super.normal(p);
	}
}
