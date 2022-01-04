package main.Utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Github {
    /*
    * URLS:
    * https://api.github.com/repos/thewebcode/Youtube-Downloads/git/trees/main?recursive=1 -> Get Files in repo
    * https://api.github.com/repos/thewebcode/Youtube-Downloads/contents/Experience-To-Border.zip -> Get download link
    *
    * */
    public String getFilesInRepo(){
        String url = "https://api.github.com/repos/thewebcode/Youtube-Downloads/git/trees/main?recursive=1";
        return request(url);
    }

    public String[] getFileNames(String json){
        try{
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(json).getAsJsonObject();
            JsonArray tree = object.get("tree").getAsJsonArray();

            String[] a = new String[tree.size()];
            for(int i = 0; i < tree.size(); i++){
                a[i] = tree.get(i).getAsJsonObject().get("path").getAsString();
            }

            return a;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String getFileUrl(String fileName){
        try{
            String url = "https://api.github.com/repos/thewebcode/Youtube-Downloads/contents/" + fileName;
            String json = request(url);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(json).getAsJsonObject();
            return jsonObject.get("download_url").getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private String request(String url){
        StringBuilder b = new StringBuilder();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(b::append).join();

        return b.toString();
    }

    public String getMostMatching(String searched, String[] files){
        char ch[] = removeDuplicates(searched.toLowerCase());

        ArrayList<String> list = new ArrayList();

        for(int i = 0; i < files.length; i++){
            list.add(files[i]);
        }

        return getMaxMatchingString(list, ch);
    }

    private String getMaxMatchingString(ArrayList<String> list, char ch[]){
        String value = "";

        int maxValue = Integer.MIN_VALUE;

        for(String s : list){
            int matchingChar = matchingChar(s.toLowerCase(), ch);

            if(matchingChar > maxValue){
                maxValue = matchingChar;
                value = s;
            }
        }

        return value;
    }

    private int matchingChar(String s, char[] ch){
        int freq = 0, c = ch.length;

        for(int i = 0; i < c; i++){
            if (s.contains(String.valueOf(ch[i]))) {
                freq++;
            }
        }

        return freq;
    }

    private char[] removeDuplicates(String src){
        HashSet<Character> set = new HashSet<>();

        int length = src.length();

        for(int i = 0; i < length; i++){
            set.add(src.charAt(i));
        }

        char arr[] = new char[set.size()];
        int index = 0;

        for(char s : set){
            arr[index] = s;
            index++;
        }

        return arr;
    }
}
