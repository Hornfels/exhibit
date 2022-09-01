package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.cmd.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Application Runtime
 */
final class Application {

    private final ApplicationOptions options;
    private final CommandLine cli;
    private final LinkedList<MatcherWithFile> matchers;

    Application(ApplicationOptions options, CommandLine cli) {
        Objects.requireNonNull(options);
        Objects.requireNonNull(cli);

        this.options = options;
        this.cli = cli;
        this.matchers = new LinkedList<>();
    }

    private void initializeMatchers(String colsNames) throws IOException {

        BufferedReader reader = Files.newBufferedReader(options.getFilters(), options.getCharset());

        if (reader.ready()) {
            reader.readLine();
        } else {
            throw new IOException("Filter file empty.");
        }

        while(reader.ready()) {

            String[] line = reader.readLine().split(options.getDelimiter());

            if (line.length != 2) {
                throw new IOException("Filter file record invalid.");
            }

            MatcherWithFile matcher = new MatcherWithFile(line[1], options.getOutput(), line[0], options.getCharset());
            matchers.add(matcher);

            matcher.writeLineToFile(colsNames);
        }
        reader.close();
    }


    private int findLabelCol(String colsNames) throws IOException {

        String[] splitted = colsNames.split(options.getDelimiter());

        int position = 0;
        boolean foundLabels = false;

        for (String col:
                splitted) {

            if (col.matches(options.getLabelColumn())) {
                foundLabels = true;
                break;
            }
            position++;
        }
        if (!foundLabels) {
            throw new IOException("Column with labels not found.");
        }
        return position;
    }


    private void processArticles(BufferedReader inputReader, String colsNames) throws IOException {

        int labelColPosition = findLabelCol(colsNames);

        while (inputReader.ready()) {
            Article current = new Article(inputReader.readLine(), options.getDelimiter(), labelColPosition);

            for (MatcherWithFile matcher :
                    matchers) {

                if (matcher.matches(current)) {
                    matcher.writeLineToFile(current.getArticleRecord());
                }
            }
        }
    }


    /**
     * Note:    This method represents the runtime logic.
     *          However, you should still use proper decomposition.
     *
     * Application runtime logic
     */
    void run() throws IOException {

        if (options.isShowUsage()) {
            cli.printUsage();
            return;
        }

        try {

            BufferedReader inputReader = Files.newBufferedReader(options.getInput(), options.getCharset());
            String colsNames = inputReader.readLine();

            initializeMatchers(colsNames);

            processArticles(inputReader, colsNames);

        } finally {

            for (MatcherWithFile matcher :
                    matchers) {

                matcher.closeFile();

                if (matcher.isFileEmpty()) {
                    matcher.deleteFile();
                }
            }
        }
    }
}
