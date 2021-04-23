package Main;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class YearMenu extends ListenerAdapter {
    private static final long INCOMING = 822305355828559893L;
    private static final long FRESHMAN = 822305557426864170L;
    private static final long SOPHOMORE = 822305633327120404L;
    private static final long JUNIOR = 822305682882691094L;
    private static final long SENIOR = 822305742476148786L;
    private static final long GRADUATE = 822305775967141930L;
    private static final long ALUMNI = 822305835086512178L;

    private static final List<Long> yearRoles = Arrays.asList(INCOMING, FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, GRADUATE, ALUMNI);
    private static String messageId;

    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        RestAction<Message> ra = event.getChannel().sendMessage("What is your current class standing?\n\n\uD83C\uDF92: Incoming Student\n\n\uD83D\uDC76: Freshman " +
        "\n\n\uD83D\uDC66: Sophomore\n\n\uD83D\uDC68: Junior\n\n\uD83D\uDC74: Senior\n\n\uD83C\uDF93: Graduate Student\n\n\uD83D\uDC68\u200D\uD83D\uDCBB: Alumni\n\n React below to receive your class standing role:");
        // Wait for the message to actually send
        Message message = ra.complete();
        // Adds all of the class role reactions to the massage after the message has been sent
        message.addReaction("\uD83C\uDF92").queue();
        message.addReaction("\uD83D\uDC76").queue();
        message.addReaction("\uD83D\uDC66").queue();
        message.addReaction("\uD83D\uDC68").queue();
        message.addReaction("\uD83D\uDC74").queue();
        message.addReaction("\uD83C\uDF93").queue();
        message.addReaction("\uD83D\uDC68\u200D\uD83D\uDCBB").queue();
        // Put the messages id into the database so we can use it in the future
        messageId = message.getId();
        DataBase db = new DataBase();
        db.updateGuildRoleID(String.valueOf(event.getGuild().getIdLong()), messageId);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        try {
            //Get the id of the reaction menu message
            messageId = DataBase.getReactRoleid(String.valueOf(event.getGuild().getIdLong()));
        } catch (Exception ignored) {}
        if(event.getMessageId().equals(messageId) && !event.getUser().isBot()){

            //Removes any class role the user has, we will give them one again in the switch
            for (int k = 0; k < yearRoles.size(); k++) {
                if (event.getMember().getRoles().get(k).getIdLong() == yearRoles.get(k)) {
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getMember().getRoles().get(k)).queue();
                    int finalK = k;
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `" + event.getMember().getRoles().get(finalK).getName() + "`!").queue());
                }
            }

            // using a switch here was far more efficient than if statements. Testing for each reaction to give the
            // user their role, this will also direct message the user of the role they are given
            switch(event.getReaction().getReactionEmote().toString()){
                case("RE:U+1f392"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(INCOMING)).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Incoming Student` role!").queue(null, null));
                    break;
                case("RE:U+1f476"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(FRESHMAN)).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Freshman` role!").queue(null, null));
                    break;
                case("RE:U+1f466"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOPHOMORE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Sophomore` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f468"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(JUNIOR)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Junior` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f474"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SENIOR)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Senior` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f393"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(GRADUATE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Graduate Student` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f468U+200dU+1f4bb"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ALUMNI)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Alumni` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
            }
            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }
}

