package Command;

import Command.SlashCommands.Concentrations;
import Command.SlashCommands.Ping;
import Command.SlashCommands.YearRoles;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.*;

public class SlashCommandManager {

    private final Map<String, ISlashCommand> commands = new HashMap<>();

    SlashCommandManager() {
        addCommand(new Ping());
        addCommand(new YearRoles());
        addCommand(new Concentrations());
    }

    private void addCommand(ISlashCommand c) {
        if(!commands.containsKey(c.commandName())){
            commands.put(c.commandName(), c);
        }
        if(!(c.buttons().isEmpty())){
            for(String k : c.buttons()){
                commands.put(k, c);
            }
        }else{
            System.out.println(c.commandName() + " had no buttons");
        }
    }

    public Collection<ISlashCommand> getCommands(){
        return commands.values();
    }

    void run(SlashCommandEvent event) throws Exception {
        final String name = event.getName();
        if(event.getUser().isBot()){
            return;
        }
        if(commands.containsKey(name)){
            commands.get(name).run(event);
        }
    }
    void run(ButtonClickEvent event) throws Exception {
        final String name = event.getComponentId();
        if(event.getUser().isBot()){
            return;
        }
        if(commands.containsKey(name)){
            commands.get(name).run(event);
        }
    }
}
