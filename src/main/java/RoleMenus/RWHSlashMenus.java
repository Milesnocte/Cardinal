package RoleMenus;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class RWHSlashMenus {

    public static void SlashConcetrationMenu(SlashCommandInteractionEvent event){
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

    public static void SlashCCIEvents(SlashCommandInteractionEvent event){
        event.reply("\n**__Receive pings for events?__**\n").addActionRow(
                Button.success("CCI_YES_CCIEVENTS", "Yes"),
                Button.danger("CCI_NO_CCIEVENTS", "No")
        ).queue();
    }
}
