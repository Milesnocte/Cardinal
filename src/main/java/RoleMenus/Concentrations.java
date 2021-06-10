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

public class Concentrations extends ListenerAdapter {
    private static final long AI_GAMING = 822305355828559893L;
    private static final long DATA_SCIENCE = 822305557426864170L;
    private static final long SOFTWARE_SYSTEMS = 822305633327120404L;
    private static final long CYBER_SECURITY = 822305682882691094L;
    private static final long HCI = 822305742476148786L;
    private static final long INFO_TECH = 822305775967141930L;
    private static final long SOFTWARE_ENGINEER = 822305835086512178L;
    private static final long WEB_MOBILE = 822305884712992818L;
    private static final long BIO_INFORMATICS = 822305921672937472L;

    private static final List<Long> concentrationRoles = Arrays.asList(AI_GAMING, DATA_SCIENCE, SOFTWARE_SYSTEMS, CYBER_SECURITY, HCI, INFO_TECH, SOFTWARE_ENGINEER, WEB_MOBILE, BIO_INFORMATICS);
    private static String messageId;

    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {

        RestAction<Message> ra = event.getChannel().sendMessage("***__What is your declared/intended concentration?__***\n\n\uD83E\uDD16: AI, Robotics, and Gaming \n\n\uD83D\uDCC3: Data Science " +
                "\n\n\uD83E\uDDD1\u200D\uD83D\uDCBB : Software, Systems, and Networks \n\n\uD83D\uDD10: Cybersecurity \n\n\uD83E\uDDD1\u200D\uD83D\uDCBC: Human-Computer Interaction\n\n\uD83D\uDDA5: Information Technology\n\n\uD83E\uDDD1\u200D\uD83D\uDD27: " +
                "Software Engineering\n\n\uD83D\uDCF1: Web and Mobile\n\n\u2623: Bioinformatics\n\nReact below to receive your concentration role:");
        // Wait for the message to actually send
        Message message = ra.complete();
        // Adds all of the class role reactions to the massge after the message has been sent
        message.addReaction("\uD83E\uDD16").queue();
        message.addReaction("\uD83D\uDCC3").queue();
        message.addReaction("\uD83E\uDDD1\u200D\uD83D\uDCBB").queue();
        message.addReaction("\uD83D\uDD10").queue();
        message.addReaction("\uD83E\uDDD1\u200D\uD83D\uDCBC").queue();
        message.addReaction("\uD83D\uDDA5").queue();
        message.addReaction("\uD83E\uDDD1\u200D\uD83D\uDD27").queue();
        message.addReaction("\uD83D\uDCF1").queue();
        message.addReaction("\u2623").queue();
        // Put the messages id into the database so we can use it in the future
        messageId = message.getId();
        DataBase db = new DataBase();
        db.updateConcentrationID(String.valueOf(event.getGuild().getIdLong()), messageId);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        try {
            //Get the id of the reaction menu message
            messageId = DataBase.getConcentrationID(String.valueOf(event.getGuild().getIdLong()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(event.getMessageId().equals(messageId) && !event.getUser().isBot()){

            List<Long> roleIDs = new ArrayList<>();

            for(int k = 0; k < event.getMember().getRoles().size(); k++){
                roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
            }

            //Removes any class role the user has, we will give them one again in the switch
            for (int k = 0; k < concentrationRoles.size(); k++) {
                if (roleIDs.contains(concentrationRoles.get(k))) {
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(concentrationRoles.get(k))).queue();
                    int finalK = k;
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `" +
                            event.getGuild().getRoleById(concentrationRoles.get(finalK)).getName() + "`!").queue());
                }
            }

            // using a switch here was far more efficient than if statements. Testing for each reaction to give the
            // user their role, this will also direct message the user of the role they are given
            switch (event.getReaction().getReactionEmote().toString()) {
                case ("RE:U+1f916"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(AI_GAMING)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `AI Gaming` role!").queue());
                }
                case ("RE:U+1f4c3"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(DATA_SCIENCE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Data Science` role!").queue());
                }
                case ("RE:U+1f9d1U+200dU+1f4bb"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOFTWARE_SYSTEMS)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Software Systems` role!").queue());
                }
                case ("RE:U+1f510"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(CYBER_SECURITY)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Cyber Sec` role!").queue());
                }
                case ("RE:U+1f9d1U+200dU+1f4bc"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(HCI)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `HCI` role!").queue());
                }
                case ("RE:U+1f5a5"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(INFO_TECH)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Info Tech` role!").queue());
                }
                case ("RE:U+1f9d1U+200dU+1f527"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOFTWARE_ENGINEER)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Software Engineering` role!").queue());
                }
                case ("RE:U+1f4f1"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(WEB_MOBILE)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Web Mobile` role!").queue());
                }
                case ("RE:U+2623"): {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(BIO_INFORMATICS)).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `BIOINF` role!").queue());
                }
            }
            event.getReaction().removeReaction(event.getUser()).queue();

            roleIDs.clear();
        }
    }
}