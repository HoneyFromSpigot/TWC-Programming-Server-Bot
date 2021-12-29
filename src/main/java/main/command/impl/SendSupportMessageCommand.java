package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SendSupportMessageCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.LIGHT_GRAY);
        builder.setTitle("Support");
        builder.setDescription("Um ein Support Ticket zu lösen reagiere bitte auf diese Nachricht. Klicke dafür auf das ✅ unten an der Nachricht.");

        ctx.getGuild().getTextChannelById("925697783762153479").sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "sendsupportmessage";
    }

    @Override
    public String getHelp() {
        return "Sendet die Support-Message.";
    }
}
