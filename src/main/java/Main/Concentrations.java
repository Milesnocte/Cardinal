package Main;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Concentrations extends ListenerAdapter {
    private static Role aiGaming;
    private static Role dataSci;
    private static Role softwareSystems;
    private static Role cyberSec;
    private static Role hci;
    private static Role infoTech;
    private static Role softwareEng;
    private static Role webMobile;
    private static Role bioInf;
    private static List<Role> concRoles;
    private static String id;
    public static void createMenu(GuildMessageReceivedEvent event) throws Exception {
        // Get all the class roles from the server
        aiGaming = event.getGuild().getRolesByName("ai-gaming", true).get(0);
        dataSci = event.getGuild().getRolesByName("data-sci", true).get(0);
        softwareSystems = event.getGuild().getRolesByName("software-systems", true).get(0);
        cyberSec = event.getGuild().getRolesByName("cyber-sec", true).get(0);
        hci = event.getGuild().getRolesByName("hci", true).get(0);
        infoTech = event.getGuild().getRolesByName("info-tech", true).get(0);
        softwareEng = event.getGuild().getRolesByName("software-eng", true).get(0);
        webMobile = event.getGuild().getRolesByName("web-mobile", true).get(0);
        bioInf = event.getGuild().getRolesByName("bio-inf", true).get(0);
        concRoles = Arrays.asList(aiGaming,dataSci,softwareSystems,cyberSec,hci,infoTech,softwareEng,webMobile,bioInf);
        RestAction<Message> ra = event.getChannel().sendMessage("What is your declared/intended concentration?\n\n\uD83E\uDD16: AI, Robotics, and Gaming \n\n\uD83D\uDCC3: Data Science " +
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
        id = message.getId();
        DataBase db = new DataBase();
        db.updateConcentrationID("" + event.getGuild().getIdLong(), id);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        // Get all the class roles from the server
        aiGaming = event.getGuild().getRolesByName("ai-gaming", true).get(0);
        dataSci = event.getGuild().getRolesByName("data-sci", true).get(0);
        softwareSystems = event.getGuild().getRolesByName("software-systems", true).get(0);
        cyberSec = event.getGuild().getRolesByName("cyber-sec", true).get(0);
        hci = event.getGuild().getRolesByName("hci", true).get(0);
        infoTech = event.getGuild().getRolesByName("info-tech", true).get(0);
        softwareEng = event.getGuild().getRolesByName("software-eng", true).get(0);
        webMobile = event.getGuild().getRolesByName("web-mobile", true).get(0);
        bioInf = event.getGuild().getRolesByName("bio-inf", true).get(0);
        concRoles = Arrays.asList(aiGaming,dataSci,softwareSystems,cyberSec,hci,infoTech,softwareEng,webMobile,bioInf);
        try {
            //Get the id of the reaction menu message
            id = DataBase.getConcentrationID("" + event.getGuild().getIdLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.getMessageId().equals(id) && !event.getUser().isBot()){

            List<Role> userRoles = event.getMember().getRoles();

            //Removes any class role the user has, we will give them one again in the switch
            for (int k = 0; k < concRoles.size(); k++) {
                if (userRoles.contains(concRoles.get(k))) {
                    event.getGuild().removeRoleFromMember(event.getMember(), concRoles.get(k)).queue();
                    int finalK = k;
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Removed `" + concRoles.get(finalK).getName() + "`!").queue(null, null));
                }
            }

            // using a switch here was far more efficient than if statements. Testing for each reaction to give the
            // user their role, this will also direct message the user of the role they are given
            switch(event.getReaction().getReactionEmote().toString()){
                case("RE:U+1f916"):
                    event.getGuild().addRoleToMember(event.getMember(), aiGaming).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `AI Gaming` role!").queue(null, null));
                    break;
                case("RE:U+1f4c3"):
                    event.getGuild().addRoleToMember(event.getMember(), dataSci).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Data Science` role!").queue(null, null));
                    break;
                case("RE:U+1f9d1U+200dU+1f4bb"):
                    event.getGuild().addRoleToMember(event.getMember(), softwareSystems).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Software Systems` role!").queue(null, null));
                    break;
                case("RE:U+1f510"):
                    event.getGuild().addRoleToMember(event.getMember(), cyberSec).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Cyber Sec` role!").queue(null, null));
                    break;
                case("RE:U+1f9d1U+200dU+1f4bc"):
                    event.getGuild().addRoleToMember(event.getMember(), hci).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `HCI` role!").queue(null, null));
                    break;
                case("RE:U+1f5a5"):
                    event.getGuild().addRoleToMember(event.getMember(), infoTech).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Info Tech` role!").queue(null, null));
                    break;
                case("RE:U+1f9d1U+200dU+1f527"):
                    event.getGuild().addRoleToMember(event.getMember(), softwareEng).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Software Engineering` role!").queue(null, null));
                    break;
                case("RE:U+1f4f1"):
                    event.getGuild().addRoleToMember(event.getMember(), webMobile).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `Web Mobile` role!").queue(null, null));
                    break;
                case("RE:U+2623"):
                    event.getGuild().addRoleToMember(event.getMember(), bioInf).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Added the `BIOINF` role!").queue(null, null));
                    break;
            }
            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }
}