package Listeners;

import CommandManager.SlashCommandData;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import Main.ScheduledTask;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotEventsListener extends ListenerAdapter
{
    @Override
    public void onReady(ReadyEvent event) {
        new ScheduledTask(event);
        event.getJDA().updateCommands().addCommands(
                SlashCommandData.commands
        ).queue();
    }
    
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
    }
    
    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
    }
}
