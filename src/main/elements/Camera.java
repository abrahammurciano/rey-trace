package elements;

import primitives.Point;
import primitives.Ray;
import java.util.Iterator;
import java.util.NoSuchElementException;
import primitives.NormalizedVector;
import util.Resolution;

/**
 * The camera represents the point of view of the rendered image.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Camera implements Iterable<Ray> {
	private final Point location;
	private final Orientation orientation;
	private final ViewPlane view;

	public Camera(Point location, NormalizedVector front, NormalizedVector up, double pixelWidth,
		double pixelHeight, double distance, Resolution resolution) {
		this.location = location;
		this.orientation = new Orientation(front, up);
		this.view = new ViewPlane(pixelWidth, pixelHeight, distance, resolution);
	}

	@Override
	public Iterator<Ray> iterator() {
		return new RayIterator(this);
	}

	public class RayIterator implements Iterator<Ray> {

		private final Camera camera;
		private int x;
		private int y;

		public RayIterator(Camera camera) {
			this.camera = camera;
			this.x = 0;
			this.y = 0;
		}

		private Ray ray(int x, int y) {
			// TODO: Implement
			return null;
		}

		@Override
		public boolean hasNext() {
			return x < camera.view.resolution.x - 1 || y < camera.view.resolution.y - 1;
		}

		@Override
		public Ray next() {
			Ray r = camera.ray(x, y);
			x = (x + 1) % camera.view.resolution.x;
			if (x == 0) {
				++y;
			}
			if (y >= camera.view.resolution.y) {
				throw new NoSuchElementException();
			}
			return r;
		}

	};
}
