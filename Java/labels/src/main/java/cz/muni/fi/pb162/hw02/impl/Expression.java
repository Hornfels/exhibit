package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.error.InvalidExpressionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a logical expression with operators and labels.
 *
 * @author Michal Krejčíř
 */
public class Expression {

    private final List<OperatorTypes> operators;
    private final List<Label> labels;

    public enum OperatorTypes {
        AND, OR
    }

    /**
     * Divides an expression String into individual Labels and Operators.
     * @param toDivide Expression String to divide.
     * @return Array containing two Arrays - first of divided Labels
     *  and second of Operators.
     */
    private String[][] divideAndCheck(String toDivide) {

        /** Getting rid of all spaces enables to have just one parsing method. **/
        String noSpaces = toDivide.replace(" ", "");

        if (noSpaces.length() == 0) {
            throw new InvalidExpressionException("");
        }
        String[] dividedLabels = (noSpaces.split("&|\\|"));

        /** This split always outputs an empty String as the first member
         * of the new array (and I don't know why). **/
        String[] dividedOperators = noSpaces.split("!*[a-zA-Z0-9]+");

        if (dividedLabels.length != dividedOperators.length
                && (dividedLabels.length != 1 || dividedOperators.length != 0)) {
            throw new InvalidExpressionException(toDivide);
        }

        return new String[][] {dividedLabels, dividedOperators};
    }

    /**
     * Parses and validates individual label Strings with negations
     *  and fills the labels attribute.
     * @param dividedLabels Label Strings to parse.
     */
    private void parseLabels(String[] dividedLabels) {

        for (int i = 0; i < dividedLabels.length; i++) {

            if (dividedLabels[i].matches("^[a-zA-Z0-9]+$")) {
                labels.add(new Label(dividedLabels[i], false));

            } else if (dividedLabels[i].matches("^!+[a-zA-Z0-9]+$")) {
                int negationCount = dividedLabels[i].lastIndexOf("!") + 1;
                labels.add(new Label(dividedLabels[i].replace("!", ""), negationCount % 2 == 1));

            } else {
                throw new InvalidExpressionException(dividedLabels[i]);
            }
        }
    }

    /**
     * Parses and validates individual operator Strings
     *  and fills the operators attribute.
     * @param dividedOperators Operator Strings to parse.
     */
    private void parseOperators(String[] dividedOperators) {

        /** Operator parsing starts from 1, because .split always produces an
         empty String as the first member of dividedOperators array
         (I've tried to fix it, but wasn't successful). **/
        for (int i = 1; i < dividedOperators.length; i++) {

            switch (dividedOperators[i]) {
                case "&":
                    operators.add(OperatorTypes.AND);
                    break;
                case "|":
                    operators.add(OperatorTypes.OR);
                    break;
                default:
                    throw new InvalidExpressionException(dividedOperators[i]);
            }
        }
    }

    /**
     * Creates a new Expression from String.
     * @param toParse String from which the Expression will be created.
     */
    public Expression(String toParse) {

        String[][] divided = divideAndCheck(toParse);

        labels = new ArrayList<>(divided[0].length);
        parseLabels(divided[0]);

        operators = new ArrayList<>(divided[1].length);
        parseOperators(divided[1]);
    }

    public List<OperatorTypes> getOperators() {
        return Collections.unmodifiableList(operators);
    }

    public List<Label> getLabels() {
        return Collections.unmodifiableList(labels);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("Labels: [");

        for (Label l: labels) {
            builder.append(l + ", ");
        }
        builder.setLength(builder.length() - 2);
        builder.append("]\nOperators: [");

        for (OperatorTypes o: operators) {
            builder.append(o + ", ");
        }
        builder.setLength(builder.length() - 2);
        builder.append("]");
        return builder.toString();
    }
}
