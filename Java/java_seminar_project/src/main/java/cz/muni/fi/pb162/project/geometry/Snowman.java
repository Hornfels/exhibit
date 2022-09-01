package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Snowman consisting of four Circular shapes.
 *
 * @author Michal Krejcir
 */
public class Snowman {

    private RegularPolygon[] spheres = new RegularPolygon[3];

    public static final double REDUCTION_FACTOR = 0.8;

    private Vertex2D shiftCenter(Circular object, double reducedRadius) {

        double newYCoord = object.getCenter().getY() + object.getRadius() + reducedRadius;
        return new Vertex2D(object.getCenter().getX(), newYCoord);
    }

    /**
     * Creates a new Snowman with the given object and reduction factor.
     * @param lowestSphere Circular object used as a base of the Snowman.
     * @param reductionFactor Factor by which each sphere is smaller than the one below it.
     */
    public Snowman(RegularPolygon lowestSphere, double reductionFactor) {

        if (reductionFactor <= 0 || reductionFactor > 1) {
            reductionFactor = REDUCTION_FACTOR;
        }

        spheres[0] = lowestSphere;
        for (int i = 1; i < 3; i++) {

            double reducedRadius = spheres[i - 1].getRadius() * reductionFactor;
            spheres[i] = new GeneralRegularPolygon(shiftCenter(spheres[i - 1], reducedRadius),
                    lowestSphere.getNumEdges(), reducedRadius) {
            };
        }
    }

    public RegularPolygon[] getBalls() {
        return spheres;
    }
}
