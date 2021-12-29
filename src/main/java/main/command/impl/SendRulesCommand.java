package main.command.impl;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SendRulesCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if(!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)){
            channel.sendMessage("Du kannst diesen Command nich benutzen!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Regeln");
        builder.setDescription("Bitte beachte die folgenden Regeln für diesen Server:\n\n" +
                "**§1 Allgemeine Regeln**\n\n" +
                "1.1 Freundlichkeit ist sehr wichtig. Sei also zu allen Membern freundlich\n" +
                "1.2 Nicknames sollten keine Verbotenen, oder rechtlich geschützten Namen enthalten\n" +
                "1.3 Avatare dürfen keine pornographische, rassistische, oder beleidigende Inhalte beinhalten.\n" +
                "1.4 Jeder Hack- und/oder DDoS-Angriff gegen den Server ist logischer weise untersagt, und wird strafrechtlich verfolgt.\n" +
                "1.5 Private Daten wie Telefonnummern, Email Addressen oder ähnliches dürfen nicht ohne die Erlaubnis des jeweiligen Members ausgetauscht werden.\n" +
                "1.6 Beleidigungen sind zu unterlassen.\n" +
                "1.7 Fremdwerbung ist streng untersagt, außer sie wurde von einem Moderator, oder höherem Rang genehmigt.\n" +
                "1.8 Rassismus und Antisemitismus wird nicht geduldet, und sofort mit einem permanentem Ban bestraft.\n" +
                "1.9 Systembugs dürfen nicht ausgenutzt werden, und sollten in " + ctx.getGuild().getTextChannelById("925697783762153481").getAsMention() + " gemeldet werden.\n" +
                "1.10 Smurfen, also das joinen mit einem 2, oder 3 Account um einen Ban zu umgehen ist streng untersagt.\n" +
                "1.11 Auch hier gilt: Unwissenheit schützt vor Strafe nicht.\n" +
                "\n**§2 Regeln für den Voice Chat**\n\n" +
                "2.1 Auch im Voice Chat sollte niemand angeschrien oder beleidigt werden.\n" +
                "2.2 Bei längerer abwesenheit solltest du den Channel bitte verlassen, oder in den " + ctx.getGuild().getVoiceChannelById("925697783904743429").getAsMention() + " gehen.\n" +
                "2.3 Channel Hopping, also das ständige Springen von einem Channel in den nächsten ist Verboten.\n" +
                "2.4 Die Discord Nutzungsbedingungen und Community Regeln sollten befolgt werden (https://discord.com/terms und https://discord.com/guidelines).\n" +
                "\n**§3 Regeln für den Chat**\n\n" +
                "3.1 Auch hier: Niemand wird beleidigt.\n" +
                "3.2 Spamen ist verboten, und wird mit einem Mute Bestraft, daraufhin mit einem Ban.\n" +
                "3.3 Pornographische, Rassistische und Antisemitistische Inhalte sind verboten.\n" +
                "3.4 Wichtige angelegenheiten mit eine:r Staff sollten privat, oder im entsprechendem Staff/Support Channel besprochen werden.\n" +
                "3.5 Invite Links fallen unter Eigenwerbung, und sind dementsprechend verboten, außer sie wurden von einem Moderator oder jemanden mit höherem Rang genehmigt worden.\n" +
                "3.6 Niemand sollte unnötigt gepingt werden. Vor allem Staff-Mitglieder sollten nur in dringenden Fällen gepingt werden.\n" +
                "\n\n\n" +
                "Da sich die meisten die Regeln wahrscheinlich nicht durchlesen werden hier eine kurze Zusammenfassung: Achte bitte einfach darauf mit den anderen Mitgliedern des Servers nett und freundlich umzugehen, beleidige niemanden, spamme nicht, und lade auch keine unangemessenen Inhalte hoch. \n" +
                "Dann kannst du _eigentlich_ nicht falsch machen.");

        ctx.getGuild().getTextChannelById("925700269696774204").sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "sendrulesmessage";
    }

    @Override
    public String getHelp() {
        return "Sendet die Regeln in den Rules Chat";
    }
}
