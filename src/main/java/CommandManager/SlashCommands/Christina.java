package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class Christina implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        event.deferReply().queue();
        event.getHook().editOriginal(new File("./img/femnocte.png")).queue();
    }

    @Override
    public void run(ButtonInteractionEvent event) throws Exception {

    }

    @Override
    public void run(SelectMenuInteractionEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "christina";
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
