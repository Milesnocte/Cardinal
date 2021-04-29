package Command.Commands.RWH;

import Command.ICommand;
import RoleMenus.YearMenu;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class YearRoles implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if(event.getGuild().getId().equals("433825343485247499")  && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            YearMenu.createMenu(event);
        }
    }

    @Override
    public String getCommandName() {
        return "yearroles";
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
