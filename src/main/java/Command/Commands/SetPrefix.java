package Command.Commands;

import Command.ICommand;
import Main.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class SetPrefix implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)){
            try {
                DataBase db = new DataBase();
                db.updateGuildPrefix(String.valueOf(event.getGuild().getIdLong()), args.get(0));
                event.getChannel().sendMessage("My prefix is now `" + db.getPrefix(String.valueOf(event.getGuild().getIdLong())) + "`").queue();
            }catch (Exception e){
                event.getChannel().sendMessage("Invalid command input, you must specify a prefix!").queue();
            }
        }else {
            event.getChannel().sendMessage("You do not have permission to run this command!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "setprefix";
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
