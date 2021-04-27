package elements;

import util.Resolution;

/**
 * Represends the field of view of the camera.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class ViewPlane {
	public final double pixelWidth;
	public final double pixelHeight;
	public final double distance;
	public final Resolution resolution;

	public ViewPlane(double pixelWidth, double pixelHeight, double distance,
		Resolution resolution) {
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.distance = distance;
		this.resolution = resolution;
	}
}
