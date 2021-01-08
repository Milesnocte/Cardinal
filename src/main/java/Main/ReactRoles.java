package Main;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ReactRoles extends ListenerAdapter {
    private static int thisMessageID;
    private static long thisChannel;
    private static String id;
    public static void createMenu(String messageID, long channel, GuildMessageReceivedEvent event) {
        thisMessageID = Integer.parseInt(messageID);
        thisChannel = channel;
        RestAction<Message> ra = event.getChannel().sendMessage("React the emote you want to add to this message!");
        Message messageEmbed = ra.complete();
        id = messageEmbed.getId();
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(event.getMessageId().equals(id)){
            event.getChannel().sendMessage(event.getReactionEmote() + " received!").queue();
            event.getGuild().getTextChannelById(String.valueOf(thisChannel)).addReactionById(thisMessageID, event.getReactionEmote().getEmote()).queue();
        }
    }
}
