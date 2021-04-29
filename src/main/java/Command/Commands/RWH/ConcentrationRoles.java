package Command.Commands.RWH;

import Command.ICommand;
import RoleMenus.Concentrations;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class ConcentrationRoles implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if (event.getGuild().getId().equals("433825343485247499")  && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            Concentrations.createMenu(event);
        }
    }

    @Override
    public String getCommandName() {
        return "concentrationroles";
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
