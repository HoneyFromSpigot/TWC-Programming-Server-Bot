package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.List;

public class ClearChannelCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        List<String> args = ctx.getArgs();

        if(args.size() < 1){
            channel.createCopy().queue();
            channel.delete().queue();
            return;
        }

        try{

            TextChannel c = ctx.getMessage().getMentionedChannels().get(0);
            c.delete().queue();
            c.createCopy().queue();
        }catch(ErrorResponseException e){
            channel.sendMessage("Der Channel kann nicht gelöscht, und deswegen auch nicht gecleart werden.").queue();
        }

    }

    @Override
    public String getName() {
        return "clearchannel";
    }

    @Override
    public String getHelp() {
        return "Löscht alle Nachrichten in einem Channel.";
    }
}
