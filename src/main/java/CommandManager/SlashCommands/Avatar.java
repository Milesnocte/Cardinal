package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.Collections;
import java.util.List;

public class Avatar implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        try {
            event.deferReply(false).queue();
            String userid;

            if (event.getOption("user") != null) {
                userid = event.getOption("user").getAsMember().getId();
            } else {
                userid = event.getMember().getId();
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(event.getGuild().getMemberById(userid).getEffectiveName() + "'s Avatar");
            embed.setImage(event.getGuild().getMemberById(userid).getUser().getAvatarUrl());
            event.getHook().editOriginalEmbeds(embed.build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
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
