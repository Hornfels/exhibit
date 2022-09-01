package cz.muni.fi.pb162.project.utils;
import cz.muni.fi.pb162.project.geometry.Polygon;

/**
 * Class providing methods for Triangle coordinates calculations.
 *
 * @author Michal Krejcir
 */
public class SimpleMath {

    /**
     * Gets minimal x coordinate of Polygon Vertices.
     * @param polygon Polygon whose minimal x coordinate we want to get.
     * @return Value of minimal x coordinate.
     */
    public static double minX(Polygon polygon) {

        double result = polygon.getVertex(0).getX();

        for (int i = 0; i < polygon.getNumVertices(); i++) {
            double current = polygon.getVertex(i).getX();
            if (current < result) {
                result = current;
            }
        }
        return result;
    }

    /**
     * Gets minimal y coordinate of Polygon Vertices.
     * @param polygon Polygon whose minimal y coordinate we want to get.
     * @return Value of minimal y coordinate.
     */
    public static double minY(Polygon polygon) {

        double result = polygon.getVertex(0).getY();

        for (int i = 0; i < polygon.getNumVertices(); i++) {
            double current = polygon.getVertex(i).getY();
            if (current < result) {
                result = current;
            }
        }
        return result;
    }

    /**
     * Gets maximal x coordinate of Polygon Vertices.
     * @param polygon Polygon whose maximal x coordinate we want to get.
     * @return Value of maximal x coordinate.
     */
    public static double maxX(Polygon polygon) {

        double result = polygon.getVertex(0).getX();

        for (int i = 0; i < polygon.getNumVertices(); i++) {
            double current = polygon.getVertex(i).getX();
            if (current > result) {
                result = current;
            }
        }
        return result;
    }

    /**
     * Gets maximal y coordinate of Polygon Vertices.
     * @param polygon Polygon whose maximal y coordinate we want to get.
     * @return Value of maximal y coordinate.
     */
    public static double maxY(Polygon polygon) {

        double result = polygon.getVertex(0).getY();

        for (int i = 0; i < polygon.getNumVertices(); i++) {
            double current = polygon.getVertex(i).getY();
            if (current > result) {
                result = current;
            }
        }
        return result;
    }
}
