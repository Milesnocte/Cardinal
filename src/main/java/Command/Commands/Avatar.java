package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Avatar implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        try{
            String userid = args.get(0).replace("<","").replace("@","").replace(">","").replace("!","");
            event.getChannel().sendMessage(
                    Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getMemberById(userid)).getUser().getAvatarUrl())
            ).queue();
        }catch (Exception e){
            e.printStackTrace();
            event.getChannel().sendMessage("Invalid userid argument, please copy the id for a valid user!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "avatar";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.singletonList("av");
    }

    @Override
    public String getHelp() {
        return null;
    }
}
