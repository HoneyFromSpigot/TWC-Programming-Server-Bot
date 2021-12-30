package main.command;

import main.Bot;
import main.command.impl.*;
import main.command.impl.music.JoinCommand;
import main.command.impl.music.NowPlayingCommand;
import main.command.impl.music.PlayCommand;
import main.command.impl.music.StopCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PasteCommand());
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new SendVerifyMessageCommand());
        addCommand(new SendRulesCommand());
        addCommand(new ClearChannelCommand());
        addCommand(new MuteCommand());
        addCommand(new UnmuteCommand());
        addCommand(new JoinCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SendNewsPingMessageCommand());
        /*

        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new PasteCommand());
        addCommand(new HasteCommand());
        addCommand(new KickCommand());
        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        addCommand(new WebhookCommand());
        addCommand(new InstagramCommand());
        addCommand(new MinecraftCommand());

        addCommand(new SetPrefixCommand());

        addCommand(new JoinCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());

         */
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);
            cmd.handle(ctx);

            ctx.getMessage().delete().queue();
        }else{
            event.getChannel().sendMessage("Der Command wurde nicht gefunden. Benutze ``" + Bot.getInstance().getPrefix() + "help `` für weitere Informationen.").queue();
        }
    }
}
