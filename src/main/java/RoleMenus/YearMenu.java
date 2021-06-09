package RoleMenus;

import Main.DataBase;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YearMenu extends ListenerAdapter {
    private static final long INCOMING = 618643756580601857L;
    private static final long FRESHMAN = 581614531789455363L;
    private static final long SOPHOMORE = 618643636665712640L;
    private static final long JUNIOR = 618643685432623135L;
    private static final long SENIOR = 618643717384831017L;
    private static final long GRADUATE = 618643752327708682L;
    private static final long ALUMNI = 449689803219271681L;

    private static final List<Long> yearRoles = Arrays.asList(INCOMING, FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, GRADUATE, ALUMNI);
    private static String messageId;

    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        RestAction<Message> ra = event.getChannel().sendMessage("***__What is your current class standing?__***\n\n\uD83C\uDF92: Incoming Student\n\n\uD83D\uDC76: Freshman " +
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

            List<Long> roleIDs = new ArrayList<>();

            for(int k = 0; k < event.getMember().getRoles().size(); k++){
                roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
            }

            //Removes any class role the user has, we will give them one again in the switch
            for (int k = 0; k < yearRoles.size(); k++) {
                if (roleIDs.contains(yearRoles.get(k))) {
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(yearRoles.get(k))).queue();
                    int finalK = k;
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `" + event.getGuild().getRoleById(yearRoles.get(finalK)).getName() + "`!").queue());
                }
            }

            // using a switch here was far more efficient than if statements. Testing for each reaction to give the
            // user their role, this will also direct message the user of the role they are given
            switch (event.getReaction().getReactionEmote().toString()) {
                case ("RE:U+1f392"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(INCOMING)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Incoming Student` role!").queue(null, null));
                }
                case ("RE:U+1f476"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(FRESHMAN)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Freshman` role!").queue(null, null));
                }
                case ("RE:U+1f466"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOPHOMORE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Sophomore` role!").queue(null, null));
                }
                case ("RE:U+1f468"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(JUNIOR)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Junior` role!").queue(null, null));
                }
                case ("RE:U+1f474"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SENIOR)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Senior` role!").queue(null, null));
                }
                case ("RE:U+1f393"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(GRADUATE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Graduate Student` role!").queue(null, null));
                }
                case ("RE:U+1f468U+200dU+1f4bb"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ALUMNI)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Alumni` role!").queue(null, null));
                }
            }
            event.getReaction().removeReaction(event.getUser()).queue();

            roleIDs.clear();
        }
    }
}

