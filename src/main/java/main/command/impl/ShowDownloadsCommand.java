package main.command.impl;

import main.Utils.Github;
import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class ShowDownloadsCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        Github github = new Github();
        String filesInRepo = github.getFilesInRepo();
        String[] fileNames = github.getFileNames(filesInRepo);

        EmbedBuilder b = new EmbedBuilder();
        b.setColor(Color.WHITE);
        b.setTitle("Downloads: ");

        for(String s : fileNames){
            b.appendDescription(s + "\n");
        }

        channel.sendMessage(b.build()).queue();
    }

    @Override
    public String getName() {
        return "showdownloads";
    }

    @Override
    public String getHelp() {
        return "Zeigt eine Liste aller Verfügbaren Downloads an.";
    }
}
