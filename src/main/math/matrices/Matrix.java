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
	 * @param p The {@link Point} to multiply by the matrix.
	 * @return The {@link Point} resulting from the multiplication.
	 */
	public Point multiply(Point p) {
		return (Point) multiply(p, Point::create);
	}

	/**
	 * Multiply the matrix by a {@link Vector}.
	 *
	 * @param v The {@link Vector} to multiply by the matrix.
	 * @return The {@link Vector} resulting from the multiplication.
	 */
	public Vector multiply(Vector v) {
		return (Vector) multiply(v, Vector::create);
	}

	@Override
	public String toString() {
		return "Matrix3x3 [r1=" + r1 + ", r2=" + r2 + ", r3=" + r3 + "]";
	}

}
