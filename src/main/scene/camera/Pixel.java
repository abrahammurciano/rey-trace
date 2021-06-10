package scene.camera;

public class Pixel<T> {
	public final T representation;
	public final int col;
	public final int row;

	public Pixel(T representation, int x, int y) {
		this.representation = representation;
		this.col = x;
		this.row = y;
	}
}
