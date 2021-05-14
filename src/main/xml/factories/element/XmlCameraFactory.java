package xml.factories.element;

import primitives.NormalizedVector;
import java.util.function.Function;
import org.w3c.dom.Element;
import primitives.Point;
import scene.camera.Camera;
import scene.camera.CameraSettings;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlFactoryFromAttribute;
import xml.factories.attribute.XmlResolutionFactory;
import xml.factories.attribute.XmlTripleFactory;

/**
 * Creates a {@link Camera} from the given XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlCameraFactory implements XmlFactoryFromElement<Camera> {

	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlTripleFactory<NormalizedVector> VECTOR_FACTORY =
		new XmlTripleFactory<>(NormalizedVector::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlResolutionFactory RESOLUTION_FACTORY = new XmlResolutionFactory();

	@Override
	public Camera create(Element element) {
		CameraSettings settings = new CameraSettings();

		loadSetting(element.getAttribute("position"), POINT_FACTORY, settings::position);
		loadSetting(element.getAttribute("front"), VECTOR_FACTORY, settings::front);
		loadSetting(element.getAttribute("up"), VECTOR_FACTORY, settings::up);
		loadSetting(element.getAttribute("width"), DOUBLE_FACTORY, settings::width);
		loadSetting(element.getAttribute("height"), DOUBLE_FACTORY, settings::height);
		loadSetting(element.getAttribute("distance"), DOUBLE_FACTORY, settings::distance);
		loadSetting(element.getAttribute("resolution"), RESOLUTION_FACTORY, settings::resolution);

		Camera camera = new Camera(settings);

		double pitch = DOUBLE_FACTORY.create(element.getAttribute("pitch"), 0d);
		double yaw = DOUBLE_FACTORY.create(element.getAttribute("yaw"), 0d);
		double roll = DOUBLE_FACTORY.create(element.getAttribute("roll"), 0d);

		return camera.rotate(toRadians(pitch), toRadians(yaw), toRadians(roll));
	}

	private <T> void loadSetting(String attribute, XmlFactoryFromAttribute<T> factory,
		Function<T, CameraSettings> setter) {
		if (!attribute.isEmpty()) {
			T obj = factory.create(attribute);
			setter.apply(obj);
		}
	}

	private static double toRadians(double degrees) {
		return degrees * Math.PI / 180;
	}

}
