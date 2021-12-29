package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SendVerifyMessageCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Verifizieren");
        builder.setColor(Color.CYAN);
        builder.setDescription("Bitte verifiziere dich, indem du auf diese Nachricht reagierst. Drücke dazu einfach auf das ✅ unten an dieser Nachricht. Daraufhin solltest du die Verifiziert-Rolle bekommen. Ist dies nicht der Fall," +
                "probiere es erneut.");

        ctx.getGuild().getTextChannelById("925699734587441182").sendMessage(builder.build()).complete().addReaction("✅").queue();
        return;
    }

    @Override
    public String getName() {
        return "sendverifymessage";
    }

    @Override
    public String getHelp() {
        return "Sendet die Verify - Nachricht";
    }
}
