package Command.Commands.RWH;

import Command.ICommand;
import RoleMenus.CCIEvents;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class CCIEventsRole implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if(event.getGuild().getId().equals("433825343485247499") && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            CCIEvents.createMenu(event);
        }
    }

    @Override
    public String getCommandName() {
        return "ccievents";
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
