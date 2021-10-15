package RoleMenus;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RWHSlashMenus {

    public static void SlashConcetrationMenu(SlashCommandEvent event){
        event.reply("\n**__What is your concentration?__**\n").addActionRow(
                Button.primary("Conc_SE", "Software Engineering"),
                Button.primary("Conc_Bioinformatics", "Bioinformatics")
        ).addActionRow(
                Button.primary("Conc_ARG", "AI, Robotics, and Gaming"),
                Button.primary("Conc_Data Science", "Data Science")
        ).addActionRow(
                Button.primary("Conc_IT", "Information Technology"),
                Button.primary("Conc_WM", "Web and Mobile")
        ).addActionRow(
                Button.primary("Conc_HCI", "Human-Computer Interaction"),
                Button.primary("Conc_Cybersecurity", "Cybersecurity")
        ).addActionRow(
                Button.primary("Conc_SSN", "Software, Systems, and Networks"),
                Button.primary("Conc_UD", "Undeclared")
        ).queue(); // reply immediately
    }

    public static void SlashCCIEvents(SlashCommandEvent event){
        event.reply("\n**__Receive pings for events?__**\n").addActionRow(
                Button.success("CCI_YES_CCIEVENTS", "Yes"),
                Button.danger("CCI_NO_CCIEVENTS", "No")
        ).queue();
    }

    public static void RemoveConcRole(ButtonClickEvent event){

        Guild guild = event.getGuild();

        long AI_GAMING = guild.getRolesByName("ai-gaming", true).get(0).getIdLong();
        long DATA_SCIENCE = guild.getRolesByName("data-sci", true).get(0).getIdLong();
        long SOFTWARE_SYSTEMS = guild.getRolesByName("software-systems", true).get(0).getIdLong();
        long CYBER_SECURITY = guild.getRolesByName("cyber-sec", true).get(0).getIdLong();
        long HCI = guild.getRolesByName("hci", true).get(0).getIdLong();
        long INFO_TECH = guild.getRolesByName("info-tech", true).get(0).getIdLong();
        long SOFTWARE_ENGINEER = guild.getRolesByName("software-eng", true).get(0).getIdLong();
        long WEB_MOBILE = guild.getRolesByName("web-mobile", true).get(0).getIdLong();
        long BIO_INFORMATICS = guild.getRolesByName("bio-inf", true).get(0).getIdLong();
        long UNDECLARED = guild.getRolesByName("Undeclared", true).get(0).getIdLong();

        List<Long> concentrationRoles = Arrays.asList(AI_GAMING, DATA_SCIENCE, SOFTWARE_SYSTEMS,
                CYBER_SECURITY, HCI, INFO_TECH, SOFTWARE_ENGINEER, WEB_MOBILE, BIO_INFORMATICS, UNDECLARED);

        // Get the roles the user currently has
        List<Long> roleIDs = new ArrayList<>();
        for(int k = 0; k < event.getMember().getRoles().size(); k++){
            roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
        }

        // Removes any concentration role the user has, we will give them one again in the switch
        for (Long concentrationRole : concentrationRoles) {
            if (roleIDs.contains(concentrationRole)) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(concentrationRole)).queue();
            }
        }
    }
}
