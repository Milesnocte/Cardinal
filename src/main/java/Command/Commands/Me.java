package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Me implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        String prefix = "$";
        if(event.getAuthor().getId().equals("225772174336720896")){
            if(event.getMessage().getReferencedMessage() != null){
                event.getChannel().sendMessage(event.getMessage().getContentRaw()
                        .replaceFirst("(?i)" + Pattern.quote(prefix + "me "), ""))
                        .reference(event.getMessage().getReferencedMessage()).queue();
                event.getMessage().delete().queue();
            }else{
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getMessage().getContentRaw()
                        .replaceFirst("(?i)" + Pattern.quote(prefix + "me "), "")).queue();
            }
        }
    }

    @Override
    public String getCommandName() {
        return "me";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }
}
