package math.matrices;

import primitives.Point;
import primitives.Triple;
import primitives.NonZeroVector;
import primitives.Vector;
import util.DoubleTriFunction;

/**
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Matrix {

	// rows of matrix
	private final Vector r1;
	private final Vector r2;
	private final Vector r3;

	/**
	 * Matrix constructor which takes three {@link Vector}s.
	 *
	 * @param r1 The first row.
	 * @param r2 The second row.
	 * @param r3 The third row.
	 */
	public Matrix(Vector r1, Vector r2, Vector r3) {
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
	 * @param <T>     The type of Triple to return.
	 *
	 * @param triple  The {@link Triple} to multiply by the matrix.
	 * @param creator A function which receives three doubles and returns some subtype of {@link Triple}.
	 * @return The {@link Triple} resulting from the multiplication, of the concrete type as returned by
	 *         {@code creator}.
	 */
	public <T extends Triple> T multiply(Triple triple, DoubleTriFunction<T> creator) {
		return creator.apply(r1.dot(triple), r2.dot(triple), r3.dot(triple));
	}

	/**
	 * Multiply the matrix by a {@link Point}.
	 *
	 * @param p The {@link Point} to multiply by the matrix.
	 * @return The {@link Point} resulting from the multiplication.
	 */
	public Point multiply(Point p) {
		return multiply(p, Point::new);
	}

	/**
	 * Multiply the matrix by a {@link NonZeroVector}.
	 *
	 * @param v The {@link NonZeroVector} to multiply by the matrix.
	 * @return The {@link NonZeroVector} resulting from the multiplication.
	 */
	public NonZeroVector multiply(NonZeroVector v) {
		return multiply(v, NonZeroVector::new);
	}

	@Override
	public String toString() {
		return "Matrix3x3 [r1=" + r1 + ", r2=" + r2 + ", r3=" + r3 + "]";
	}

}
