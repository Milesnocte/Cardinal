package Listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CheatManager extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // TODO: Fix this shit
        String messageToLower = event.getMessage().getContentRaw().toLowerCase().replaceAll(" ","");
        if(messageToLower.contains("if(") || messageToLower.contains("elseif(") || messageToLower.contains("for(") || messageToLower.contains("while(")
        || messageToLower.contains("else{")){
            event.getGuild().getTextChannelById("582400125452681216").sendMessage(event.getAuthor().getAsMention() + " sent a potential code snippet in "
                    + event.getChannel().getAsMention() + "! Please contact MilesNocte if this is a false positive!").queue();
        }
    }
}
