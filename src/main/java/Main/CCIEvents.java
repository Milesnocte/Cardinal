package Main;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

public class CCIEvents extends ListenerAdapter {
    private static Role CCIEvents;
    private static String id;
    private static DataBase db;
    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        CCIEvents = event.getGuild().getRolesByName("CCI Events", true).get(0);
        RestAction<Message> ra = event.getChannel().sendMessage("Would you like to receive pings for CCI Events?");
        Message message = ra.complete();
        message.addReaction("✅").queue();
        id = message.getId();
        db = new DataBase();
        db.updateGuildeventsId("" + event.getGuild().getIdLong(), id);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        CCIEvents = event.getGuild().getRolesByName("CCI Events", true).get(0);
        try {
            id = db.getReacteventsId("" + event.getGuild().getIdLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.getMessageId().equals(id) && !event.getUser().isBot()){
            event.getGuild().addRoleToMember(event.getMember(), CCIEvents).queue();
            event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added `CCI Events` role!").queue(null, null));
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        CCIEvents = event.getGuild().getRolesByName("CCI Events", true).get(0);
        try {
            id = db.getReacteventsId("" + event.getGuild().getIdLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.getMessageId().equals(id) && !event.getUser().isBot()){
            event.getGuild().removeRoleFromMember(event.getMember(), CCIEvents).queue();
            event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `CCI Events` role!").queue(null, null));
        }
    }
}
