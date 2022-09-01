package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.HasLabels;
import cz.muni.fi.pb162.hw02.LabelFilter;
import cz.muni.fi.pb162.hw02.LabelMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Label filter implementation.
 *
 * @author Michal Krejčíř
 */
public final class LabelFilterImpl implements LabelFilter {

    private LabelMatcher matcher;

    /**
     * Creates a new LabelFilterImpl based on expression String.
     * @param expression Expression String to be used for matching.
     */
    public LabelFilterImpl(String expression) {

        this.matcher = new LabelMatcherImpl(expression);
    }

    private Collection<HasLabels> singleMatching(Iterable<HasLabels> labeled, boolean matching) {

        LinkedList<HasLabels> result = new LinkedList<>();
        Iterator<HasLabels> iterator = labeled.iterator();

        while (iterator.hasNext()) {

            HasLabels current = iterator.next();

            if (matcher.matches(current) && matching
                || !matcher.matches(current) && !matching) {
                result.add(current);
            }
        }
        return result;
    }

    @Override
    public Collection<HasLabels> matching(Iterable<HasLabels> labeled) {

        return singleMatching(labeled, true);
    }

    @Override
    public Collection<HasLabels> notMatching(Iterable<HasLabels> labeled) {

        return singleMatching(labeled, false);
    }

    @Override
    public Collection<HasLabels> joined(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {

        Set<HasLabels> firstMatching = new HashSet<>(singleMatching(fst, true));
        Set<HasLabels> secondMatching = new HashSet<>(singleMatching(snd, true));
        firstMatching.addAll(secondMatching);

        return firstMatching;
    }

    @Override
    public Collection<HasLabels> distinct(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {

        Set<HasLabels> firstMatching = new HashSet<>(singleMatching(fst, true));
        Set<HasLabels> secondMatching = new HashSet<>(singleMatching(snd, true));

        Set<HasLabels> result = new HashSet<>(firstMatching);
        result.addAll(secondMatching);
        firstMatching.retainAll(secondMatching);
        result.removeAll(firstMatching);

        return result;
    }

    @Override
    public Collection<HasLabels> intersection(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {

        Set<HasLabels> firstMatching = new HashSet<>(singleMatching(fst, true));
        Set<HasLabels> secondMatching = new HashSet<>(singleMatching(snd, true));
        firstMatching.retainAll(secondMatching);

        return firstMatching;
    }
}
