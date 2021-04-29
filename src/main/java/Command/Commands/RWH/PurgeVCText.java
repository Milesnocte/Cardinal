package Command.Commands.RWH;

import Command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PurgeVCText implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if(event.getGuild().getId().equals("433825343485247499")  && event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            event.getMessage().delete().queue();

            Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).createCopy().queue();

            TimeUnit.SECONDS.sleep(1);

            Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).delete().queue();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.GREEN);
            embed.setDescription("VC-Text purged by " + event.getMessage().getAuthor().getAsTag() + "!");

            Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelById("582399042240118814")).sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String getCommandName() {
        return "purgevc";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return null;
    }
}
