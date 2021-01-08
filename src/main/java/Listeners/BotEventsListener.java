package Listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.RoleManager;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import Main.*;

import java.awt.*;

public class BotEventsListener extends ListenerAdapter {

    // NUMBER OF GUILDS THE BOT IS IN
    public static int guildCount;

    @Override
    public void onReady(@NotNull ReadyEvent event) {

    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        DataBase db = new DataBase();
        try {db.addGuild("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}

        Boolean roleExists = (event.getGuild().getRolesByName("InVoiceChannel", true).isEmpty());
        if(roleExists) {
            RestAction ra = event.getGuild().createRole().setMentionable(true).setName("InVoiceChannel").setColor(Color.CYAN);
            ra.complete();
            RoleManager roleManager = event.getGuild().getRolesByName("InVoiceChannel", true).get(0).getManager();
            roleManager.setPermissions(Permission.VOICE_CONNECT,Permission.VOICE_SPEAK,Permission.MESSAGE_HISTORY,Permission.MESSAGE_READ,Permission.MESSAGE_WRITE,
                    Permission.VOICE_STREAM,Permission.VOICE_USE_VAD,Permission.MESSAGE_EMBED_LINKS,Permission.MESSAGE_EXT_EMOJI,Permission.MESSAGE_ADD_REACTION,
                    Permission.MESSAGE_ATTACH_FILES,Permission.NICKNAME_CHANGE).queue();
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        DataBase db = new DataBase();
        try {db.dropGuild("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}
    }
}
