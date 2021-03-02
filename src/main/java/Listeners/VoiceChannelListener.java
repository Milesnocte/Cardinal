package Listeners;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VoiceChannelListener extends ListenerAdapter implements EventListener {
    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("InVoiceChannel", true).get(0)).queue();
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRolesByName("InVoiceChannel", true).get(0)).queue();
    }
}


