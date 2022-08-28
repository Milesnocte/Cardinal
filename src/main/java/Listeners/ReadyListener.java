package Listeners;

import CommandManager.SlashCommandData;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event)
    {
        event.getJDA().updateCommands().addCommands(
                SlashCommandData.commands
        ).queue();
    }
}
