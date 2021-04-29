package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class Ping implements ICommand {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong! " + event.getJDA().getGatewayPing() + "ms").queue(msg -> {
            msg.editMessage("Pong! Took " + event.getJDA().getGatewayPing() + "ms").queue();
        });
    }

    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return "Gives the Gateway ping of the bot";
    }
}
