package Listeners;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import java.util.Iterator;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.Guild;
import Main.ScheduledTask;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotEventsListener extends ListenerAdapter
{
    @Override
    public void onReady(ReadyEvent event) {
        new ScheduledTask(event);
    }
    
    @Override
    public void onGuildJoin(@NotNull final GuildJoinEvent event) {
    }
    
    @Override
    public void onGuildLeave(@NotNull final GuildLeaveEvent event) {
    }
}
