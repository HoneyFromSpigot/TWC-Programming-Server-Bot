package main.Utils.support;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class SupportManager {
    private ArrayList<SupportChannel> supportChannels;

    public SupportManager(){
        this.supportChannels = new ArrayList();
    }

    public void createNew(Member member){
        SupportChannel channel = new SupportChannel(member);
        add(channel);
    }

    public void add(SupportChannel channel){
        supportChannels.add(channel);
    }
}
