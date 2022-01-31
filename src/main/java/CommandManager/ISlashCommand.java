package CommandManager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.List;

/**
 * @implNote ISlashCommand is the command interface, all commands must use this interface to be recognized
 * by the SlashCommandManager
 */
public interface ISlashCommand {
    void run(SlashCommandEvent event) throws Exception; // Run on command execution
    void run(ButtonClickEvent event) throws Exception; // Run on button click
    List<String> buttons(); // List of buttons the command is associated with
    String commandName(); // The command name is how the command will be displayed to the user
    Boolean enabled(); // Enable or disable the command
}
