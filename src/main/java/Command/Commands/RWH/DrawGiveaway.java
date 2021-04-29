package Command.Commands.RWH;

import Command.ICommand;
import Main.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class DrawGiveaway implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if(event.getGuild().getId().equals("433825343485247499")  && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("Congratulations " + DataBase.getDrawing() + ", you won the giveaway! <@!573339588442193930> will be in contact as soon as possible!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "draw";
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
