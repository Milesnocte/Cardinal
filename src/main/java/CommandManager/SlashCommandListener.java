package CommandManager;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listens for the onSlashCommand and onButtonClick events
 * and sends the event to the SlashCommandManager
 */
public class SlashCommandListener extends ListenerAdapter {

    /**
     * Hashmap for the commands with a command name key
     */
    private final SlashCommandManager commandManager = new SlashCommandManager();

    /**
     * Send the event to the SlashCommandManager
     * @param event SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the event to the SlashCommandManager
     * @param event ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
