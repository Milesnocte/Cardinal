package RoleMenus;

import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RWHSlashMenus
{
    public static void SlashConcetrationMenu(SlashCommandInteractionEvent event) {
        event.reply("\n**__What is your concentration?__**\n")
                .addActionRow(
                        SelectMenu.create("concentration")
                                .addOptions(
                                        SelectOption.of("Software Engineering", "Conc_SE"),
                                        SelectOption.of("Bioinformatics", "Conc_Bioinformatics"),
                                        SelectOption.of("AI, Robotics, and Gaming", "Conc_ARG"),
                                        SelectOption.of("Data Science", "Conc_DataScience"),
                                        SelectOption.of("Information Technology", "Conc_IT"),
                                        SelectOption.of("Web and Mobile", "Conc_WM"),
                                        SelectOption.of("Human-Computer Interaction", "Conc_HCI"),
                                        SelectOption.of("Cybersecurity", "Conc_Cybersecurity"),
                                        SelectOption.of("Software, Systems, and Networks", "Conc_SSN"),
                                        SelectOption.of("Undeclared", "Conc_UD")
                                ).setRequiredRange(0, 1).setPlaceholder("Select Your Concentration").build()).queue();
    }
}
