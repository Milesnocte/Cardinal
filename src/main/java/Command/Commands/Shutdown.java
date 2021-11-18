package Command.Commands;

import Command.ICommand;
import Main.Credentials;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Collections;
import java.util.List;

public class Shutdown implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if (event.getAuthor().getId().equals(Credentials.OWNER)) {
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
            event.getChannel().sendMessage("Shutting down...").queue();
            Thread.sleep(1000);
            System.exit(0);
        }
    }

    @Override
    public String getCommandName() {
        return "shutdown";
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
