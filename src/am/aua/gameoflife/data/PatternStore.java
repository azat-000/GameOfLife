package am.aua.gameoflife.data;

import java.io.*;
import java.net.*;
import java.util.*;
public class PatternStore {
    public PatternStore(String source) throws IOException {
        if (source.startsWith("http://") || source.startsWith("https://"))
            loadFromURL(source);
        else
            loadFromDisk(source);
    }
    public PatternStore(Reader source) throws IOException {
        load(source);
    }
    private void load(Reader r) throws IOException {
        BufferedReader b = new BufferedReader(r);
        String line;
        while((line = b.readLine()) != null){
            System.out.println(line);
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
    public static void main(String args[]) throws IOException {
        PatternStore p = new PatternStore(args[0]);
    }
}
