package CommandManager.SlashCommands;

import java.util.Collections;
import java.util.List;

import CommandManager.SlashCommandData;
import Main.Credentials;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import CommandManager.ISlashCommand;

public class UpdateCommands implements ISlashCommand
{
    @Override
    public void run(final SelectMenuInteractionEvent event) throws Exception {
    }
    
    @Override
    public void run(final SlashCommandInteractionEvent event) throws Exception {
        if (event.getMember().getId().equals(Credentials.OWNER)) {
            event.getJDA().updateCommands().queue();
            event.getJDA().updateCommands().addCommands(
                    SlashCommandData.commands
            ).queue();

            event.reply("Updating Commands...").queue();
        }
        else {
            event.reply("This is an owner only command!").queue();
        }
    }
    
    @Override
    public void run(final ButtonInteractionEvent event) throws Exception {
    }
    
    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }
    
    @Override
    public String commandName() {
        return "updatecommands";
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
