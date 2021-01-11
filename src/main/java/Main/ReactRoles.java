package Main;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class ReactRoles extends ListenerAdapter {
    private static Role Incoming;
    private static Role Freshman;
    private static Role Sophomore;
    private static Role Junior;
    private static Role Senior;
    private static Role Graduate;
    private static Role Alumni;
    private static List<Role> classRoles;
    private static String id;
    private static DataBase db;
    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        Incoming = event.getGuild().getRolesByName("Incoming Student", true).get(0);
        Freshman = event.getGuild().getRolesByName("Freshman", true).get(0);
        Sophomore = event.getGuild().getRolesByName("Sophomore", true).get(0);
        Junior = event.getGuild().getRolesByName("Junior", true).get(0);
        Senior = event.getGuild().getRolesByName("Senior", true).get(0);
        Graduate = event.getGuild().getRolesByName("Graduate Student", true).get(0);
        Alumni = event.getGuild().getRolesByName("Alumni", true).get(0);
        classRoles = Arrays.asList(Incoming,Freshman,Sophomore,Junior,Senior,Graduate,Alumni);
        RestAction<Message> ra = event.getChannel().sendMessage("What is your current class standing?\n\n\uD83C\uDF92: Incoming Student\n\n\uD83D\uDC76: Freshman " +
        "\n\n\uD83D\uDC66: Sophomore\n\n\uD83D\uDC68: Junior\n\n\uD83D\uDC74: Senior\n\n\uD83C\uDF93: Graduate Student\n\n\uD83D\uDC68\u200D\uD83D\uDCBB: Alumni\n\n React below to receive your class standing role:");
        Message message = ra.complete();
        message.addReaction("\uD83C\uDF92").queue();
        message.addReaction("\uD83D\uDC76").queue();
        message.addReaction("\uD83D\uDC66").queue();
        message.addReaction("\uD83D\uDC68").queue();
        message.addReaction("\uD83D\uDC74").queue();
        message.addReaction("\uD83C\uDF93").queue();
        message.addReaction("\uD83D\uDC68\u200D\uD83D\uDCBB").queue();
        id = message.getId();
        db = new DataBase();
        db.updateGuildRoleID("" + event.getGuild().getIdLong(), id);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        Incoming = event.getGuild().getRolesByName("Incoming Student", true).get(0);
        Freshman = event.getGuild().getRolesByName("Freshman", true).get(0);
        Sophomore = event.getGuild().getRolesByName("Sophomore", true).get(0);
        Junior = event.getGuild().getRolesByName("Junior", true).get(0);
        Senior = event.getGuild().getRolesByName("Senior", true).get(0);
        Graduate = event.getGuild().getRolesByName("Graduate Student", true).get(0);
        Alumni = event.getGuild().getRolesByName("Alumni", true).get(0);
        classRoles = Arrays.asList(Incoming,Freshman,Sophomore,Junior,Senior,Graduate,Alumni);
        try {
            id = db.getReactRoleid("" + event.getGuild().getIdLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.getMessageId().equals(id) && !event.getUser().isBot()){

            List<Role> userRoles = event.getMember().getRoles();

            for(int k = 0; k < classRoles.size(); k++){
                if(userRoles.contains(classRoles.get(k))){
                    event.getGuild().removeRoleFromMember(event.getMember(), classRoles.get(k)).queue();
                    int finalK = k;
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `" + classRoles.get(finalK).getName() + "`!").queue(null, null));
                }
            }

            switch(event.getReaction().getReactionEmote().toString()){
                case("RE:U+1f392"):
                    event.getGuild().addRoleToMember(event.getMember(), Incoming).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Incoming Student` role!").queue(null, null));
                    break;
                case("RE:U+1f476"):
                    event.getGuild().addRoleToMember(event.getMember(), Freshman).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Freshman` role!").queue(null, null));
                    break;
                case("RE:U+1f466"):
                    event.getGuild().addRoleToMember(event.getMember(), Sophomore).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Sophomore` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f468"):
                    event.getGuild().addRoleToMember(event.getMember(), Junior).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Junior` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f474"):
                    event.getGuild().addRoleToMember(event.getMember(), Senior).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Senior` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f393"):
                    event.getGuild().addRoleToMember(event.getMember(), Graduate).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Graduate Student` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
                case("RE:U+1f468U+200dU+1f4bb"):
                    event.getGuild().addRoleToMember(event.getMember(), Alumni).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Alumni` role!").queue(null, null));
                    event.getReaction().removeReaction(event.getUser()).queue();
                    break;
            }
            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }
}

