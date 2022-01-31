package CommandManager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.List;

/**
 * ISlashCommand is the command interface, all commands must use this interface to be recognized
 * by the SlashCommandManager
 */
public interface ISlashCommand {
    /**
     * Run when a slash command is executed
     * @param event SlashCommandEvent
     * @throws Exception to be caught my the manager
     */
    void run(SlashCommandEvent event) throws Exception;

    /**
     * Run when a button component is clicked
     * @param event ButtonClickEvent
     * @throws Exception to be caught my the manager
     */
    void run(ButtonClickEvent event) throws Exception;

    /**
     * List of buttons associated with this command
     */
    List<String> buttons();

    /**
     * Command name is the name the user will see
     */
    String commandName();

    /**
     * Enable or Disabled this command
     */
    Boolean enabled();

    /**
     * Description of this command, will be used to automate the command update process soon
     */
    String description();
}
