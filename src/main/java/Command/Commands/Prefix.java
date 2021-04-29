package Command.Commands;

import Command.ICommand;
import Main.DataBase;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class Prefix implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        try {
            DataBase db = new DataBase();
            event.getChannel().sendMessage("`" + db.getPrefix(String.valueOf(event.getGuild().getIdLong())) + "` is my prefix!").queue();
        } catch (Exception e) { e.printStackTrace();}
    }

    @Override
    public String getCommandName() {
        return "prefix";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return "Returns the prefix for the bot!";
    }
}
