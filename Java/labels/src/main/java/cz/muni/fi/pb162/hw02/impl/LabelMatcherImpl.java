package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.HasLabels;
import cz.muni.fi.pb162.hw02.LabelMatcher;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Label matcher implementation.
 *
 * @author Michal Krejčíř
 */
public final class LabelMatcherImpl implements LabelMatcher {

    private Expression expression;

    private enum IterableMatchingTypes {
        ALL, ANY, NONE
    }

    /**
     * Creates a new LabelMatcherImpl based on expression String.
     * @param expression Expression String to be used for matching.
     */
    public LabelMatcherImpl(String expression) {

        this.expression = new Expression(expression);
    }

    /**
     * Decides if the labeled object satisfies the given Label (i.e contains
     *  the Label if it's not negated or doesn't contain it if it's negated).
     * @param labeled Object with labels.
     * @param label Label to look for in the labeled object.
     * @return True if the object satisfies the Label, false otherwise.
     */
    private boolean doesSatisfyLabel(HasLabels labeled, Label label) {

        if (label.isNegated()) {
            return !labeled.getLabels().contains(label.getLabel());
        } else {
            return labeled.getLabels().contains(label.getLabel());
        }
    }

    @Override
    public boolean matches(HasLabels labeled) {

        ListIterator<Label> labels = expression.getLabels().listIterator();
        ListIterator<Expression.OperatorTypes> operators = expression.getOperators().listIterator();

        boolean currentEvaluation = doesSatisfyLabel(labeled, labels.next());

        while (labels.hasNext()) {

            /** Evaluation works by applying next operator on previous result and next label. */
            Expression.OperatorTypes currentOperator = operators.next();
            Label currentLabel = labels.next();

            /** Includes lazy evaluation for slightly increased performance. */
            if (currentEvaluation == (currentOperator == Expression.OperatorTypes.AND)) {
                currentEvaluation = doesSatisfyLabel(labeled, currentLabel);
            }
        }
        return currentEvaluation;
    }

    /**
     * Combines the logic for evaluating different variants of Iterable matching.
     * @param labeled Iterable of labeled items to evaluate.
     * @param type Type of desired matching operation.
     * @return MatchReturn if any HasLabels item from the Iterable satisfies shouldMatch
     *  (i.e. matches if shouldMatch is true or doesn't match if shouldMatch is false),
     *  !matchReturn otherwise.
     */
    private boolean iterableMatching(Iterable<HasLabels> labeled, IterableMatchingTypes type) {

        boolean shouldMatch = type == IterableMatchingTypes.ANY || type == IterableMatchingTypes.NONE;
        boolean matchReturn = type == IterableMatchingTypes.ANY;

        Iterator<HasLabels> iterator = labeled.iterator();

        while (iterator.hasNext()) {

            if (matches(iterator.next()) == shouldMatch) {
                return matchReturn;
            }
        }
        return !matchReturn;
    }

    @Override
    public boolean all(Iterable<HasLabels> labeled) {

        return iterableMatching(labeled, IterableMatchingTypes.ALL);
    }

    @Override
    public boolean any(Iterable<HasLabels> labeled) {

        return iterableMatching(labeled, IterableMatchingTypes.ANY);
    }

    @Override
    public boolean none(Iterable<HasLabels> labeled) {

        return iterableMatching(labeled, IterableMatchingTypes.NONE);
    }
}
