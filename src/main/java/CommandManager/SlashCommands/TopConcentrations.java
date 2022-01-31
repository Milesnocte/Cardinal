package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.util.List;
import java.util.*;

public class TopConcentrations implements ISlashCommand {

    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if (event.getGuild().getId().equals("433825343485247499")) {
            final long AI_GAMING = 822305355828559893L;
            final long DATA_SCIENCE = 822305557426864170L;
            final long SOFTWARE_SYSTEMS = 822305633327120404L;
            final long CYBER_SECURITY = 822305682882691094L;
            final long HCI = 822305742476148786L;
            final long INFO_TECH = 822305775967141930L;
            final long SOFTWARE_ENGINEER = 822305835086512178L;
            final long WEB_MOBILE = 822305884712992818L;
            final long BIO_INFORMATICS = 822305921672937472L;
            final List<Long> concentrationRoles = Arrays.asList(AI_GAMING, DATA_SCIENCE, SOFTWARE_SYSTEMS, CYBER_SECURITY, HCI, INFO_TECH, SOFTWARE_ENGINEER, WEB_MOBILE, BIO_INFORMATICS);
            List<String> roleNumberArray = new ArrayList<>();

            // Grab all the role names and number of members
            for (int k = 0; k < 9; k++) {
                roleNumberArray.add(k, event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(concentrationRoles.get(k))).size()
                        + " " + event.getGuild().getRoleById(concentrationRoles.get(k)).getName().toUpperCase());
            }

            // Sort by the number of people in each role
            roleNumberArray.sort(new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return extractInt(o1) - extractInt(o2);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("[\\D]", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });

            // put it all into a single string to make it easier to add to the embed
            StringBuilder rolesList = new StringBuilder();
            for (int k = 8; k >= 0; k--) {
                rolesList.append(roleNumberArray.get(k)).append("\n");
            }
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.cyan);
            embed.setDescription("Grab a role from <#618647310179762188>!");
            embed.addField("Concentration roles leaderboard", rolesList.toString(), false);
            event.replyEmbeds(embed.build()).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "topconcentrations";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }
}
