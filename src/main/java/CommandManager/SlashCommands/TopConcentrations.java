package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.awt.*;
import java.util.List;
import java.util.*;

public class TopConcentrations implements ISlashCommand {

    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        if (event.getGuild().getId().equals("433825343485247499")) {
            final long AI_GAMING = 991735308083286116L;
            final long DATA_SCIENCE = 991735308632723467L;
            final long SOFTWARE_SYSTEMS = 991735315519766658L;
            final long CYBER_SECURITY = 991735314697683034L;
            final long HCI = 991735314055970857L;
            final long INFO_TECH = 991735312747335840L;
            final long SOFTWARE_ENGINEER = 991735306548166706L;
            final long WEB_MOBILE = 991735313095471167L;
            final long BIO_INFORMATICS = 991735307571572848L;
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
    public void run(ButtonInteractionEvent event) throws Exception {

    }

    @Override
    public void run(SelectMenuInteractionEvent event) throws Exception {

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