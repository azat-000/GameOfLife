package am.aua.gameoflife.data;

import am.aua.gameoflife.core.Pattern;
import am.aua.gameoflife.exceptions.PatternFormatException;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PatternStore {
    private ArrayList<Pattern> patterns;
    /**
     *
     * @param source - URL/file path
     * @throws IOException
     */
    public PatternStore(String source) throws IOException {
        patterns = new ArrayList<>();
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
        patterns = new ArrayList<>();
        load(source);
    }


    private void load(Reader r) throws IOException {
        BufferedReader b = new BufferedReader(r);
        String line;
        outerLoop:
        while((line = b.readLine()) != null){
            System.out.println(line);
            try{
                Pattern current = new Pattern(line);
                for(int i =0; i<patterns.size(); i++){
                    if(current.equals(patterns.get(i)))
                        continue outerLoop;
                }
                patterns.add(current);
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
    public Pattern[] getPatternsNameSorted() {
        ArrayList<Pattern> copy = new ArrayList<>(patterns);
        Collections.sort(copy);
        Pattern[] copyArray = new Pattern[copy.size()];
        return copy.toArray(copyArray);
    }

    /**
     * Returns a sorted array of the authors of the patterns.
     * @return a sorted array of the authors of the patterns.
     */
    public String[] getPatternAuthors() {
        String[] authors = new String[patterns.size()];
        for(int i = 0; i<authors.length; i++)
            authors[i] = patterns.get(i).getAuthor();
        Arrays.sort(authors);
        return authors;
    }

    /**
     * Returns a sorted array of the names of the patterns.
     * @return a sorted array of the names of the patterns.
     */
    public String[] getPatternNames() {
        String[] names = new String[patterns.size()];
        for(int i = 0; i<names.length; i++)
            names[i] = patterns.get(i).getName();
        Arrays.sort(names);
        return names;
    }


//    public static void main(String args[]) throws IOException {
//        PatternStore p = new PatternStore(args[0]);
//    }
}
