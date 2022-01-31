package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PurgeVCText implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if (event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).createCopy().queue();
            TimeUnit.SECONDS.sleep(1);
            event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).delete().queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.GREEN);
            embed.setDescription("VC-Text purged by " + event.getMember().getAsMention() + "!");
            Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelById("582399042240118814").sendMessageEmbeds(embed.build()).queue();

            event.reply("Deleted vc text").setEphemeral(true).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {
    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "purgevctxt";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }
}
