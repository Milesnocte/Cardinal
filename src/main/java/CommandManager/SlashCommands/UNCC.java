package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import Main.FetchUNCC;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UNCC implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws IOException, InterruptedException {
        event.deferReply().queue();
        switch (event.getSubcommandName()) {
            case "sovi" -> {
                
                String openInfo = FetchUNCC.openInfo("SoVi");
                event.getHook().editOriginal(new File("./img/sovi.png")).setContent("Sovi Occupancy: "+openInfo).queue();

            }
            case "crown" ->
                event.getHook().editOriginal(new File("./img/crown.png")).setContent("Crown Occupancy").queue();
            case "parking" ->
                event.getHook().editOriginal(new File("./img/parking.png")).setContent("Parking Availability").queue();

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
        return "uncc";
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