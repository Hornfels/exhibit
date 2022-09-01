package cz.muni.fi.pb162.project.demo;
import cz.muni.fi.pb162.project.geometry.LabeledPolygon;
import cz.muni.fi.pb162.project.geometry.Vertex2D;

import java.io.File;
import java.io.IOException;

/**
 * Class for running main method.
 *
 * @author Michal Krejcir
 */
public class Demo {

    /**
     * Runs the code - creates a new Triangle and prints information about it.
     *
     * @param args command line arguments, will be ignored
     */
    public static void main(String[] args) throws IOException {

        File file = new File("polygon-ok.txt");

        LabeledPolygon pol = new LabeledPolygon.Builder()
                .addVertex("vrchol x", new Vertex2D(123, 456)).read(file).build();
        pol.writeJson(System.out);
        System.out.println("Hello World!");
    }
}
