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
        // Get the cci events role
        CCIEvents = event.getGuild().getRolesByName("CCI Events", true).get(0);
        RestAction<Message> ra = event.getChannel().sendMessage("Would you like to receive pings for CCI Events?");
        // wait until the message has actually sent
        Message message = ra.complete();
        // add the reaction role to the message
        message.addReaction("✅").queue();
        // put the message id into the database
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
