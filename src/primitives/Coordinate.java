package primitives;

public class Coordinate {
    private double value;

    public Coordinate(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Coordinate add(Coordinate c) {
        return new Coordinate(getValue() + c.value);
    }

    public Coordinate subtract(Coordinate c) {
        return new Coordinate(getValue() - c.value);
    }

    public Coordinate multiply(double d) {
        return new Coordinate(getValue() * d);
    }

    public Coordinate multiply(Coordinate c) {
        return new Coordinate(getValue() * c.getValue());
    }

    @Override
    public boolean equals(Object c) {
        if (this == c) {
            return true;
        }
        if (c == null || getClass() != c.getClass()) {
            return false;
        }
        return Util.isZero(value - ((Coordinate) c).value);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
