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
	private Matrix3x3 rotation;
	private Matrix3x3 rotationInv;

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
		// Get rotation matrix from tube axis to the z-axis
		if (direction().equals(new NormalizedVector(0, 0, 1))) {
			rotation = rotationInv = Matrix3x3.IDENTITY;
		} else {
			rotation = Matrix3x3.getRotation(direction(), new NormalizedVector(0, 0, 1));
			rotationInv = rotation.inverseRotMat();
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
	 * This function will find intersection points between a {@link Ray} and an {@link Cylinder}. The general steps are as
	 * follows: 1) Rotate and shift space such that the cylinders center lies on the origin 2) Project the {@link Ray} r
	 * onto the the xy plane. 3) Find 2 dimensional intersections with with circle about the origin with radius of the
	 * {@link Cylinder}. 4) Bring those intersections back into 3 dimensions 5) Undo the rotation and the shift done at the
	 * beginning.
	 *
	 * @param r The {@link Ray} to intersect
	 * @return a list (possibly empty) of intersection points
	 */
	// @Override
	public List<Point> intersect(Ray r) {
		double x1, y1, x2, y2;
		Vector toCenter = null;
		Vector fromCenter = null;
		Point A, B;
		try {
			toCenter = new Vector(axis.source); // could throw
			fromCenter = toCenter.reversed();
			A = rotation.multiply(r.source.add(fromCenter));
			B = rotation.multiply(r.source.add(r.direction).add(fromCenter));
		} catch (ZeroVectorException e) {
			A = rotation.multiply(r.source);
			B = rotation.multiply(r.source.add(r.direction));
		}
		Vector newDirection = A.vectorTo(B);
		if (DoubleCompare.eq(A.x, B.x)) {
			// y^2 = r^2 - A.x^2
			x1 = x2 = A.x;
			double ySquared = A.x * A.x - radius * radius;
			if (DoubleCompare.leq(ySquared, 0)) {
				return Collections.emptyList();
			}
			y1 = Math.sqrt(ySquared);
			y2 = -y1;
		} else {
			double slope = (A.y - B.y) / (A.x - B.x);
			double yIntercept = (-1 * slope * A.x) + A.y;
			// intersection of line and circle will give a quadratic
			double a = 1 + (slope * slope);
			double b = 2 * slope * (yIntercept);
			double c = (yIntercept * yIntercept) - (radius * radius);
			double det = b * b - 4 * a * c;
			if (DoubleCompare.leq(det, 0.0)) {
				return Collections.emptyList();
			}
			x1 = (-b + Math.sqrt(det)) / (2 * a);
			y1 = (slope * x1) + yIntercept;
			x2 = (-b - Math.sqrt(det)) / (2 * a);
			y2 = (slope * x2) + yIntercept;
		}
		double div;
		if (DoubleCompare.eq(newDirection.x, 0)) {
			div = newDirection.y;
		} else {
			div = newDirection.x;
		}
		if (DoubleCompare.eq(div, 0)) {
			return Collections.emptyList();
		}
		double t1 = (x1 - A.x) / div;
		double t2 = (x2 - A.x) / div;
		List<Point> l = new ArrayList<Point>();
		if (DoubleCompare.gt(t1, 0.0)) {
			double z = A.z + (t1 * newDirection.z);
			Point intersection;
			if (toCenter == null) {
				intersection = rotationInv.multiply(new Point(x1, y1, z));
			} else {
				intersection = rotationInv.multiply(new Point(x1, y1, z)).add(toCenter);
			}
			l.add(intersection);
		}
		if (DoubleCompare.gt(t2, 0.0)) {
			double z = A.z + (t2 * newDirection.z);
			Point intersection;
			if (toCenter == null) {
				intersection = rotationInv.multiply(new Point(x2, y2, z));
			} else {
				intersection = rotationInv.multiply(new Point(x2, y2, z)).add(toCenter);
			}
			l.add(intersection);
		}
		return l;
	}

		// double A = (y_a2 * x_v2) - 2*(y_aX_a * x_vY_v) + (x_a2 * y_v2) +
		// 	(z_a2 * y_v2) - 2*(z_aY_a * y_vZ_v) + (y_a2 * z_v2) + 
		// 	(x_a2 * z_v2) - 2*(x_aZ_a * z_vX_v) + (z_a2 * x_v2);


		// double B = 2*((y_a2 * x_pX_v) - (y_aX_a * x_pY_v) - (y_aX_a * y_pX_v) + (x_a2 * y_pY_v) +
		// 	(z_a2 * y_pY_v) - (z_aY_a * y_pZ_v) - (z_aY_a * z_pY_v) + (y_a2 * z_pZ_v) +
		// 	(x_a2 * z_pZ_v) - (x_aZ_a * x_pZ_v) - (x_aZ_a * z_pX_v) + (z_a2 * x_pX_v));

		// double C = (y_a2*x_p2) - 2*(y_aX_a * x_pY_p) + (x_a2 * y_p2) + 
		// 	(z_a2 * y_p2) - 2*(z_aY_a * z_pY_p) + (y_a2 * z_p2) +
		// 	(x_a2 *z_p2) -2*(x_aZ_a * x_pZ_p) + (z_a2 * x_p2) -
		// 	(radius * radius);

	public List<Point> intersectBetter(Ray r) {
		Point source;
		Vector toOrigin = null, fromOrigin = null, direction;
		boolean tubeIsCentered = false;
		try {
			toOrigin = axis.source.vectorTo(Point.ORIGIN);
			fromOrigin = toOrigin.reversed();
			source = r.source.add(toOrigin);
			direction = r.direction.add(toOrigin);
		} catch (ZeroVectorException e) {
			tubeIsCentered = true;
			source = r.source;
			direction = r.direction;
		}
		Ray shiftedRay = new Ray(source, direction);
		CartesianProductSelf a = new CartesianProductSelf(axis.direction);
		CartesianProductSelf p = new CartesianProductSelf(source); // p for point
		CartesianProductSelf v = new CartesianProductSelf(direction); // v for vector
		CartesianProduct pv = new CartesianProduct(source, direction); // pv for...

		double A = 
			(a.yy * v.xx) - 2*(a.yx * v.yx) + (a.xx * v.yy) +
			(a.zz * v.yy) - 2*(a.zy * v.zy) + (a.yy * v.zz) +
			(a.xx * v.zz) - 2*(a.xz * v.xz) + (a.zz * v.xx);

		double B = 2*(
			(a.yy * pv.xx) - a.yx*(pv.xy + pv.yx) + (a.xx * pv.yy) +
			(a.zz * pv.yy) - a.zy*(pv.yz + pv.zy) + (a.yy * pv.zz) +
			(a.xx * pv.zz) - a.xz*(pv.xz + pv.zx) + (a.zz * pv.xx));

		double C = 
			(a.yy * p.xx) - 2*(a.yx * p.yx) + (a.xx * p.yy) +
			(a.zz * p.yy) - 2*(a.zy * p.zy) + (a.yy * p.zz) +
			(a.xx * p.zz) - 2*(a.xz * p.xz) + (a.zz * p.xx) -
			(radius * radius);

		double det = (B * B) - (4 * A * C); 
		List<Point> intersections = new ArrayList<Point>();
		if (DoubleCompare.gt(det, 0)) {
			double t1 = ((-1)*B + Math.sqrt(det))/ (2*A); // what to do if A is 0?
			double t2 = ((-1)*B - Math.sqrt(det))/ (2*A);
			fillList(intersections, shiftedRay, tubeIsCentered, fromOrigin, t1); 
			fillList(intersections, shiftedRay, tubeIsCentered, fromOrigin, t2);
			return intersections;
		}
		if (intersections.isEmpty()) {
			return Collections.emptyList();
		}
		return intersections;
	}

	// helper function for intersections
	private List<Point> fillList(List<Point> intersections, Ray r, boolean tubeIsCentered, Vector fromOrigin, double t) {
		if (DoubleCompare.leq(t, 0)) {
			return intersections;
		}
		Point p = r.travel(t);
		if (!tubeIsCentered) {
			p.add(fromOrigin);
		}
		intersections.add(p);
		return intersections;
	}

}
