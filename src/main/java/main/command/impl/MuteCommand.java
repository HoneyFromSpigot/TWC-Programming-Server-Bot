package main.command.impl;

import main.Bot;
import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class MuteCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        if(ctx.getArgs().size() < 1){
            channel.sendMessage("Bitte benutze ``" + Bot.getInstance().getPrefix() + "mute <MEMBER>`` oder ``" + Bot.getInstance().getPrefix() + "help " + getName() + "`` für weitere Informationen.").queue();
            return;
        }

        Member member = ctx.getMessage().getMentionedMembers().get(0);
        if(member == null){
            channel.sendMessage("Der Member wurde nicht gefunden!").queue();
            return;
        }

        ctx.getGuild().addRoleToMember(member, ctx.getGuild().getRoleById("925751360526381066")).queue();
        channel.sendMessage(String.format("%s wurde gemutet.", member.getAsMention())).queue();

    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getHelp() {
        return "Mutet einen Member auf unbestimmte Zeit.";
    }
}
