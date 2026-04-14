package am.aua.gameoflife.data;

import am.aua.gameoflife.core.Pattern;
import am.aua.gameoflife.exceptions.PatternFormatException;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class PatternStore {
    private Pattern[] patterns;
    public static final int MAX_NUMBER_PATTERNS = 1000;
    private int numberUsed;

    /**
     *
     * @param source - URL/file path
     * @throws IOException
     */
    public PatternStore(String source) throws IOException {
        patterns = new Pattern[MAX_NUMBER_PATTERNS];
        numberUsed = 0;
        if (source.startsWith("http://") || source.startsWith("https://"))
            loadFromURL(source);
        else
            loadFromDisk(source);
    }

    /**
     *
     * @param source -- Reader connected to a source (URL/File path)
     * @throws IOException
     */
    public PatternStore(Reader source) throws IOException {
        patterns = new Pattern[MAX_NUMBER_PATTERNS];
        numberUsed = 0;
        load(source);
    }


    private void load(Reader r) throws IOException {
        BufferedReader b = new BufferedReader(r);
        String line;
        outerLoop:
        while((line = b.readLine()) != null){
            if(numberUsed>=MAX_NUMBER_PATTERNS) break;
            System.out.println(line);
            try{
                Pattern current = new Pattern(line);
                for(int i =0; i<numberUsed; i++){
                    if(current.equals(patterns[i]))
                        continue outerLoop;
                }
                patterns[numberUsed] = current;
                numberUsed++;
            } catch (PatternFormatException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
            }
        }
        b.close();
    }
    private void loadFromURL(String url) throws IOException {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }
    private void loadFromDisk(String filename) throws IOException {
        Reader r = new FileReader(filename);
        load(r);
    }

    /**
     * Returns a copy of the patterns stored in the object as an array.
     * @return a copy of the patterns stored in the object as an array.
     */
    public Pattern[] getPatterns() {
        Pattern[] copy = new Pattern[numberUsed];
        for(int i = 0; i<numberUsed; i++)
            copy[i] = patterns[i];
        return copy;
    }

    /**
     * Returns a sorted array of the authors of the patterns.
     * @return a sorted array of the authors of the patterns.
     */
    public String[] getPatternAuthors() {
        String[] authors = new String[numberUsed];
        for(int i = 0; i<authors.length; i++)
            authors[i] = patterns[i].getAuthor();
        Arrays.sort(authors);
        return authors;
    }

    /**
     * Returns a sorted array of the names of the patterns.
     * @return a sorted array of the names of the patterns.
     */
    public String[] getPatternNames() {
        String[] names = new String[numberUsed];
        for(int i = 0; i<names.length; i++)
            names[i] = patterns[i].getName();
        Arrays.sort(names);
        return names;
    }


//    public static void main(String args[]) throws IOException {
//        PatternStore p = new PatternStore(args[0]);
//    }
}
