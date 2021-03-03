package primitives;

/**
 * Vector Class
 * This class represents a vector with it's base at the origin and it's head  at the point 'head'
 * @author [Abe Murciano]
 * @author [Eli Levin]
 *
 * */
public class Vector {
	private Point head;

    /** This constructor accepts 3 doubles and returns the appropriate vector
     * @param double x
     * @param double y
     * @param double z 
     * */
	public Vector(double x, double y, double z) {
		this(new Point(x, y, z));
	}

    /** This constructor accepts 3 Coordinates and returns the appropriate vector
     * @param Coordinate x
     * @param Coordinate y
     * @param Coordinate z 
     * */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Point(x, y, z));
	}

    /** This constructor accepts a Point and returns the appropriate vector
     * @param Point head
     * */
	public Vector(Point head) {
		if (head.equals(Point.ORIGIN)) {
			throw new IllegalArgumentException("Error: Zero vector is not allowed.");
		}
		this.head = head;
	}

    /** getter
     * */
	public Point getHead() {
		return head;
	}

    /** Calls the lambda function 'transformation' on this and on 'auxiliary'.
     * 
     * @param transformation Function which accepts 2 Coordinates and returns a Coordinate
     * @param auxiliary The other vector being operated on
     * @return Transformed Vector
     * */
	public Vector transform(CoordinateTransformation transformation, Vector auxiliary) {
		return new Vector(getHead().transform(transformation, auxiliary.getHead()));
	}

    /** Calls the lambda function 'transformation' on this.
     * 
     * @param transformation Function which accepts a Coordinate and returns a Coordinate
     * @return Transformed Vector
     * */
	public Vector transform(CoordinateTransformation transformation) {
		return new Vector(getHead().transform(transformation));
	}
 
    /** Adds two vectors and returns a new vector
     * @param vector Added to this
     * @return sum of vectors 
     * */
	public Vector add(Vector vector) {
		return new Vector(getHead().add(vector));
	}

    /** Subtracts two vectors and returns a new vector
     * @param vector Subtracted from this
     * @return difference of vectors
     * */
	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

    /** Scales a vector
     * @param factor The scalar
     * @return  New scaled vector
     * */
	public Vector scale(double factor) {
		return transform((c, __) -> c.multiply(factor));
	}

    /** returns cross product of 2 vectors
     * @param vector The second vector in cross
     * @return cross product
     * */
	public Vector crossProduct(Vector vector) {
		Coordinate[] coordinates = new Coordinate[3];
		for (int i = 0; i < coordinates.length; ++i) {
			coordinates[i] = getHead().getCoordinate(i + 1 % coordinates.length)
					.multiply(vector.getHead().getCoordinate(1 + 2 % coordinates.length))
					.subtract(getHead().getCoordinate(i + 2 % coordinates.length)
							.multiply(vector.getHead().getCoordinate(i + 1 % coordinates.length)));
		}
		return new Vector(coordinates[0], coordinates[1], coordinates[2]);
	}

    /** returns dot product of 2 vectors
     * @param vector The second vector in dot
     * @return dot product
     * */
	public double dotProduct(Vector vector) {
		// Construct a vector whose coordinates are the product of the coordinates of the other two.
		Vector v = transform((base, aux) -> base.multiply(aux), vector);
		return v.getHead().sum();
	}

    /** returns length of vector
     * @return length 
     * */
	public double length() {
		return head.distance(Point.ORIGIN);
	}

    /** returns squared length of vector
     * @return squared length 
     * */
	public double squareLength() {
		return head.squareDistance(Point.ORIGIN);
	}

    /** returns a new normalized vector 
     * @return new vector 
     * */
	public Vector normalized() {
		return scale(1 / length());
	}

	@Override
	public boolean equals(Object v) {
		if (this == v) {
			return true;
		}
		if (v == null || getClass() != v.getClass()) {
			return false;
		}
		return head.equals(((Vector) v).head);
	}

	@Override
	public int hashCode() {
		return head.hashCode();
	}

	@Override
	public String toString() {
		return head.toString();
	}
}
