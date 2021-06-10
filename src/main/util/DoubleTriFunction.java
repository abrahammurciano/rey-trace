package util;

/**
 * A functional interface for a function that takes three doubles and returns an object of type R.
 */
public interface DoubleTriFunction<R> {
	/**
	 * A function that takes three doubles and returns an object of type R.
	 *
	 * @param d1 The first double.
	 * @param d2 The second double.
	 * @param d3 The third double.
	 * @return an object of type R..
	 */
	R apply(double d1, double d2, double d3);
}
