package primitives;

/**
 * This class contains all the configurations which affect how light interacts with some surface.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Material {
	/**
	 * The colour of the object. The red, green, and blue values will be scaled down to a range from 0 to 1, and then
	 * multiplied by each of the light coefficients (specular, diffuse, etc) during the colour calculation process. This
	 * way we can define the colour of any given object.
	 */
	public final Colour colour;
	/** The emission light colour of the material. */
	public final Colour emission;
	/** The shine exponent of the material. */
	public final double shine;
	/** The ambient coefficient of the material */
	public final double ambient;
	/** The diffuse coefficient of the material. */
	public final double diffuse;
	/** The specular coefficient of the material. */
	public final double specular;


	/**
	 * Material constructor.
	 *
	 * @param emission The emission light colour of the material.
	 * @param shine    The shine exponent of the material. Should be non-negative.
	 * @param ambient  The ambient coefficient of the material. Should be between 0 and 1.
	 * @param diffuse  The diffuse coefficient of the material. Should be between 0 and 1.
	 * @param specular The specular coefficient of the material. Should be between 0 and 1.
	 */
	public Material(Colour colour, Colour emission, double shine, double ambient, double diffuse, double specular) {
		this.colour = colour;
		this.emission = emission;
		this.shine = shine;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	/**
	 * Material constructor with no colour (defaults to black), no emission light (defaults to black) nor ambient
	 * coefficient (defaults to 1).
	 *
	 * @param shine    The shine exponent of the material.
	 * @param diffuse  The diffuse coefficient of the material.
	 * @param specular The specular coefficient of the material.
	 */
	public Material(double shine, double diffuse, double specular) {
		this(Colour.BLACK, Colour.BLACK, shine, 1, diffuse, specular);
	}
}
