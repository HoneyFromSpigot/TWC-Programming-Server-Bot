package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class UnmuteCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nicht benutzen!").queue();
            return;
        }

        if(ctx.getArgs().size() < 1){
            channel.sendMessage("Bitte gebe einen Member an!").queue();
            return;
        }

        Member member = ctx.getMessage().getMentionedMembers().get(0);

        if(member == null){
            channel.sendMessage("Der Member wurde nicht gefunden!").queue();
            return;
        }

        Role muted = ctx.getGuild().getRoleById("925751360526381066");

        if(member.getRoles().contains(muted)){
            ctx.getGuild().removeRoleFromMember(member, muted).queue();
            channel.sendMessage(member.getAsMention() + " wurde entmuted.").queue();
            return;
        }

        channel.sendMessage("Der Member ist garnicht gemutet!").queue();
    }

    @Override
    public String getName() {
        return "unmute";
    }

    @Override
    public String getHelp() {
        return "Entmutet einen Member";
    }
}
