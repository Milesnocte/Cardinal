package Main;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CCIEvents extends ListenerAdapter {
    private static final long CCI_EVENT_ROLE = 618652464643571712L;
    private static String MessageId;
    private static final DataBase database = new DataBase();

    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        RestAction<Message> ra = event.getChannel().sendMessage("Would you like to receive pings for CCI Events?");
        Message message = ra.complete();
        message.addReaction("✅").queue();
        MessageId = message.getId();
        database.updateGuildeventsId("" + event.getGuild().getIdLong(), MessageId);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        try {
            MessageId = DataBase.getReacteventsId(String.valueOf(event.getGuild().getIdLong()));
        } catch (Exception ignored){}

        if(event.getMessageId().equals(MessageId) && !event.getUser().isBot()){
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(CCI_EVENT_ROLE)).queue();
            event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added `CCI Events` role!").queue(null, null));
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {

        try {
            MessageId = DataBase.getReacteventsId(String.valueOf(event.getGuild().getIdLong()));
        } catch (Exception ignored){}

        if(event.getMessageId().equals(MessageId) && !event.getUser().isBot()){
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(CCI_EVENT_ROLE)).queue();
            event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `CCI Events` role!").queue(null, null));
        }
    }
}
