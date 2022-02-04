package Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class WatchList extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getGuild().getId().equals("433825343485247499")) {
            Role role;
            TextChannel channel;
            try {
                role = event.getGuild().getRolesByName("Last Chance", true).get(0);
                channel = event.getGuild().getTextChannelsByName("mod-watchlist", true).get(0);
            } catch (Exception e) {
                return;
            }

            if (event.getMember().getRoles().contains(role)) {
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

                channel.sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
