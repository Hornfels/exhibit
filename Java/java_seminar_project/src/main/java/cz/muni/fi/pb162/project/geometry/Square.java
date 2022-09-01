package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Square in a 2D plane.
 *
 * @author Michal Krejcir
 */
public class Square extends GeneralRegularPolygon implements Circular {

    /**
     * Creates a new Square with the given center and diameter.
     * @param center Center Vertex of the Square.
     * @param diameter Diameter of the Square.
     */
    public Square(Vertex2D center, double diameter) {
        super(center, 4, diameter / 2);
    }

    /**
     * Creates a new Square based on the center coordinates
     * and radius of the given object.
     * @param object Object whose center and radius are used for Square creation.
     */
    public Square(Circular object) {
        this(new Vertex2D( object.getCenter().getX(), object.getCenter().getY()),
                object.getRadius() * 2);
    }

    ///**
    // * Returns the intex-th Vertex of the Square.
    // * @param index Index of the desired vertex ranging from 0 to 3.
    // * @return Intex-th Vertex of the Square.
    // */
    //public Vertex2D getVertex(int index) {
//
    //    return super.getVertex(index);

        //index = Math.abs(index);
//
        //if (index > 3) {
        //    index = index % 4;
        //}
//
        //switch (index) {
//
        //    case 0:
        //        return new Vertex2D(getCenter().getX() - getRadius(), getCenter().getY());
        //    case 1:
        //        return new Vertex2D(getCenter().getX(), getCenter().getY() - getRadius());
        //    case 2:
        //        return new Vertex2D(getCenter().getX() + getRadius(), getCenter().getY());
        //    case 3:
        //        return new Vertex2D(getCenter().getX(), getCenter().getY() + getRadius());
        //    default:
        //        return null;
        //}
    //}

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("Square: vertices=");

        for (int i = 0; i < 4; i++) {
            builder.append(getVertex(i).toString());
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}
