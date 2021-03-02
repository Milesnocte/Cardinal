package Listeners;

import Main.DataBase;
import Managers.CommandManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String prefix = "$";
        DataBase db = new DataBase();
        try {prefix = db.getPrefix("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].startsWith(prefix) && !event.getAuthor().isBot()){
            args[0] = args[0].replace(prefix,"");
            try {
                new CommandManager(event, args);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(event.getMessage().getContentRaw().replace("!","").equals(event.getJDA().getSelfUser().getAsMention())){
            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_CHANNEL)){
                event.getChannel().sendMessage("I am missing the `MANAGE_CHANNEL` permission! This permission is critical for the bot to function.").queue();
            }
            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_ROLES)){
                event.getChannel().sendMessage("I am missing the `MANAGE_ROLES` permission! Please add this permission and run the $addrole command!").queue();
            }
            event.getChannel().sendMessage("My prefix is `" + prefix + "`").queue();
        }
    }
}
