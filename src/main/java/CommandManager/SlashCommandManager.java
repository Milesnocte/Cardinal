package CommandManager;

import CommandManager.SlashCommands.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.*;

/**
 * Handles all SlashCommands and Button clicks
 */
public class SlashCommandManager {

    /**
     * Hashmap for the commands to be stored in
     */
    private final Map<String, ISlashCommand> commands = new HashMap<>();

    // Add commands to the commands hashmap
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
        addCommand(new TopConcentrations());
        addCommand(new TopStars());
        addCommand(new AddChannel());
        addCommand(new StarCheck());
        addCommand(new UpdateCommands());
    }

    /**
     * @param c ISlashCommand to be added to the hashmap
     */
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
            if(commands.get(name).enabled()) {
                commands.get(name).run(event);
            } else {
                event.reply("This command is disabled!").setEphemeral(true).queue();
            }
        }
    }

    void run(ButtonClickEvent event) throws Exception {
        final String name = event.getComponentId();
        if(event.getUser().isBot()){
            return;
        }
        if(commands.containsKey(name)){
            if(commands.get(name).enabled()) {
                commands.get(name).run(event);
            }
        }
    }
}
