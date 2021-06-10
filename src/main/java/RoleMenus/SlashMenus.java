package RoleMenus;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlashMenus {

    private static final long INCOMING = 618643756580601857L;
    private static final long FRESHMAN = 581614531789455363L;
    private static final long SOPHOMORE = 618643636665712640L;
    private static final long JUNIOR = 618643685432623135L;
    private static final long SENIOR = 618643717384831017L;
    private static final long GRADUATE = 618643752327708682L;
    private static final long ALUMNI = 449689803219271681L;
    private static final long AI_GAMING = 822305355828559893L;
    private static final long DATA_SCIENCE = 822305557426864170L;
    private static final long SOFTWARE_SYSTEMS = 822305633327120404L;
    private static final long CYBER_SECURITY = 822305682882691094L;
    private static final long HCI = 822305742476148786L;
    private static final long INFO_TECH = 822305775967141930L;
    private static final long SOFTWARE_ENGINEER = 822305835086512178L;
    private static final long WEB_MOBILE = 822305884712992818L;
    private static final long BIO_INFORMATICS = 822305921672937472L;
    private static final long UNDECLARED = 852049528458444831L;

    private static final List<Long> yearRoles = Arrays.asList(INCOMING, FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, GRADUATE, ALUMNI);
    private static final List<Long> concentrationRoles = Arrays.asList(AI_GAMING, DATA_SCIENCE, SOFTWARE_SYSTEMS,
            CYBER_SECURITY, HCI, INFO_TECH, SOFTWARE_ENGINEER, WEB_MOBILE, BIO_INFORMATICS, UNDECLARED);

    public static void SlashYearMenu(SlashCommandEvent event) {
        event.reply("\n**__What year are you?__**\n").addActionRow(
                Button.primary("Year_Incoming", "Incoming"),
                Button.primary("Year_Freshman", "Freshman"),
                Button.primary("Year_Sophomore", "Sophomore")

        ).addActionRow(
                Button.primary("Year_Junior", "Junior"),
                Button.primary("Year_Senior", "Senior"),
                Button.primary("Year_Grad", "Grad"),
                Button.primary("Year_Alumni", "Alumni")
        ).queue();
    }

    public static void SlashConcetrationMenu(SlashCommandEvent event){
        event.reply("\n**__What is your concentration?__**\n").addActionRow(
                Button.primary("Conc_SE", "Software Engineering"),
                Button.primary("Bioinformatics", "Bioinformatics")
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

    public static void RemoveYearRole(ButtonClickEvent event){
        // Get the roles the user currently has
        List<Long> roleIDs = new ArrayList<>();
        for(int k = 0; k < event.getMember().getRoles().size(); k++){
            roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
        }

        // Removes any class role the user has, we will give them one again in the switch
        for (Long yearRole : yearRoles) {
            if (roleIDs.contains(yearRole)) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(yearRole)).queue();
            }
        }
    }

    public static void RemoveConcRole(ButtonClickEvent event){
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
