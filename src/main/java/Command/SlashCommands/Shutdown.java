package Command.SlashCommands;

import Command.ISlashCommand;
import Main.Credentials;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collections;
import java.util.List;

public class Shutdown implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if (event.getMember().getId().equals(Credentials.OWNER)) {
            event.reply("Shutting down...").queue();
            Thread.sleep(1000);
            System.exit(0);
        }else {
            event.reply("Why would you even try? <a:fightmehoe:892929182772895744>").queue();
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
        return "shutdown";
    }
}
