package main.Utils.support;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.util.ArrayList;

public class SupportManager {
    private ArrayList<SupportChannel> supportChannels;

    public SupportManager(){
        this.supportChannels = new ArrayList();
    }

    public void createNew(Member member){
        for(SupportChannel c : supportChannels){
            if (c.getMember().equals(member)) {
                PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();

                privateChannel.sendMessage("Dein Ticket konnte nicht erstellt werden, da du bereits ein Ticket offen hast!").queue();
                privateChannel.close();
            }
        }

        SupportChannel channel = new SupportChannel(member);
        channel.open();
        add(channel);
    }

    public ArrayList<SupportChannel> getSupportChannels() {
        return supportChannels;
    }

    public void close(SupportChannel channel){
        if (supportChannels.contains(channel)) {
            supportChannels.remove(channel);
        }

        channel.close();
    }

    public void add(SupportChannel channel){
        supportChannels.add(channel);
    }
}
