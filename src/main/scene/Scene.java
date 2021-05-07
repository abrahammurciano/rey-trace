package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Colour;

/**
 * This class groups together the elements of a scene. That is, the light sources, the geometries, and some additional
 * details.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Scene {
	/** The background colour of the image. This will be applied when a ray doesn't intersect any geometry. */
	public final Colour background;
	/** The ambient lighting of the scene. */
	public final AmbientLight ambient;
	/** The collection of geometries in the scene. */
	public final Geometries geometries;

	/**
	 * Constructor for the scene with no geometries.
	 *
	 * @param background The background colour of the scene.
	 * @param ambient    The ambient lighting of the scene.
	 */
	public Scene(Colour background, AmbientLight ambient) {
		this(background, ambient, new Geometries());
	}

	/**
	 * Constructor for the scene.
	 *
	 * @param background The background colour of the scene.
	 * @param ambient    The ambient colour of the scene.
	 * @param geometries The geometries to initialise the scene with.
	 */
	public Scene(Colour background, AmbientLight ambient, Geometries geometries) {
		this.background = background;
		this.ambient = ambient;
		this.geometries = geometries;
	}
}
