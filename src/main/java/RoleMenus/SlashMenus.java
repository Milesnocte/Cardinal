package RoleMenus;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashMenus {
    public static void SlashYearMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__What year are you?__**\n")
                .addActionRow(
                        SelectMenu.create("years")
                                .addOptions(
                                        SelectOption.of("Incoming Student", "Year_Incoming"),
                                        SelectOption.of("Freshman", "Year_Freshman"),
                                        SelectOption.of("Sophomore", "Year_Sophomore"),
                                        SelectOption.of("Junior", "Year_Junior"),
                                        SelectOption.of("Senior", "Year_Senior"),
                                        SelectOption.of("Graduate Student", "Year_Grad"),
                                        SelectOption.of("Alumni", "Year_Alumni")
                                ).setRequiredRange(0, 1).setPlaceholder("Select Your Year").build()).queue();
    }

    public static void PronounMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__What are your pronouns?__**\n")
                .addActionRow(
                        SelectMenu.create("pronouns")
                                .addOptions(
                                        SelectOption.of("He/Him", "Pron_He"),
                                        SelectOption.of("She/Her", "Pron_She"),
                                        SelectOption.of("They/Them", "Pron_They"),
                                        SelectOption.of("He/They", "Pron_HeThey"),
                                        SelectOption.of("She/They", "Pron_SheThey"),
                                        SelectOption.of("Ask Me", "Pron_Ask")
                                ).setRequiredRange(0, 6).setPlaceholder("Select pronouns").build()).queue();
    }

    public static void CollegeMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__What is your major?__**\n")
                .addActionRow(SelectMenu.create("college")
                        .addOptions(
                                SelectOption.of("Data Science", "College_Data"),
                                SelectOption.of("Liberal Arts and Sciences", "College_Liberal"),
                                SelectOption.of("Health and Human Services", "College_Health"),
                                SelectOption.of("Engineering", "College_Engineering"),
                                SelectOption.of("Education", "College_Education"),
                                SelectOption.of("Computing and Informatics", "College_Computing"),
                                SelectOption.of("Arts and Architectures", "College_Arts"),
                                SelectOption.of("Business", "College_Business"),
                                SelectOption.of("Undeclared", "College_Undec")
                        ).setRequiredRange(0, 2).setPlaceholder("Select Your Major(s)").build()).queue();
    }

    public static void PlatformsMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__What platforms do you play on?__**")
                .addActionRow(SelectMenu.create("platform")
                        .addOptions(
                                SelectOption.of("PC", "Platform_PC"),
                                SelectOption.of("Xbox", "Platform_XBOX"),
                                SelectOption.of("Mobile", "Platform_Mobile"),
                                SelectOption.of("PlayStation", "Platform_PS"),
                                SelectOption.of("Nintendo Switch", "Platform_Switch")
                        ).setRequiredRange(0, 5).setPlaceholder("Select Your platform(s)").build()).queue();
    }

    public static void LivingMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__Do you live on or off campus?__**")
                .addActionRow(
                        SelectMenu.create("living")
                                .addOptions(
                                        SelectOption.of("On Campus", "Living_On"),
                                        SelectOption.of("Off Campus", "Living_Off"),
                                        SelectOption.of("Commuter", "Living_Commuter")
                                ).setRequiredRange(0, 1).setPlaceholder("Select One").build()).queue();
    }

    public static void removeRoles(List<String> roleNames, SelectMenuInteractionEvent event) {
        List<Role> roles = event.getMember().getRoles();
        for (Role role : roles) {
            for (String roleName : roleNames) {
                if (roleName.equalsIgnoreCase(role.getName())) {
                    event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
                }
            }
        }
    }

    public static void removeRoles(List<String> roleNames, ButtonInteractionEvent event) {
        List<Role> roles = event.getMember().getRoles();
        for (Role role : roles) {
            for (String roleName : roleNames) {
                if (roleName.equalsIgnoreCase(role.getName())) {
                    event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
                }
            }
        }
    }

    public static void PingRoles(SlashCommandInteractionEvent event) {
        event.reply("\n**__Do you live on or off campus?__**")
                .addActionRow(
                        Button.primary("EventPing","Events"),
                        Button.primary("MeetupPing","Meetups")
                ).queue();
    }
}
