package geometries;

import org.junit.Assert;
import org.junit.Test;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class TubeTests {
    public final Ray ray = new Ray(new Point(1,2,3), new Vector(4,5,6));
    public final Tube tube = new Tube(ray, 3); 

    @Test
    public void normal() {
        // find random point on tube by scaling the vector by some random number,
        // then going perpendicular from there for a length of radius. 
        // Scale vector by 6.9
        // (30.942606428329093,34.625914857336724, 44.4)
        NormalizedVector calc = tube.normal(new Point(30.942606428329093,34.625914857336724, 44.4));
        NormalizedVector actual = new NormalizedVector(5,-4,0);
        Assert.assertEquals("Normalized vectors should be equal", calc, actual);

        // One more, this time perpendicular to the source of the ray
        calc = tube.normal(new Point(1, 8, -2));
        actual = new NormalizedVector(0,6,-5);
        Assert.assertEquals("Normalized vectors should be equal", calc, actual);
    }
    
}
