package Command.Commands;

import Command.ICommand;
import Main.Credentials;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ConnectToVC implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(Credentials.OWNER)) {
            event.getMessage().delete().queue();
            VoiceChannel myChannel = Objects.requireNonNull(event.getJDA()
                    .getGuildById(event.getMember().getVoiceState().getGuild().getId()))
                    .getVoiceChannelById(event.getMember().getVoiceState().getChannel().getId());
            event.getJDA().getGuildById(event.getMember().getVoiceState().getGuild().getId()).getAudioManager().openAudioConnection(myChannel);
        }
    }

    @Override
    public String getCommandName() {
        return "connect";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return null;
    }
}
