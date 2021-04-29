package Command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface ICommand {
    void run(List<String> args, GuildMessageReceivedEvent event) throws Exception;
    String getCommandName();
    List<String> getCommandAlias();
    String getHelp();
}
