package CommandManager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listens for the onSlashCommand and onButtonClick events
 * and sends the event to the SlashCommandManager
 */
public class SlashCommandListener extends ListenerAdapter {

    private final SlashCommandManager commandManager = new SlashCommandManager();

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
