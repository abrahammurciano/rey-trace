package primitives;

public interface CoordinateTransformation {
	Coordinate transform(Coordinate base, Coordinate auxiliary);
}
