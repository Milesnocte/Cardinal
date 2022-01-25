package Command;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.List;

public interface ISlashCommand {
    void run(SlashCommandEvent event) throws Exception;
    void run(ButtonClickEvent event) throws Exception;
    List<String> buttons();
    String commandName();
    Boolean enabled();
}
