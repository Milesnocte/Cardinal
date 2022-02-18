package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collections;
import java.util.List;

public class Settings implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getSubcommandGroup().equals("set")){
            event.reply("Settings set").queue();
        }
        if(event.getSubcommandGroup().equals("toggle")){
            event.reply("Settings toggle").queue();
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
        return "settings";
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
