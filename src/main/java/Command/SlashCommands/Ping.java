package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ping implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        event.reply("Pong! " + event.getJDA().getGatewayPing() + "ms").queue();
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
        return "ping";
    }
}
