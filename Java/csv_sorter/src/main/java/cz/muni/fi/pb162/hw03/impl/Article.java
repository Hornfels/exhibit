package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw02.HasLabels;

import java.util.Collections;
import java.util.Set;

/**
 * Class representing an Article with Labels.
 *
 * @author Michal Krejcir
 */
public class Article implements HasLabels {

    private final Set<String> labels;
    private final String articleRecord;

    /**
     * Creates a new Article.
     * @param articleRecord Information about the Article.
     * @param delimiter Delimiter used for separating fields in articleRecord.
     * @param labelColPosition Position of field containing Labels.
     */
    public Article(String articleRecord, String delimiter, int labelColPosition) {

        this.articleRecord = articleRecord;
        this.labels = Set.of(articleRecord.split(delimiter)[labelColPosition].split(" "));
    }

    @Override
    public Set<String> getLabels() {
        return Collections.unmodifiableSet(labels);
    }

    public String getArticleRecord() {
        return articleRecord;
    }
}
