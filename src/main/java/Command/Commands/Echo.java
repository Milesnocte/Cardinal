package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Echo implements ICommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws Exception {
        String prefix = "$";
        if(event.getAuthor().getId().equals("225772174336720896")){
            if(event.getMessage().getReferencedMessage() != null){
                event.getChannel().sendMessage(event.getMessage().getContentRaw()
                        .replaceFirst("(?i)" + Pattern.quote(prefix + "echo "), ""))
                        .reference(event.getMessage().getReferencedMessage()).queue();
                event.getMessage().delete().queue();
            }else{
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getMessage().getContentRaw()
                        .replaceFirst("(?i)" + Pattern.quote(prefix + "echo "), "")).queue();
            }
        }
    }

    @Override
    public String getCommandName() {
        return "echo";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }
}
