package Command.Commands;

import Command.ICommand;
import Main.Credentials;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class LeaveVC implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(Credentials.OWNER)) {
            event.getMessage().delete().queue();
            event.getGuild().leave().queue();
        }
    }

    @Override
    public String getCommandName() {
        return "leave";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return null;
    }
}
