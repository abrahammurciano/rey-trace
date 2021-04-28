package elements;

import primitives.Point;
import primitives.Ray;
import java.util.Iterator;
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

	public Camera(Point location, NormalizedVector front, NormalizedVector up, double width, double height,
			double distance, Resolution resolution) {
		this.location = location;
		this.orientation = new Orientation(front, up);
		this.view = new ViewPlane(width, height, location.add(orientation.front.scale(distance)), resolution,
				orientation);
	}

	@Override
	public Iterator<Ray> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
