package Listeners;

import Main.ScheduledTask;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BotEventsListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        // Create scheduled task that runs once a day
        new ScheduledTask(event);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        boolean roleExists = (event.getGuild().getRolesByName("InVC", true).isEmpty());
        if(roleExists) {
            event.getGuild().createRole().setMentionable(true).setName("InVC").setColor(Color.CYAN).queue();
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {

    }
}
