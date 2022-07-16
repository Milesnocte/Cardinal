package CommandManager;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import CommandManager.SlashCommands.UNCC;
import CommandManager.SlashCommands.Update;
import CommandManager.SlashCommands.UpdateCommands;
import CommandManager.SlashCommands.StarCheck;
import CommandManager.SlashCommands.TopStars;
import CommandManager.SlashCommands.TopConcentrations;
import CommandManager.SlashCommands.Shutdown;
import CommandManager.SlashCommands.Restart;
import CommandManager.SlashCommands.EightBall;
import CommandManager.SlashCommands.Define;
import CommandManager.SlashCommands.Stats;
import CommandManager.SlashCommands.Purge;
import CommandManager.SlashCommands.Avatar;
import CommandManager.SlashCommands.WhoIs;
import CommandManager.SlashCommands.Menus;
import CommandManager.SlashCommands.Ping;
import java.util.HashMap;
import java.util.Map;

public class SlashCommandManager
{
    private final Map<String, ISlashCommand> commands;
    
    SlashCommandManager() {
        commands = new HashMap<String, ISlashCommand>();
        addCommand(new Ping());
        addCommand(new Menus());
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
        addCommand(new StarCheck());
        addCommand(new UpdateCommands());
        addCommand(new Update());
        addCommand(new UNCC());
    }
    
    private void addCommand(final ISlashCommand c) {
        this.commands.putIfAbsent(c.commandName(), c);
        if (!c.buttons().isEmpty()) {
            for (final String k : c.buttons()) {
                this.commands.putIfAbsent(k, c);
            }
        }
    }
    
    void run(final SlashCommandInteractionEvent event) throws Exception {
        final String name = event.getName();
        if (event.getUser().isBot()) {
            return;
        }
        if (this.commands.containsKey(name)) {
            if (this.commands.get(name).enabled()) {
                this.commands.get(name).run(event);
            }
            else {
                event.reply("This command is disabled!").setEphemeral(true).queue();
            }
        }
    }
    
    void run(final ButtonInteractionEvent event) throws Exception {
        final String name = event.getComponentId();
        if (event.getUser().isBot()) {
            return;
        }
        if (this.commands.containsKey(name) && this.commands.get(name).enabled()) {
            this.commands.get(name).run(event);
        }
    }
    
    void run(final SelectMenuInteractionEvent event) throws Exception {
        final String name = event.getComponentId();
        if (event.getUser().isBot()) {
            return;
        }
        if (this.commands.containsKey(name) && this.commands.get(name).enabled()) {
            this.commands.get(name).run(event);
        }
    }
}
