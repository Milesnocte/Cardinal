package RoleMenus;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlashMenus {

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
    public static void PronounMenu(SlashCommandEvent event) {
        event.reply("\n**__What are your pronouns?__**\n").addActionRow(
                Button.primary("Pron_He", "He/Him"),
                Button.primary("Pron_She", "She/Her"),
                Button.primary("Pron_They", "They/Them")

        ).addActionRow(
                Button.primary("Pron_HeThey", "He/They"),
                Button.primary("Pron_SheThey", "She/They"),
                Button.primary("Pron_Ask", "Ask Me")
        ).queue();
    }

    public static void CollegeMenu(SlashCommandEvent event) {
        event.reply("\n**__What is your major?__**\n").addActionRow(
                Button.primary("College_Data", "Data Science"),
                Button.primary("College_Liberal", "Liberal Arts and Sciences")
        ).addActionRow(
                Button.primary("College_Health", "Health and Human Services"),
                Button.primary("College_Engineering", "Engineering")
        ).addActionRow(
                Button.primary("College_Education", "Education"),
                Button.primary("College_Computing", "Computing and Informatics")
        ).addActionRow(
                Button.primary("College_Arts", "Arts and Architectures"),
                Button.primary("College_Business", "Business")
        ).addActionRow(
                Button.primary("College_Undec", "Undeclared")
        ).queue();
    }

    public static void RemoveYearRole(ButtonClickEvent event){

        final long INCOMING = event.getGuild().getRolesByName("Incoming student",true).get(0).getIdLong();
        final long FRESHMAN = event.getGuild().getRolesByName("Freshman",true).get(0).getIdLong();
        final long SOPHOMORE = event.getGuild().getRolesByName("Sophomore",true).get(0).getIdLong();
        final long JUNIOR = event.getGuild().getRolesByName("Junior",true).get(0).getIdLong();
        final long SENIOR = event.getGuild().getRolesByName("Senior",true).get(0).getIdLong();
        final long GRADUATE = event.getGuild().getRolesByName("Graduate Student",true).get(0).getIdLong();
        final long ALUMNI = event.getGuild().getRolesByName("Alumni",true).get(0).getIdLong();

        final List<Long> yearRoles = Arrays.asList(INCOMING, FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, GRADUATE, ALUMNI);

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

    public static void RemovePronRole(ButtonClickEvent event){

        final long HE = event.getGuild().getRolesByName("He/Him",true).get(0).getIdLong();
        final long SHE = event.getGuild().getRolesByName("She/Her",true).get(0).getIdLong();
        final long THEY = event.getGuild().getRolesByName("They/Them",true).get(0).getIdLong();
        final long HETHEY = event.getGuild().getRolesByName("He/They",true).get(0).getIdLong();
        final long SHETHEY = event.getGuild().getRolesByName("She/They",true).get(0).getIdLong();
        final long ASK = event.getGuild().getRolesByName("Ask pronouns",true).get(0).getIdLong();

        final List<Long> pronRoles = Arrays.asList(HE, SHE, THEY, HETHEY, SHETHEY, ASK);

        // Get the roles the user currently has
        List<Long> roleIDs = new ArrayList<>();
        for(int k = 0; k < event.getMember().getRoles().size(); k++){
            roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
        }

        // Removes any class role the user has, we will give them one again in the switch
        for (Long pronRole : pronRoles) {
            if (roleIDs.contains(pronRole)) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(pronRole)).queue();
            }
        }
    }

    public static void RemoveCollegeRole(ButtonClickEvent event){

        final long DATA = event.getGuild().getRolesByName("Data Science",true).get(0).getIdLong();
        final long LIBERAL = event.getGuild().getRolesByName("Liberal Arts & Sciences",true).get(0).getIdLong();
        final long HEALTH = event.getGuild().getRolesByName("Health & Human Services",true).get(0).getIdLong();
        final long ENGINEER = event.getGuild().getRolesByName("Engineering",true).get(0).getIdLong();
        final long EDU = event.getGuild().getRolesByName("Education",true).get(0).getIdLong();
        final long COMPUTER = event.getGuild().getRolesByName("Computing & Informatics",true).get(0).getIdLong();
        final long ARTS = event.getGuild().getRolesByName("Arts + Architecture",true).get(0).getIdLong();
        final long BUSINESS = event.getGuild().getRolesByName("Business",true).get(0).getIdLong();
        final long UNDEC = event.getGuild().getRolesByName("Undeclared",true).get(0).getIdLong();

        final List<Long> pronRoles = Arrays.asList(DATA, LIBERAL, HEALTH, ENGINEER, EDU, COMPUTER, ARTS, BUSINESS, UNDEC);

        // Get the roles the user currently has
        List<Long> roleIDs = new ArrayList<>();
        for(int k = 0; k < event.getMember().getRoles().size(); k++){
            roleIDs.add(event.getMember().getRoles().get(k).getIdLong());
        }

        // Removes any class role the user has, we will give them one again in the switch
        for (Long pronRole : pronRoles) {
            if (roleIDs.contains(pronRole)) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(pronRole)).queue();
            }
        }
    }
}
