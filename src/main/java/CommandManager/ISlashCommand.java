package CommandManager;

import java.util.List;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ISlashCommand
{
    void run(SlashCommandInteractionEvent event) throws Exception;
    
    void run(ButtonInteractionEvent event) throws Exception;
    
    void run(SelectMenuInteractionEvent event) throws Exception;
    
    List<String> buttons();
    
    String commandName();
    
    Boolean enabled();
    
    String description();
}
