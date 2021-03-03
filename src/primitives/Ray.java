package primitives;

/**
 * Ray Class
 * This class represents a ray with it's base at the Point 'source' and shoots off in direction pointed at by Vector 'direction'
 * @author [Abe Murciano]
 * @author [Eli Levin]
 *
 * */
public class Ray {
	private Point source;
	private Vector direction;

	public Ray(Point source, Vector direction) {
		this.source = source;
		this.direction = direction;
	}

	@Override
	public boolean equals(Object r) {
		if (this == r) {
			return true;
		}
		if (r == null || getClass() != r.getClass()) {
			return false;
		}
		Ray ray = (Ray) r;
		return source.equals(ray.source) && direction.equals(ray.direction);
	}

	@Override
	public int hashCode() {
		return source.hashCode() ^ direction.hashCode();
	}

	@Override
	public String toString() {
		// ((0, 0, 0), (1, 1, 1))
		return "(" + source + ", " + direction + ")";
	}
}
