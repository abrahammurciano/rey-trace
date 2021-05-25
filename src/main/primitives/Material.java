package primitives;

/**
 * This class contains all the configurations which affect how light interacts with some surface.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Material {
	/** The emission light colour of the material. */
	public final Colour emission;
	/** The shine exponent of the material. */
	public final double shine;
	/** The ambient coefficients of the material */
	public final Factors ambient;
	/** The diffuse coefficients of the material. */
	public final Factors diffuse;
	/** The specular coefficients of the material. */
	public final Factors specular;
	/** The reflective coefficients of the material. */
	public final Factors reflectivity;
	/** The transparency coefficients of the material */
	public final Factors transparency;

	/**
	 * Material constructor.
	 *
	 * @param emission The emission light colour of the material.
	 * @param shine    The shine exponent of the material. Should be non-negative.
	 * @param ambient  The ambient coefficient of the material. Should be between 0 and 1.
	 * @param diffuse  The diffuse coefficient of the material. Should be between 0 and 1.
	 * @param specular The specular coefficient of the material. Should be between 0 and 1.
	 */
	public Material(Colour emission, double shine, Factors ambient, Factors diffuse, Factors specular,
		Factors reflectivity, Factors transparency) {
		this.emission = emission;
		this.shine = shine;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflectivity = reflectivity;
		this.transparency = transparency;
	}
}
