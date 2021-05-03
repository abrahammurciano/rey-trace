package primitives;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Triple;
import primitives.Vector;
import primitives.ZeroVectorException;
import util.CartesianProductSelf;
import util.NormalCompare;

/**
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Matrix3x3 {

	// rows of matrix
	public Vector r1;
	public Vector r2;
	public Vector r3;
	public static final Matrix3x3 IDENTITY =
		new Matrix3x3(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));

	public Matrix3x3(Vector r1, Vector r2, Vector r3) {
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}

	public Matrix3x3(Matrix3x3 A) {
		this(A.r1, A.r2, A.r3);
	}

	public Matrix3x3 scale(double t) {
		r1 = r1.scale(t);
		r2 = r2.scale(t);
		r3 = r3.scale(t);
		return this;
	}

	public Point multiply(Point p) {
		return new Point(r1.dot(p), r2.dot(p), r3.dot(p));
	}

	public Vector multiply(Vector v) {
		return new Vector(r1.dot(v), r2.dot(v), r3.dot(v));
	}

	public Matrix3x3 multiply(Matrix3x3 A) {
		Vector col1 = new Vector(A.r1.x, A.r2.x, A.r3.x);
		Vector col2 = new Vector(A.r1.y, A.r2.y, A.r3.y);
		Vector col3 = new Vector(A.r1.z, A.r2.z, A.r3.z);
		return new Matrix3x3(new Vector(r1.dot(col1), r1.dot(col2), r1.dot(col3)),
			new Vector(r2.dot(col1), r2.dot(col2), r2.dot(col3)), new Vector(r3.dot(col1), r3.dot(col2), r3.dot(col3)));
	}

	// After much keyboard smashing and tears (and too much math), this somehow works.
	// Still needs testing tho :o
	//
	// Supposedly, you give it 2 vectors and it will return a rotation matrix that makes
	// the first vector land on the second vector.
	// feat(https://en.wikipedia.org/wiki/Rotation_matrix)
	public static Matrix3x3 getRotation(NormalizedVector f, NormalizedVector t) {
		if (NormalCompare.eq(f, t)) {
			return IDENTITY;
		}
		NormalizedVector axis = f.cross(t).normalized();
		double cosA = f.dot(t);
		double sinA = f.cross(t).length();
		return getRotationImpl(axis, cosA, sinA);
	}

	public static Matrix3x3 getRotation(NormalizedVector axis, double angle) {
		return getRotationImpl(axis, Math.cos(angle), Math.sin(angle));
	}

	private static Matrix3x3 getRotationImpl(NormalizedVector u, double cosA, double sinA) {
		CartesianProductSelf cu = new CartesianProductSelf(u);
		// @formatter:off
		return new Matrix3x3(
			new Vector((cosA + cu.xx * (1 - cosA)), (cu.xy * (1 - cosA) - (u.z * sinA)), (cu.xz * (1 - cosA) + (u.y * sinA))),
			new Vector((cu.xy * (1 - cosA) + (u.z * sinA)), (cosA + cu.yy * (1 - cosA)), (cu.yz * (1 - cosA) - (u.x * sinA))),
			new Vector((cu.xz * (1 - cosA) - (u.y * sinA)), (cu.yz * (1 - cosA) + (u.x * sinA)), (cosA + cu.zz * (1 - cosA))));
		// @formatter:on
	}

	public Matrix3x3 inverse() {
		double det = this.determinant();
		// rotation matrix will never have a zero det
		// try catch something here
		return adjoint().scale(1 / det);
	}

	// Inverse of a rotation matrix is just it's transpose.
	public Matrix3x3 inverseRotMat() {
		return new Matrix3x3(transpose());
	}

	public Matrix3x3 transpose() {
		return new Matrix3x3(new Vector(r1.x, r2.x, r3.x), new Vector(r1.y, r2.y, r3.y), new Vector(r1.z, r2.z, r3.z));
	}

	public double determinant() {
		return r1.dot(r2.cross(r3));
	}

	// I hate this
	public Matrix3x3 adjoint() {
		return new Matrix3x3(
			new Vector(((r2.y * r3.z) - (r3.y * r2.z)), (r3.y * r1.z - r1.y * r3.z), (r1.y * r2.z - r2.y * r1.z)),
			new Vector((r3.x * r2.z - r2.x * r3.z), (r1.x * r3.z - r3.x * r1.z), (r2.x * r1.z - r1.x * r2.z)),
			new Vector((r2.x * r3.y - r3.x * r2.y), (r3.x * r1.y - r1.x * r3.y), (r1.x * r2.y - r2.x * r1.y)));
	}

	@Override
	public String toString() {
		return "Matrix3x3 [r1=" + r1 + ", r2=" + r2 + ", r3=" + r3 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r1 == null) ? 0 : r1.hashCode());
		result = prime * result + ((r2 == null) ? 0 : r2.hashCode());
		result = prime * result + ((r3 == null) ? 0 : r3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix3x3 other = (Matrix3x3) obj;
		if (r1 == null) {
			if (other.r1 != null)
				return false;
		} else if (!r1.equals(other.r1))
			return false;
		if (r2 == null) {
			if (other.r2 != null)
				return false;
		} else if (!r2.equals(other.r2))
			return false;
		if (r3 == null) {
			if (other.r3 != null)
				return false;
		} else if (!r3.equals(other.r3))
			return false;
		return true;
	}

}
