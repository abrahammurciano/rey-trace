package xml.factories.attribute;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Some utility functions for parsing some common formats from an XML attribute string.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class AttributeParser {
	private AttributeParser() {}

	private static final Pattern int_regex = Pattern.compile("[+-]?[0-9]+");
	private static final Pattern double_regex = Pattern.compile("[-+]?[0-9]*(?:(?:[0-9][.]|[.][0-9])[0-9]*)?");

	/**
	 * Extracts the first three integers in the given string.
	 *
	 * @param str The string to parse ints from.
	 * @return An array of three ints containing the first, second and third integers in the given string.
	 * @throws ArrayIndexOutOfBoundsException if there were not enough numbers in the string.
	 */
	public static int[] threeInts(String str) {
		String[] tokens = matches(int_regex, str);
		int[] result = new int[3];
		for (int i = 0; i < 3; ++i) {
			result[i] = Integer.parseInt(tokens[i]);
		}
		return result;
	}

	/**
	 * Extracts the first three doubles in the given string.
	 *
	 * @param str The string to parse doubles from.
	 * @return An array of three doubles containing the first, second and third doubles in the given string.
	 * @throws ArrayIndexOutOfBoundsException if there were not enough numbers in the string.
	 */
	public static double[] threeDoubles(String str) {
		String[] tokens = matches(double_regex, str);
		double[] result = new double[3];
		for (int i = 0; i < 3; ++i) {
			result[i] = Double.parseDouble(tokens[i]);
		}
		return result;
	}

	/**
	 * Returns an array of non-empty matches in str of the given pattern.
	 *
	 * @param pattern The pattern to apply to the string.
	 * @param str     The string to search for matches.
	 * @return An array of non-empty matches.
	 */
	private static String[] matches(Pattern pattern, String str) {
		return pattern.matcher(str).results().map(MatchResult::group).filter(s -> s.length() > 0)
			.toArray(String[]::new);
	}
}
