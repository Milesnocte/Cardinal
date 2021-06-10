package Command.Commands;

import Command.ICommand;
import Main.DataBase;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Me implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        DataBase db = new DataBase();
        String prefix = db.getPrefix(String.valueOf(event.getGuild().getIdLong()));
        if(event.getAuthor().getId().equals("225772174336720896") || event.getAuthor().getId().equals("573339588442193930")){
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

    @Override
    public String getHelp() {
        return "secret";
    }
}
