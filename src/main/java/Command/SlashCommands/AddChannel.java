package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AddChannel implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {

        if (event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {

            if (!event.getOption("channel").getChannelType().isMessage()) {
                event.reply("Please specify a text channel!").queue();
            } else {
                String channelId = event.getOption("channel").getAsMessageChannel().getId();
                TextChannel channel = event.getGuild().getTextChannelById(channelId);
                Role inVoiceChannel = event.getGuild().getRolesByName("InVC", true).get(0);
                event.getGuild().addRoleToMember(event.getGuild().getSelfMember(), inVoiceChannel).queue();

                try {
                    assert channel != null;
                    channel.createPermissionOverride(inVoiceChannel).setAllow(Permission.VIEW_CHANNEL, Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS
                            , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
                } catch (Exception e) {
                    System.out.println("Override already exists, upsert new role");
                    channel.upsertPermissionOverride(inVoiceChannel).setAllow().setAllow(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS
                            , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
                }

                event.reply(Objects.requireNonNull(event.getGuild().getTextChannelById(channelId)).getAsMention() +
                        " is now a Voice Text Channel!").queue();
                channel.upsertPermissionOverride(event.getGuild().getPublicRole()).deny(Permission.VIEW_CHANNEL, Permission.VIEW_CHANNEL).queue();
            }
        } else {
            event.reply("You do not have permission to run this command!").queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "addchannel";
    }

    @Override
    public Boolean enabled() {
        return true;
    }
}
