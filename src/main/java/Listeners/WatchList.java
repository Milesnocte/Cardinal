package Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WatchList extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Role role;
        TextChannel channel;
        try {
            role = event.getGuild().getRolesByName("Last Chance", true).get(0);
            channel = event.getGuild().getTextChannelsByName("mod-watchlist", true).get(0);
        } catch (Exception e) {
            return;
        }

        if(event.getMember().getRoles().contains(role)){
            Message message = event.getMessage();
            EmbedBuilder embed = new EmbedBuilder();

            embed.setAuthor(event.getMessage().getAuthor().getAsTag(), message.getJumpUrl(), message.getAuthor().getAvatarUrl());
            if (message.getReferencedMessage() != null) {
                embed.addField("**Reply to " + message.getReferencedMessage().getAuthor().getAsTag() + "**",
                        message.getReferencedMessage().getContentDisplay(), false);
            }
            if (!message.getContentDisplay().isBlank()) {
                embed.addField("**Message**", message.getContentDisplay(), false);
            }
            embed.setFooter("Sent on: " + message.getTimeCreated().atZoneSameInstant(ZoneId.of("America/New_York"))
                    .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")));

            channel.sendMessageEmbeds(embed.build()).setActionRow(Button.link(message.getJumpUrl(),"Jump to message")).queue();
        }
    }
}
