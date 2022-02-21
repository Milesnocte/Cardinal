package RoleMenus;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
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

    public static void PoliMenu(SlashCommandEvent event){
        event.reply("\n**__Would you like access to politics?__**\nMature and potentially triggering subjects may be discussed here.")
                .addActionRow(
                        Button.success("POLI_YES", "Yes"),
                        Button.danger("POLI_NO", "No")
                ).queue();
    }

    public static void PlatformsMenu(SlashCommandEvent event) {
        event.reply("\n**__What platforms do you play on?__**")
                .addActionRow(
                        Button.primary("Platform_PC", "PC"),
                        Button.primary("Platform_XBOX", "XBOX"),
                        Button.primary("Platform_Mobile", "Mobile")
                ).addActionRow(
                        Button.primary("Platform_PS", "Playstation"),
                        Button.primary("Platform_Switch", "Switch")
                ).queue();
    }

    public static void LivingMenu(SlashCommandEvent event) {
        event.reply("\n**__Do you live on or off campus?__**")
                .addActionRow(
                        Button.primary("Living_On", "On Campus"),
                        Button.primary("Living_Off", "Off Campus"),
                        Button.primary("Living_Commuter", "Commuter")
                ).queue();
    }

    public static void removeRoles(List<String> roleNames, ButtonClickEvent event){
        List<Role> roles = event.getMember().getRoles();
        // Removes any class role the user has, we will give them one again in the switch
        for (Role role : roles) {
            if (roleNames.contains(role.getName())) {
                event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
            }
        }
    }
}
