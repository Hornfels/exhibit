package cz.muni.fi.pb162.hw02.impl;

/**
 * Class representing one label including it's negation.
 *
 * @author Michal Krejčíř
 */
public final class Label {

    private String label;
    private boolean negated;

    /**
     * Creates a new label.
     * @param label Text of the label.
     * @param negated Whether the label is negated.
     */
    public Label(String label, boolean negated) {

        this.label = label;
        this.negated = negated;
    }

    public String getLabel() {
        return label;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public String toString() {

        if (negated) {
            return "!" + label;
        } else {
            return label;
        }
    }
}
