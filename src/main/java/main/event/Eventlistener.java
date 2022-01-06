package main.event;

import main.Bot;
import main.command.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class Eventlistener extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Bot erfolgreich gestartet");
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("925697783393034283")).queue();
        TextChannel channel = event.getGuild().getTextChannelById("925697783762153476");
        String message = String.format("Willkommen %s auf dem %s",
                event.getMember().getUser().getAsMention(), event.getGuild().getName());

        channel.sendMessage(message).queue();
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;
        String reaction = event.getReaction().getReactionEmote().getEmoji();

        if(!Bot.getInstance().getSupportManager().getChannels().isEmpty()){
            for(String s : Bot.getInstance().getSupportManager().getChannels()){
                if(event.getChannel().getId().equals(s)){
                    if(!reaction.equals("❌")){
                        event.getReaction().removeReaction(event.getUser()).queue();
                        return;
                    }

                    closeSupportChannel(event.getTextChannel());
                    event.getReaction().removeReaction(event.getUser()).queue();
                    return;
                }
            }
        }


        //Support Channel
        if (event.getChannel().getId().equals("928688751608205392")) {
            if(!reaction.equals("\uD83D\uDCF0")){
                event.getReaction().removeReaction(event.getUser()).queue();
                return;
            }
            openSupportChannel(event.getMember());
            event.getReaction().removeReaction(event.getUser()).queue();


        }

        //Verify Channel
        if(event.getChannel().getId().equals("925699734587441182")){
            if(!reaction.equals("✅")){
                event.getReaction().removeReaction(event.getUser()).queue();
                return;
            }
            event.getReaction().removeReaction(event.getUser()).queue();
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("925700563973320755")).queue();
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("925717484718403626")).queue();
        }

        //News Channel
        if (event.getChannel().getId().equals("925697783762153472") && event.getMessageId().equals("925770909661405184")) {
            Role add = event.getGuild().getRoleById("925717484718403626");
            Member member = event.getMember();
            if(reaction.equals("✅")){
                if(!member.getRoles().contains(add)){
                    event.getGuild().addRoleToMember(member, add).queue();
                }
                event.getReaction().removeReaction(event.getUser()).queue();
                return;
            }

            if(reaction.equals("❌")){
                if(member.getRoles().contains(add)){
                    event.getGuild().removeRoleFromMember(member, add).queue();
                }
                event.getReaction().removeReaction(event.getUser()).queue();
                return;
            }
        }
    }

    private void closeSupportChannel(TextChannel channel){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setTitle("Ticket geschlossen");
        builder.setDescription("Das Ticket wurde geschlossen. Der Channel wird in 10 Sekunden automatisch gelöscht.");

        channel.sendMessage(builder.build()).queue();
        channel.delete().queueAfter(10, TimeUnit.SECONDS);
    }

    private void openSupportChannel(Member member){
        String channelName = "support-" + member.getUser().getName();

        Guild guild = member.getGuild();
        Category parent = guild.getCategoryById("928689577575723079");
        TextChannel channel = guild.createTextChannel(channelName).addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL)).setParent(parent).complete();


        if(!member.getPermissions().contains(Permission.VIEW_CHANNEL)){
            channel.createPermissionOverride(member).queue();
        }

        Role supporter = guild.getRoleById("925697783393034289");

        for(Member m : guild.getMembers()){
            if (m.hasPermission(Permission.MANAGE_SERVER) || m.getRoles().contains(supporter)) {
                channel.createPermissionOverride(m).queue();
            }
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.YELLOW);
        builder.setTitle("Ticket-" + member.getUser().getName());
        builder.setDescription("Um das Ticket zu schließen drücke auf das ❌");
        channel.sendMessage(builder.build()).complete().addReaction("❌").queue();
        Bot.getInstance().getSupportManager().add(channel.getId());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final long guildId = event.getGuild().getIdLong();
        String prefix = Bot.getInstance().getPrefix();
        String raw = event.getMessage().getContentRaw();

        boolean admin = false;

        for(Role role : event.getMember().getRoles()){
            if (role.getId().equals("925697783418224671")) {
                admin = true;
            }
        }

        if (raw.equalsIgnoreCase(prefix + "shutdown")) {
            if(admin){
                event.getChannel().sendMessage("Herunterfahren...").queue();
                event.getJDA().shutdown();
                return;
            }else{
                event.getChannel().sendMessage("Du kannst den Bot nicht herunterfahren!").queue();
                return;
            }
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);
        }
    }
}
