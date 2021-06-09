package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.Arrays;
import java.util.List;

public class Concentrations implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            event.reply("\n**__What is your concentration?__**\n").addActionRow(
                    Button.success("SE", "Software Engineering"),
                    Button.success("Bioinformatics", "Bioinformatics")
            ).addActionRow(
                    Button.success("ARG", "AI, Robotics, and Gaming"),
                    Button.success("Data Science", "Data Science")
            ).addActionRow(
                    Button.success("IT", "Information Technology"),
                    Button.success("WM", "Web and Mobile")
            ).addActionRow(
                    Button.success("HCI", "Human-Computer Interaction"),
                    Button.success("Cybersecurity", "Cybersecurity")
            ).addActionRow(
                    Button.success("SSN", "Software, Systems, and Networks"),
                    Button.success("UD", "Undeclared")
            ).queue(); // reply immediately
        }else{
            event.reply("You don't have the `MANAGE_ROLES` permission, which is required to run this command!").setEphemeral(true).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {
        event.reply("Concentrations button").setEphemeral(true).queue();
    }

    @Override
    public List<String> buttons() {
        return Arrays.asList("SE","Bioinformatics","ARG","Data Science","IT","WM","HCI","Cybersecurity","SSN","UD");
    }

    @Override
    public String commandName() {
        return "concentrations";
    }
}
