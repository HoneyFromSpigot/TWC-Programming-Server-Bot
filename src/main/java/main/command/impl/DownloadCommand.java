package main.command.impl;

import main.Bot;
import main.Utils.Github;
import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class DownloadCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(ctx.getArgs().size() < 1){
            channel.sendMessage("Benutze " + Bot.getInstance().getPrefix() + getName() + " <Episoden/Tutorial Name>").queue();
            return;
        }

        Github github = new Github();

        String json = github.getFilesInRepo();
        String[] s = github.getFileNames(json);

        String file = ctx.getArgs().get(0);
        String wantedFile = github.getMostMatching(file, s);

        try{
            File f = new File(wantedFile);

            if (!f.exists()) {
                System.out.println("Downloade eine Datei: " + wantedFile);
                FileUtils.copyURLToFile(new URL(github.getFileUrl(wantedFile)), f);
            }
            ctx.getChannel().sendMessage(wantedFile).addFile(f).queue();
            return;
        }catch (Exception e){
            channel.sendMessage("Es gab einen Fehler beim Download. Kontaktiere den Support wenn der Fehler weiterhin besteht.").queue();
            return;
        }
    }

    @Override
    public String getName() {
        return "download";
    }

    @Override
    public String getHelp() {
        return "Downloadet datein aus Tutorials. Benutze !!download <Tutorial/Episoden Name>";
    }
}
