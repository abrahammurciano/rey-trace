package geometries;

import static java.lang.System.out;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import primitives.Matrix3x3;
import primitives.NormalizedVector;
import primitives.Point;
import util.DoubleCompare;
import util.CartesianProduct;
import util.CartesianProductSelf;
import primitives.Vector;
import primitives.Ray;
import primitives.Triple;

/**
 * A Tube is a 3D tube object that goes on to infinity.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Tube implements Geometry {
	private Ray axis;
	private double radius;
	private Vector toOrigin, fromOrigin;

	/**
	 * Constructs a {@link Tube} with the source at the same source and direction as the given axis {@link Ray}.
	 *
	 * @param axis The {@link Ray} from which to get the source and direction.
	 * @param radius The distance from the axis to the surface.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Tube(Ray axis, double radius) {
		if (DoubleCompare.eq(radius, 0)) {
			throw new IllegalArgumentException("Error: Radius must be non-zero.");
		}
		this.axis = axis;
		this.radius = Math.abs(radius);

		if (!axis.source.equals(Point.ORIGIN)) {
			toOrigin = axis.source.vectorTo(Point.ORIGIN);
			fromOrigin = toOrigin.reversed();
		}
	}

	/**
	 * Gets the axis {@link Vector} of the {@link Tube}.
	 *
	 * @return The axis {@link Vector} of the {@link Tube}.
	 */
	public NormalizedVector direction() {
		return axis.direction;
	}

	/**
	 * This function returns the normal to the tube at the given point. If the point doesn't lie on the surface of the tube,
	 * the behavior is undefined.
	 *
	 * @param p The {@link Point} to get the normal at.
	 * @return The normalized normal {@link Vector}
	 * @throws ZeroVectorException if the p is equal to the source @{@link Point} of the {@link Tube}.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		Vector sourceToP = axis.source.vectorTo(p);
		double dotProduct = direction().dot(sourceToP);
		if (DoubleCompare.eq(dotProduct, 0)) { // Would throw a zero vector exception if not checked
			return sourceToP.normalized();
		}
		return sourceToP.subtract(direction().scale(dotProduct)).normalized();
	}



	/**
	 * This function will find intersection points (possibly none) between a {@link Ray} and an {@link Cylinder}.
	 *
	 * @param r The {@link Ray} to intersect
	 * @return a list (possibly empty) of intersection points
	 */
	// @Override
	public List<Point> intersect(Ray r) {
		Point source;
		Vector direction;
		boolean tubeIsCentered = false;
		if (!axis.source.equals(Point.ORIGIN)) {
			source = r.source.add(toOrigin);
			direction = r.direction.add(toOrigin);
		} else {
			tubeIsCentered = true;
			source = r.source;
			direction = r.direction;
		}
		Ray shiftedRay = new Ray(source, direction);
		CartesianProductSelf a = new CartesianProductSelf(axis.direction);
		CartesianProductSelf p = new CartesianProductSelf(source); // p for point
		CartesianProductSelf v = new CartesianProductSelf(direction); // v for vector
		CartesianProduct pv = new CartesianProduct(source, direction); // pv for...

		// @formatter:off
		double A =
			(a.yy * v.xx) - 2*(a.yx * v.yx) + (a.xx * v.yy) +
			(a.zz * v.yy) - 2*(a.zy * v.zy) + (a.yy * v.zz) +
			(a.xx * v.zz) - 2*(a.xz * v.xz) + (a.zz * v.xx);

		double B = 2 * (
			(a.yy * pv.xx) - a.yx*(pv.xy + pv.yx) + (a.xx * pv.yy) +
			(a.zz * pv.yy) - a.zy*(pv.yz + pv.zy) + (a.yy * pv.zz) +
			(a.xx * pv.zz) - a.xz*(pv.xz + pv.zx) + (a.zz * pv.xx));

		double C = 
			(a.yy * p.xx) - 2*(a.yx * p.yx) + (a.xx * p.yy) +
			(a.zz * p.yy) - 2*(a.zy * p.zy) + (a.yy * p.zz) +
			(a.xx * p.zz) - 2*(a.xz * p.xz) + (a.zz * p.xx) -
			(radius * radius);
		// @formatter:on

		// use abes quadratic thingy instead
		double det = (B * B) - (4 * A * C);
		List<Point> intersections = new ArrayList<Point>();
		if (DoubleCompare.gt(det, 0)) {
			double t1 = ((-1) * B + Math.sqrt(det)) / (2 * A); // what to do if A is 0?
			double t2 = ((-1) * B - Math.sqrt(det)) / (2 * A);
			fillList(intersections, shiftedRay, tubeIsCentered, fromOrigin, t1);
			fillList(intersections, shiftedRay, tubeIsCentered, fromOrigin, t2);
		}
		if (intersections.isEmpty()) {
			return Collections.emptyList();
		}
		return intersections;
	}

	// helper function for intersection
	private void fillList(List<Point> intersections, Ray r, boolean tubeIsCentered,
			Vector fromOrigin, double t) {
		if (DoubleCompare.leq(t, 0)) {
			return;
		}
		Point p = r.travel(t);
		if (!tubeIsCentered) {
			p.add(fromOrigin);
		}
		intersections.add(p);
	}

}
