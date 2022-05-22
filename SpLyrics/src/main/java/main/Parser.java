package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
    public static String textToGoogleRef(String text){
        return text.replaceAll(" ", "+").toLowerCase();
    }

    public static String getLyrics(String songName) throws Exception {
        Path path = Paths.get("src/main/java/main/Lyrics");
        String ref = songName.replaceAll(" ", "+").toLowerCase();

        Document d = Jsoup.connect("https://www.google.com/search?q="+ref+"+lyrics").get();
        Elements elements = d.getElementsByClass("ujudUb");

        BufferedWriter writer = new BufferedWriter(new FileWriter(path + ""));

        StringBuilder read = new StringBuilder(elements.text());

        for (int i = 0; i < elements.text().length(); i++) {
            if(!(read.charAt(i) + "").equals((read.charAt(i) + "").toLowerCase()) && (read.charAt(i) + "").matches("[a-zA-Z]")){
                if(i != 0){
                    read.setCharAt(i-1, '\n');
                }
            }
        }
        writer.write(String.valueOf(read));

        writer.flush();
        writer.close();
        try {
            return Files.readString(path);
        } catch (Exception e){
            System.out.println("Lyrics file is empty");
        }
        return Files.readString(path);
    }

}
