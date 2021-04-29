package Command;

import Main.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    public final CommandManager commandManager = new CommandManager();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String prefix = "$";
        DataBase db = new DataBase();
        try {prefix = db.getPrefix("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}

        try {
            commandManager.run(event);
        } catch (Exception e) { e.printStackTrace(); }

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
