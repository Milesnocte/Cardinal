package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import java.util.Collections;
import java.util.List;

public class Avatar implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        try {
            event.deferReply().queue();
            EmbedBuilder embed = new EmbedBuilder();
            String subcommandName = event.getSubcommandName();
            switch (subcommandName) {
                case "server" -> {
                    if (event.getOption("user") == null) {
                        embed.setImage(event.getMember().getEffectiveAvatarUrl() + "?size=4096");
                        embed.setTitle(event.getMember().getEffectiveName() + "'s Avatar");
                    } else {
                        embed.setImage(event.getOption("user").getAsMember().getEffectiveAvatarUrl() + "?size=4096");
                        embed.setTitle(event.getOption("user").getAsMember().getEffectiveName() + "'s Avatar");
                    }
                }
                case "global" -> {
                    if (event.getOption("user") == null) {
                        embed.setImage(event.getUser().getEffectiveAvatarUrl() + "?size=4096");
                        embed.setTitle(event.getMember().getEffectiveName() + "'s Avatar");
                    } else {
                        embed.setImage(event.getOption("user").getAsUser().getEffectiveAvatarUrl() + "?size=4096");
                        embed.setTitle(event.getOption("user").getAsMember().getEffectiveName() + "'s Avatar");
                    }
                }
            }
            event.getHook().editOriginalEmbeds(embed.build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
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
        return "avatar";
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