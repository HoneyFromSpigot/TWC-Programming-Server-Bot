package main.command.impl;

import main.Bot;
import main.command.CommandContext;
import main.command.CommandManager;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            String prefix = Bot.getInstance().getPrefix();

            builder.setTitle("Liste aller Commands:");
            builder.setColor(Color.WHITE);

            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.appendDescription("``")
                            .appendDescription(prefix)
                            .appendDescription(it)
                            .appendDescription("``\n")
            );

            channel.sendMessage(builder.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Command " + search + " wurde nicht gefunden!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.WHITE);
        builder.setTitle("Hilfe zu " + command.getName());
        builder.setDescription(command.getHelp());

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Zeigt eine Liste aller Commands an, oder genaue Informationen zu einem einzelnen Command. Benutze ``" + Bot.getInstance().getPrefix() + "help`` oder ``" + Bot.getInstance().getPrefix() + "help <COMMAND>``.";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}
