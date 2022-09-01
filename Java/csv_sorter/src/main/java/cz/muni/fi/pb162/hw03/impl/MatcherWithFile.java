package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw02.HasLabels;
import cz.muni.fi.pb162.hw02.impl.LabelMatcherImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents a LabelMatcher with an associated output file.
 *
 * @author Michal Krejcir
 */
public class MatcherWithFile {

    private final LabelMatcherImpl matcher;
    private final Path outputFilePath;
    private BufferedWriter fileWriter;
    private final Charset fileCharset;


    /**
     * Creates a new MatcherWithFile, creates the output file and enables writing into it.
     * @param expression Expression used for matching HasLabels objects.
     * @param outputDirectory Directory for the output file.
     * @param name Name of the output file (without the .csv suffix)
     * @param charset Charset used for the output file.
     * @throws IOException
     */
    public MatcherWithFile(String expression, Path outputDirectory, String name, Charset charset) throws IOException {

        this.matcher = new LabelMatcherImpl(expression);
        this.outputFilePath = Paths.get(outputDirectory + FileSystems.getDefault().getSeparator() + name + ".csv");
        this.fileWriter = Files.newBufferedWriter(this.outputFilePath, charset);
        this.fileCharset = charset;
    }


    /**
     * Writes the given String into the output file followed by a newline character.
     * @param toWrite Line to write.
     * @throws IOException
     */
    public void writeLineToFile(String toWrite) throws IOException {

        if (fileWriter == null) {
            throw new IOException("File writer is closed.");
        }
        fileWriter.write(toWrite);
        fileWriter.newLine();
    }


    /**
     * Decides if the given HasLabels objects matches the matcher.
     * @param labeled Object to match.
     * @return True if the object matches, false otherwise.
     */
    public boolean matches(HasLabels labeled) {

        return matcher.matches(labeled);
    }


    /**
     * Decides if the output file of the matcher has any data written in it
     * (except the first line with column names).
     * @return True if the file is empty, false otherwise.
     * @throws IOException
     */
    public boolean isFileEmpty() throws IOException {

        try {
            BufferedReader reader = Files.newBufferedReader(outputFilePath, fileCharset);
            reader.readLine();
            return !reader.ready();

        } catch (IOException e) {
            return true;
        }
    }


    /**
     * Closes the matcher's output file.
     * @throws IOException
     */
    public void closeFile() throws IOException {

        if (fileWriter == null) {
            return;
        }
        fileWriter.flush();
        fileWriter.close();
        fileWriter = null;
    }


    /**
     * Deletes the matcher's output file.
     * @throws IOException
     */
    public void deleteFile() throws IOException {

        Files.delete(outputFilePath);
        fileWriter = null;
    }

}
