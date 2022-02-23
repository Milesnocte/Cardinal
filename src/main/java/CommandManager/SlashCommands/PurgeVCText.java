package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PurgeVCText implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        if (event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getGuild().getTextChannelsByName("vc-text", true).get(0).createCopy().queue();
            TimeUnit.SECONDS.sleep(1);
            event.getGuild().getTextChannelsByName("vc-text", true).get(0).delete().queue();
        }
    }

    @Override
    public void run(ButtonInteractionEvent event) throws Exception {
    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "purgevctxt";
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
