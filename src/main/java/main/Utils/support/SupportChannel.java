package main.Utils.support;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.EnumSet;

public class SupportChannel {
    private TextChannel channel;
    private Member member;
    private Guild guild;

    public SupportChannel(Member member){
        this.member = member;
        this.guild = member.getGuild();
    }

    public void open(){
        String name = "support-" + member.getUser().getName();

        this.channel = guild.createTextChannel(name).addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL)).setParent(guild.getCategoryById("928689577575723079")).complete();

        if (!member.getPermissions(channel).contains(Permission.VIEW_CHANNEL)) {
            PermissionOverride o = channel.createPermissionOverride(member).complete();
            o.getManager().setAllow(EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE)).queue();

        }

        Role supportRole = guild.getRoleById("925697783393034289");

        for(Member m : guild.getMembers()){
            if (m.hasPermission(Permission.MANAGE_SERVER) || m.getRoles().contains(supportRole)) {
                channel.createPermissionOverride(m).queue();
            }
        }


        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.YELLOW);
        builder.setTitle("Ticket-" + member.getUser().getName());
        builder.setDescription("Um das Ticket zu schließen drücke auf das ❌");
        channel.sendMessage(builder.build()).complete().addReaction("❌").queue();

        PrivateChannel pc = member.getUser().openPrivateChannel().complete();
        pc.sendMessage("Dein Support Ticket ist fertig. Du kannst deine Frage in " + channel.getAsMention() + " stellen.").queue();
        pc.close();
    }

    public Member getMember() {
        return member;
    }

    public TextChannel getChannel() {
        return channel;
    }


}
