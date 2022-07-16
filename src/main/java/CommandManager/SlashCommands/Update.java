package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import Main.Credentials;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class Update implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        if(event.getMember().getId().equals(Credentials.OWNER)) {
            event.reply("Nothing to update..").setEphemeral(true).queue();
        } else {
            event.reply("This is an owner only command..").setEphemeral(true).queue();
        }
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
        return "update";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return "update";
    }
}