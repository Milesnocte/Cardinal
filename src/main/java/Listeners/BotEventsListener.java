package Listeners;

import CommandManager.SlashCommandData;
import Main.FetchUNCC;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import Main.ScheduledTask;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Calendar;

public class BotEventsListener extends ListenerAdapter
{
    private String[] UNCCServers = {
            "931663140687585290", // Party Hours
            "433825343485247499", // Woodward Hours
            "935650201291620392", // Charlotte Haven
            "778743841187823636" // Fretwell hours
    };
    @Override
    public void onReady(ReadyEvent event) {
        new ScheduledTask(event);
        event.getJDA().updateCommands().addCommands(
                SlashCommandData.commands
        ).queue();
        for(String server : UNCCServers) {
            event.getJDA().getGuildById(server).updateCommands().addCommands(
                    SlashCommandData.UNCCcommands
            ).queue();
        }

        try {
            new FetchUNCC().screenshot();
            int users = 0;
            for (Guild guild : event.getJDA().getGuilds()) {
                users += guild.getMemberCount();
            }
            event.getJDA().getPresence().setActivity(Activity.watching( users + " Users"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
    }
    
    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
    }
}
