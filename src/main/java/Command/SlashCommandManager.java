package Command;

import Command.SlashCommands.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.*;

public class SlashCommandManager {

    private final Map<String, ISlashCommand> commands = new HashMap<>();

    SlashCommandManager() {
        addCommand(new Ping());
        addCommand(new Menus());
        addCommand(new PurgeVCText());
        addCommand(new WhoIs());
        addCommand(new Avatar());
        addCommand(new Purge());
        addCommand(new Stats());
        addCommand(new Define());
        addCommand(new EightBall());
        addCommand(new Restart());
        addCommand(new Shutdown());
    }

    private void addCommand(ISlashCommand c) {
        commands.putIfAbsent(c.commandName(), c);
        if(!(c.buttons().isEmpty())){
            for(String k : c.buttons()){
                commands.putIfAbsent(k, c);
            }
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
