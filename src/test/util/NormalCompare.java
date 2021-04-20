package util;

import primitives.NormalizedVector;

public class NormalCompare {

    public static boolean eq(NormalizedVector calc, NormalizedVector actual) {
        return calc.equals(actual) || calc.equals(actual.reversed());
    }
}

