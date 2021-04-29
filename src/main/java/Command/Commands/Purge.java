package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

public class Purge implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);
            if ((Integer.parseInt(args.get(0)) <= 100 && Integer.parseInt(args.get(0)) > 0) && (args.size() == 1)) {
                List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args.get(0))).complete();

                messages.removeIf(m -> m.getTimeCreated().isBefore(twoWeeksAgo));

                event.getChannel().deleteMessages(messages).complete();
                event.getChannel().sendMessage("Deleted `" + Integer.parseInt(args.get(0)) + "` messages in " + event.getChannel().getAsMention()).queue();
                event.getGuild().getTextChannelById("582399042240118814").sendMessage("Deleted `" + Integer.parseInt(args.get(0))
                        + "` messages in " + event.getChannel().getAsMention() + " by " + event.getAuthor().getAsMention()).queue();
            }
        } else {
            event.getChannel().sendMessage("You do not have permission to run this command!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "purge";
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
