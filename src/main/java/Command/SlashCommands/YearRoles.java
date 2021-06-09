package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.Arrays;
import java.util.List;

public class YearRoles implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            event.reply("\n**__What year are you?__**\n").addActionRow(
                    Button.success("Incoming", "Incoming"),
                    Button.success("Freshman", "Freshman"),
                    Button.success("Sophomore", "Sophomore")

            ).addActionRow(
                    Button.success("Junior", "Junior"),
                    Button.success("Senior", "Senior"),
                    Button.success("Grad", "Grad"),
                    Button.success("Alumni", "Alumni")
            ).queue(); // reply immediately
        }else{
            event.reply("You don't have the `MANAGE_ROLES` permission, which is required to run this command!").setEphemeral(true).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {
        event.reply("YearRoles button").setEphemeral(true).queue();
    }

    @Override
    public List<String> buttons() {
        return Arrays.asList("Incoming","Freshman","Sophomore","Junior","Senior","Grad","Alumni");
    }

    @Override
    public String commandName() {
        return "yearroles";
    }
}
