package primitives;

import java.util.Objects;
import lighting.LightSource;
import math.compare.DoubleCompare;

/**
 * A ray between a point and a light source.
 */
public class ShadowRay extends Ray {
	/** The square of the distance between the source and the light source. */
	public final double squareDistance;

	/**
	 * Construct a shadow ray.
	 *
	 * @param source    The point where the ray starts.
	 * @param direction The direction of the ray.
	 * @param light     The {@link LightSource} that the shadow ray corresponds to.
	 */
	public ShadowRay(Point source, NormalizedVector direction, LightSource light) {
		super(source, direction);
		this.squareDistance = light.squareDistance(source);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o)) {
			return false;
		}
		ShadowRay shadow = (ShadowRay) o;
		return DoubleCompare.eq(squareDistance, shadow.squareDistance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(squareDistance, super.hashCode());
	}

}
