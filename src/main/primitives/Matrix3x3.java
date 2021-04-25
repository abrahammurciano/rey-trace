package primitives;

import util.NormalCompare;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the origin and it's head at the {@link Point}
 * 'head'.
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

	private Triple multiply_T(Triple t) {
		try {
			Vector temp = new Vector(t);
			return new Triple(r1.dot(temp), r2.dot(temp), r3.dot(temp));
		} catch (ZeroVectorException e) {
			return new Triple(0,0,0);
		}
	}

	public Point multiply(Point p) {
		return new Point(multiply_T(p));
	}

	public Vector multiply(Vector v) {
		return new Vector(multiply_T(v));
	}

	public Matrix3x3 multiply(Matrix3x3 B) {
		Vector col1 = new Vector(B.r1.x, B.r2.x, B.r3.x);
		Vector col2 = new Vector(B.r1.y, B.r2.y, B.r3.y);
		Vector col3 = new Vector(B.r1.z, B.r2.z, B.r3.z);
		return new Matrix3x3(new Vector(r1.dot(col1), r1.dot(col2), r1.dot(col3)),
				new Vector(r2.dot(col1), r2.dot(col2), r2.dot(col3)),
				new Vector(r3.dot(col1), r3.dot(col2), r3.dot(col3)));
	}


	// After much keyboard smashing and tears (and too much math), this somehow works.
	// Still needs testing tho :o
	//
	// Supposedly, you give it 2 vectors and it will return a rotation matrix that makes
	// the first vector land on the second vector.
	// feat(https://en.wikipedia.org/wiki/Rotation_matrix)
	public static Matrix3x3 getRotation(NormalizedVector f, NormalizedVector t) {
		if (NormalCompare.eq(f, t)) {
			return IDENTITY; // is this how to do this?
		}
		NormalizedVector u = f.cross(t).normalized();
		double cosA = f.dot(t);
		double sinA = f.cross(t).length();
		double u_xy = u.x * u.y;
		double u_xz = u.x * u.z;
		double u_yz = u.y * u.z;
		double u_x2 = u.x * u.x;
		double u_y2 = u.y * u.y;
		double u_z2 = u.z * u.z;
		return new Matrix3x3(
				new Vector((cosA + u_x2 * (1 - cosA)), (u_xy * (1 - cosA) - u.z * sinA),
						(u_xz * (1 - cosA) + u.y * sinA)),
				new Vector((u_xy * (1 - cosA + u.z * sinA)), (cosA + u_y2 * (1 - cosA)),
						(u_yz * (1 - cosA) - u.x * sinA)),
				new Vector((u_xz * (1 - cosA) - u.y * sinA),
						(u_yz * (1 - cosA) + u.x * sinA), (cosA + u_z2 * (1 - cosA))));
	}

	public Matrix3x3 inverse() {
		double det = this.determinant();
		// rotation matrix will never have a zero det
		return new Matrix3x3(adjoint().scale(1 / det));
	}

	// Inverse of a rotation matrix is just it's transpose.
	public Matrix3x3 inverseRotMat() {
		return new Matrix3x3(transpose());
	}

	public Matrix3x3 transpose() {
		return new Matrix3x3(new Vector(r1.x, r2.x, r3.x),
				new Vector(r1.y, r2.y, r3.y),
				new Vector(r1.z, r2.z, r3.z));
	}

	public double determinant() {
		Vector v = r2.cross(r3);
		return (v.x * r1.x) + (v.y * r1.y) + (v.z * r1.z);
	}

	// I hate this
	public Matrix3x3 adjoint() {
		return new Matrix3x3(
				new Vector(((r2.y * r3.z) - (r3.y * r2.z)),
						(r3.y * r1.z - r1.y * r3.z),
						(r1.y * r2.z - r2.y * r1.z)),
				new Vector((r3.x * r2.z - r2.x * r3.z),
						(r1.x * r3.z - r3.x * r1.z),
						(r2.x * r1.z - r1.x * r2.z)),
				new Vector((r2.x * r3.y - r3.x * r2.y),
						(r3.x * r1.y - r1.x * r3.y),
						(r1.x * r2.y - r2.x * r1.y)));
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
