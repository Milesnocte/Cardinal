package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collections;
import java.util.List;

public class Watchlist implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getSubcommandName().equals("add")){
            event.reply("Watchlist add").queue();
        }
        if(event.getSubcommandName().equals("remove")){
            event.reply("Watchlist remove").queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {

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
