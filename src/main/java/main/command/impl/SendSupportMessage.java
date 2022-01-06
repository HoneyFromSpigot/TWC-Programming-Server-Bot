package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SendSupportMessage implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.CYAN);
        builder.setTitle("Support");
        builder.setDescription("Um ein Ticket zu erstellen reagiere mit \uD83D\uDCF0 .");

        ctx.getGuild().getTextChannelById("928688751608205392").sendMessage(builder.build()).complete().addReaction("\uD83D\uDCF0").queue();
    }


    @Override
    public String getName() {
        return "sendsupportmessage";
    }

    @Override
    public String getHelp() {
        return "Sendet die Support-Message";
    }
}
