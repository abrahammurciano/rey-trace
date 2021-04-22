package primitives;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import util.DoubleCompare;

/**
 * A class that has three values, (x, y, z)
 */
public class Triple {

	public final double x;
	public final double y;
	public final double z;

	public static final Triple ZERO = new Triple(0, 0, 0);

	protected Triple(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new {@link Triple} which is a transformation of this {@link Triple} by applying the given
	 * transformation to each of the coordinates.
	 *
	 * @param transformation A function which receives two doubles and returns another double.
	 * @param aux An auxiliary {@link Triple} whose corresponding coordinate may (or may not) be used in the
	 *        transformation function in order to calculate each of the new coordinates.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroTripleException if the transformation results in the zero Triple.
	 */
	public Triple transform(DoubleBinaryOperator transformation, Triple aux) {
		return new Triple(transformation.applyAsDouble(x, aux.x),
			transformation.applyAsDouble(y, aux.y), transformation.applyAsDouble(z, aux.z));
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Triple)} but does not require an auxiliary {@link Triple},
	 * since the transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param transformation A function which receives a dingle double and returns another double.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroTripleException if the transformation results in the zero Triple.
	 */
	public Triple transform(DoubleUnaryOperator transformation) {
		return new Triple(transformation.applyAsDouble(x), transformation.applyAsDouble(y),
			transformation.applyAsDouble(z));
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Triple)) {
			return false;
		}
		Triple triple = (Triple) o;
		return DoubleCompare.eq(x, triple.x) && DoubleCompare.eq(y, triple.y)
			&& DoubleCompare.eq(z, triple.z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

}
