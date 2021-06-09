package Command;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    public final SlashCommandManager commandManager = new SlashCommandManager();

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        try {
            commandManager.run(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
