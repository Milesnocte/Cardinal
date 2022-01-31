package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Stats implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        long currentTime = System.currentTimeMillis();
        String command = "ping 8.8.8.8";
        Process process = Runtime.getRuntime().exec(command);
        process.getOutputStream();
        currentTime = System.currentTimeMillis() - currentTime;

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.cyan);
        embed.setDescription("**Woody By MilesNocte**");
        embed.addField("**Woody's Stats**", "`Ping:` " + currentTime + "ms" +
                "\n`Gateway Ping:` " + event.getJDA().getGatewayPing() + "ms" +
                "\n`Guilds:` " + event.getJDA().getGuilds().size() +
                "\n`Bot ID:` " + event.getJDA().getSelfUser().getId() +
                "\n`Bot API:` JDA 5.0.0-alpha.4", false);
        event.replyEmbeds(embed.build()).addActionRow(
                Button.link("https://discordstatus.com/", "Discord Status")
        ).queue();
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
        return "stats";
    }

    @Override
    public Boolean enabled() {
        return true;
    }
}
