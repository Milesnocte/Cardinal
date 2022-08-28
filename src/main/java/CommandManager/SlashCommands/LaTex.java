package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.List;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXConstants;

public class LaTex implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        try {
            TeXFormula formula = new TeXFormula(event.getOption("content").getAsString());
            event.deferReply().queue();
            //Grouped by guild members and guild id to prevent it from returning the wrong result with simultaneous use
            String path = String.format("./img/%s_%s.png", event.getGuild().getId(), event.getMember().getId());
            formula.createImage("png", TeXConstants.STYLE_DISPLAY, 20, path, Color.white, Color.black, false);
            event.getHook().editOriginal(new File(path)).setContent("Output: ").queue();
        } catch (ParseException e) {
            event.reply("An error has occurred parsing the LaTex formula").queue();
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
        return "latex";
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