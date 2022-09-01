package cz.muni.fi.pb162.project.geometry;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;



/**
 * Class representing a Polygon with named Vertices stored in a map.
 *
 * @author Michal Krejcir
 */
public final class LabeledPolygon extends SimplePolygon implements Labelable, Polygon, PolygonWritable, Sortable {

    private TreeMap<String, Vertex2D> vertices;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private LabeledPolygon(Map<String, Vertex2D> vertices) {

        super(vertices.values().toArray(new Vertex2D[0]));
        this.vertices = new TreeMap(vertices);
    }

    @Override
    public Vertex2D getVertex(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("Index must be non-negative number.");
        }

        List<String> keys = new ArrayList<>(vertices.keySet());
        Collections.sort(keys);
        return vertices.get(keys.get(index % keys.size()));
    }

    @Override
    public int getNumVertices() {

        return vertices.size();
    }

    @Override
    public Vertex2D getVertex(String label) {

        if (vertices.containsKey(label)) {
            return vertices.get(label);
        }
        throw new IllegalArgumentException("Vertex with this label not found.");
    }

    @Override
    public Collection<String> getLabels() {
        return Collections.unmodifiableSortedSet(vertices.navigableKeySet());
    }

    @Override
    public Collection<String> getLabels(Vertex2D vertex) {

        SortedSet<String> result = new TreeSet<>();

        for (Map.Entry<String, Vertex2D> entry : vertices.entrySet() ) {

            if (entry.getValue().equals(vertex)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public Collection<Vertex2D> getSortedVertices() {

        return getSortedVertices(null);
    }

    @Override
    public Collection<Vertex2D> getSortedVertices(Comparator<Vertex2D> comparator) {

        SortedSet result = new TreeSet(comparator);
        result.addAll(vertices.values());
        return Collections.unmodifiableSortedSet(result);
    }

    /**
     * Finds Vertices which are stored multiple times in the polygon.
     * @return Set of duplicate vertices.
     */
    public Collection<Vertex2D> duplicateVertices() {

        Set<Vertex2D> seenSoFar = new HashSet<>();
        Set<Vertex2D> result = new HashSet<>();

        for (Map.Entry<String, Vertex2D> entry : vertices.entrySet() ) {

            if (seenSoFar.contains(entry.getValue())) {
                result.add(entry.getValue());
            }
            seenSoFar.add(entry.getValue());
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public void write(OutputStream os) throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

        for (Map.Entry<String, Vertex2D> e: vertices.entrySet()) {

            Vertex2D vertex = e.getValue();
            String name = e.getKey();

            writer.newLine();
            writer.write(String.format("%f %f %s", vertex.getX(), vertex.getY(), name));
        }
        writer.flush();
        return;

    }

    @Override
    public void write(File file) throws IOException {

        try (FileOutputStream stream = new FileOutputStream(file)) {
            write(stream);
            stream.flush();
        }
        return;
    }

    /**
     * Writes Vertices of this Polygon in json format into the stream.
     * @param os Stream for writing result into.
     * @throws IOException
     */
    public void writeJson(OutputStream os) throws IOException {

        String json = GSON.toJson(vertices);

        Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);

        writer.write(json);
        writer.flush();

        return;
    }


    /**
     * Class representing a LabeledPolygon builder.
     *
     * @author Michal Krejcir
     */
    public static final class Builder implements Buildable, PolygonReadable {

        private TreeMap<String, Vertex2D> vertices;

        /**
         * Initializes the Builder.
         */
        public Builder() {
            this.vertices = new TreeMap<>();
        }

        /**
         * Adds Vertex named label into the Builder.
         * @param label Label of the new Vertex.
         * @param vert New Vertex.
         * @return This Builder.
         */
        public Builder addVertex(String label, Vertex2D vert) {

            if (label == null || vert == null) {
                throw new IllegalArgumentException("vertex or label can't be null");
            }

            vertices.put(label, vert);
            return this;
        }
        @Override
        public LabeledPolygon build() {
            return new LabeledPolygon(vertices);
        }

        @Override
        public Builder read(InputStream is) throws IOException {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            TreeMap<String, Vertex2D> loaded = new TreeMap<>();

            try {
                while (reader.ready()) {

                    String[] splitted = reader.readLine().split(" ");

                    if (splitted.length < 3) {
                        throw new IOException("Vertex name missing.");
                    }
                    StringBuilder nameBuilder = new StringBuilder();

                    for (int i = 2; i < splitted.length; i++) {
                        nameBuilder.append(splitted[i] + " ");
                    }
                    nameBuilder.deleteCharAt(nameBuilder.length() - 1);
                    String name = nameBuilder.toString();

                    if (name.isEmpty()) {
                        throw new IOException("Vertex name invalid.");
                    }

                    double x = Double.parseDouble(splitted[0]);
                    double y = Double.parseDouble(splitted[1]);

                    loaded.put(name, new Vertex2D(x, y));
                }

            } catch (NumberFormatException e) {
                throw new IOException("Vertex coordinates invalid.", e);
            }
            vertices.putAll(loaded);
            return this;
        }

        @Override
        public Builder read(File file) throws IOException {

            try (FileInputStream stream = new FileInputStream(file)) {
                read(stream);
            }
            return this;
        }
    }
}
