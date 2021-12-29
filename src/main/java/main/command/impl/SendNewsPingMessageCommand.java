package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SendNewsPingMessageCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.LIGHT_GRAY);
        builder.setDescription("Um bei neuigkeiten einen Ping zu erhalten reagiere bitte auf diese Nachricht. Klicke einfach unten auf das ✅ um dich einzutragen. Klicke auf das ❌ um keine weiteren Benachrichtigungen zu erhalten.");
        Message message = ctx.getGuild().getTextChannelById("925697783762153472").sendMessage(builder.build()).complete();
        message.addReaction("✅").queue();
        message.addReaction("❌").queue();
    }

    @Override
    public String getName() {
        return "sendnewspingmessage";
    }

    @Override
    public String getHelp() {
        return "Sendet die News Ping Message.";
    }
}
