package lighting;

import math.equations.Quadratic;
import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * This class is represents an omni-directional light source that attenuates
 * with respect to distance, similar to a light bulb.
 *
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PointLight extends LightSource {

    private Quadratic factors;
    private Point position;

    /**
     * Construct a {@link PointLight} from a {@link Colour} and 3 doubles that represent the attenuation constants.
     * 
     * @param colour   The colour of the light
     * @param position The position in space of the light source
	 * @param a The quadratic factor
	 * @param b The linear factor
	 * @param c The constant factor
     */
    public PointLight(Colour colour, Point position, double a, double b, double c) {
        super(colour);
        this.position = position;
        this.factors = new Quadratic(a,b,c);
    }

    /**
     * Construct a {@link PointLight} from a {@link Colour}, a {@link Point} and a {@link Quadratic}
     * 
     * @param colour   The colour of the light
     * @param position The position in space of the light source
     * @param factors  The quadratic equation that contains the attenuations factors
     */
    public PointLight(Colour colour, Point position, Quadratic factors) {
        super(colour);
        this.position = position;
        this.factors = factors;
    }

    /**
     * Calculate the colour of the light that this light source is projecting onto a
     * given point.
     *
     * @param point The point at which to calculate the colour of the light.
     * @return The colour of the light at the given point.
     */
    @Override
    public Colour colourAt(Point point) {
        double d = position.distance(point);
        return colour.scale(1 / factors.sub(d));
    }

	/**
	 * Calculate a {@link NormalizedVector} from the source of the light to the given point.
	 *
	 * @param point The point the vector will point to from the light source.
	 * @return The normalized vector from the light source to the given point.
	 */
    @Override
    public NormalizedVector vectorTo(Point point) {
        return position.vectorTo(point).normalized();
    }
}
