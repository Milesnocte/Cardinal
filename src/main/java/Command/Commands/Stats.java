package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Stats implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        long currentTime = System.currentTimeMillis();
        String command ="ping 8.8.8.8";
        Process process = Runtime.getRuntime().exec(command);
        process.getOutputStream();
        currentTime = System.currentTimeMillis() - currentTime;

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.cyan);
        embed.setDescription("**Woody By MilesNocte**");
        int trim = event.getJDA().getRateLimitPool().toString().indexOf("[");
        embed.addField("**Statistics**","`Ping:` " + currentTime + "ms" +
                "\n`Gateway Ping:` " + event.getJDA().getGatewayPing() +
                "\n`Guilds:` " + event.getJDA().getGuilds().size() +
                "\n`Bot ID:` " + event.getJDA().getSelfUser().getId() +
                "\n`Rate Pool:` \n" + event.getJDA().getRateLimitPool().toString().substring(trim) +
                "\nBot API: `JDA 4.2.0_227`", false);
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getCommandName() {
        return "stats";
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
