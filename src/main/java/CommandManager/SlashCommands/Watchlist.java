package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.Collections;
import java.util.List;

public class Watchlist implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        if(event.getSubcommandName().equals("add")){
            event.reply("Watchlist add").queue();
        }
        if(event.getSubcommandName().equals("remove")){
            event.reply("Watchlist remove").queue();
        }
    }

    @Override
    public void run(ButtonInteractionEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "watchlist";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }
}
