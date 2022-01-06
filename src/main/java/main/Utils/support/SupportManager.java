package main.Utils.support;

import java.util.ArrayList;

public class SupportManager {
    private ArrayList<String> channels;

    public SupportManager(){
        this.channels = new ArrayList<>();
    }

    public void add(String id){
        channels.add(id);
    }

    public void remove(String id){
        if (channels.contains(id)) {
            channels.remove(id);
        }
    }

    public ArrayList<String> getChannels() {
        return channels;
    }
}
