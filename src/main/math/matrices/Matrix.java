package math.matrices;

import primitives.Point;
import primitives.Triple;
import primitives.TripleCreator;
import primitives.Vector;
import primitives.VectorBase;

/**
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Matrix {

	// rows of matrix
	private final VectorBase r1;
	private final VectorBase r2;
	private final VectorBase r3;

	/**
	 * Matrix constructor which takes three {@link VectorBase}s.
	 *
	 * @param r1 The first row.
	 * @param r2 The second row.
	 * @param r3 The third row.
	 */
	public Matrix(VectorBase r1, VectorBase r2, VectorBase r3) {
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}

	/**
	 * Mutlyply the matrix by a scalar.
	 *
	 * @param t The scalar to multiply the matrix by.
	 * @return A new matrix which is the result of the multiplication.
	 */
	public Matrix scale(double t) {
		return new Matrix(r1.scale(t), r2.scale(t), r3.scale(t));
	}

	/**
	 * Multiply the matrix by a {@link Triple}.
	 *
	 * @param triple  The {@link Triple} to multiply by the matrix.
	 * @param creator A function which receives three doubles and returns some subtype of {@link Triple}.
	 * @return The {@link Triple} resulting from the multiplication, of the concrete type as returned by
	 *         {@code creator}.
	 */
	public Triple multiply(Triple triple, TripleCreator creator) {
		return creator.create(r1.dot(triple), r2.dot(triple), r3.dot(triple));
	}

	/**
	 * Multiply the matrix by a {@link Point}.
	 *
	 * @param triple The {@link Point} to multiply by the matrix.
	 * @return The {@link Point} resulting from the multiplication.
	 */
	public Point multiply(Point p) {
		return (Point) multiply(p, Point::create);
	}

	/**
	 * Multiply the matrix by a {@link Vector}.
	 *
	 * @param triple The {@link Vector} to multiply by the matrix.
	 * @return The {@link Vector} resulting from the multiplication.
	 */
	public Vector multiply(Vector v) {
		return (Vector) multiply(v, Vector::create);
	}

	/**
	 * Multiply the matrix by a {@link Vector}.
	 *
	 * @param triple The {@link Vector} to multiply by the matrix.
	 * @return The {@link Vector} resulting from the multiplication.
	 */
	public Matrix multiply(Matrix A) {
		VectorBase col1 = new VectorBase(A.r1.x, A.r2.x, A.r3.x);
		VectorBase col2 = new VectorBase(A.r1.y, A.r2.y, A.r3.y);
		VectorBase col3 = new VectorBase(A.r1.z, A.r2.z, A.r3.z);
		return new Matrix(new VectorBase(r1.dot(col1), r1.dot(col2), r1.dot(col3)),
			new VectorBase(r2.dot(col1), r2.dot(col2), r2.dot(col3)),
			new VectorBase(r3.dot(col1), r3.dot(col2), r3.dot(col3)));
	}

	public Matrix inverse() {
		double det = this.determinant();
		if (det == 0) {
			throw new IllegalStateException("This matrix is not invertible");
		}
		return adjoint().scale(1 / det);
	}

	public Matrix transpose() {
		return new Matrix(new Vector(r1.x, r2.x, r3.x), new Vector(r1.y, r2.y, r3.y), new Vector(r1.z, r2.z, r3.z));
	}

	public double determinant() {
		return r1.dot(r2.cross(r3));
	}

	public Matrix adjoint() {
		return new Matrix(
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
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Matrix other = (Matrix) obj;
		return r1.equals(other.r1) && r2.equals(other.r2) && r3.equals(other.r3);
	}

}
