package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import Main.Credentials;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class Update implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getMember().getId().equals(Credentials.OWNER)) {
            event.reply("Nothing to update..").setEphemeral(true).queue();
        } else {
            event.reply("This is an owner only command..").setEphemeral(true).queue();
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
