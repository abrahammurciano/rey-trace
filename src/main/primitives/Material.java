package primitives;

public class Material {
	/** The emission light color of the material. */
	public final Colour emission;
	/** The shine factor of the material. */
	public final int shine;
	/** The diffuse coefficient of the material. */
	public final double diffuse;
	/** The specular coefficient of the material. */
	public final double specular;

	/**
	 * Material constructor.
	 *
	 * @param emission The emission light color of the material.
	 * @param shine    The shine factor of the material.
	 * @param diffuse  The diffuse coefficient of the material.
	 * @param specular The specular coefficient of the material.
	 */
	public Material(Colour emission, int shine, double diffuse, double specular) {
		this.emission = emission;
		this.shine = shine;
		this.diffuse = diffuse;
		this.specular = specular;
	}
}
