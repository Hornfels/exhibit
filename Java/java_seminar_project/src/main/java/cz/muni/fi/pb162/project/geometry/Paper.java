package cz.muni.fi.pb162.project.geometry;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.ArrayList;

import cz.muni.fi.pb162.project.exception.EmptyDrawableException;
import cz.muni.fi.pb162.project.exception.MissingVerticesException;
import cz.muni.fi.pb162.project.exception.TransparentColorException;

/**
 * Class simulating a sheet of paper on which we can draw Polygons.
 *
 * @author Michal Krejcir
 */
public class Paper implements Drawable, PolygonFactory {

    private Color currentColor = Color.BLACK;
    private List<ColoredPolygon> drawnPolygons = new LinkedList<ColoredPolygon>();

    /**
     * Creates a new blank Paper with default color of BLACK.
     */
    public Paper() {};

    /**
     * Creates a new Paper from Drawable Object with default color of BLACK.
     * @param drawable The Object whose Objects will be copied to Paper.
     */
    public Paper(Drawable drawable) {

        drawnPolygons = List.copyOf(drawable.getAllDrawnPolygons());
    }

    @Override
    public void changeColor(Color color) {

        currentColor = color;
    }

    @Override
    public void drawPolygon(Polygon polygon) throws TransparentColorException {

        if (currentColor == Color.WHITE) {
            throw new TransparentColorException("can't draw in white color");
        }
        ColoredPolygon newPolygon = new ColoredPolygon(polygon, currentColor);

        if (drawnPolygons.contains(newPolygon)) {
            return;
        }
        drawnPolygons.add(newPolygon);
    }

    @Override
    public void erasePolygon(ColoredPolygon polygon) {

        drawnPolygons.remove(polygon);
    }

    @Override
    public void eraseAll() throws EmptyDrawableException {

        if (drawnPolygons.isEmpty()) {
            throw new EmptyDrawableException("can't erase empty Paper");
        }

        drawnPolygons.clear();
    }

    @Override
    public Collection<ColoredPolygon> getAllDrawnPolygons() {

        return Collections.unmodifiableList(drawnPolygons);
    }

    @Override
    public int uniqueVerticesAmount() {

        List<Vertex2D> allVerts = new LinkedList<>();
        ListIterator<ColoredPolygon> polygonIterator = drawnPolygons.listIterator();

        while (polygonIterator.hasNext()) {

            Polygon currentPolygon = polygonIterator.next().getPolygon();

            for (int i = 0; i < currentPolygon.getNumVertices(); i++) {

                allVerts.add(currentPolygon.getVertex(i));
            }
        }

        HashSet<Vertex2D> uniqueVerts = new HashSet<>(allVerts);

        return uniqueVerts.size();
    }

    @Override
    public Polygon tryToCreatePolygon(List<Vertex2D> vertices) throws NullPointerException, MissingVerticesException {

        if (vertices == null) {
            throw new NullPointerException("input list can't be null");
        }

        List<Vertex2D> copy = new ArrayList<>(vertices);

        try {
            return new CollectionPolygon(copy);
        } catch (IllegalArgumentException e) {
            while (copy.remove(null)) {}
        }
        return new CollectionPolygon(copy);
    }

    @Override
    public void tryToDrawPolygons(List<List<Vertex2D>> collectionPolygons) throws EmptyDrawableException {

        boolean drawn = false;
        Exception lastError = null;
        Iterator<List<Vertex2D>> verticesListIterator = collectionPolygons.iterator();

        while(verticesListIterator.hasNext()) {

            List<Vertex2D> vertices = verticesListIterator.next();
            try {
                Polygon polygon = tryToCreatePolygon(vertices);
                drawPolygon(polygon);
                drawn = true;
            } catch (MissingVerticesException | NullPointerException e) {
                lastError = e;
            } catch (TransparentColorException e) {
                lastError = e;
                //changeColor(Color.BLACK);
                currentColor = Color.BLACK;
            }
        }
        if (!drawn) {
            throw new EmptyDrawableException("no polygon was drawn", lastError);
        }
    }

    /**
     * Returns all drawn Polygons with the given color.
     * @param color Color of the polygons.
     * @return ArrayList of the polygons.
     */
    public Collection<Polygon> getPolygonsWithColor(Color color) {

        return getAllDrawnPolygons().stream()
                .filter(polygon -> polygon.getColor() == color)
                .map(ColoredPolygon::getPolygon)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
