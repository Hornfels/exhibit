package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.geometry.Measurable;
import cz.muni.fi.pb162.project.geometry.Triangle;

/**
 * Class for printing information regarding the width and height of a Measurable object.
 *
 * @author Michal Krejcir
 */
public class Gauger {

    /**
     * Prints width and height of a Measurable object.
     * @param object Object we want to print information about.
     */
    public static void printMeasurement(Measurable object) {

        System.out.println(String.format("Width: %s", object.getWidth()));
        System.out.println(String.format("Height: %s", object.getHeight()));
    }

    /**
     * Prints Triangle representation, width and height.
     * @param triangle Triangle we want to print information about.
     */
    public static void printMeasurement(Triangle triangle) {

        System.out.println(triangle);
        printMeasurement((Measurable) triangle);
    }

}
