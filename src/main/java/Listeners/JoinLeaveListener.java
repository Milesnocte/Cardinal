package Listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class JoinLeaveListener extends ListenerAdapter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final Long MONTH_MILLIS = 30L * DAY_MILLIS; // Not perfect
    private static final Long YEAR_MILLIS = 12L * MONTH_MILLIS;

    @Override
    public void onGuildMemberUpdatePending(@NotNull GuildMemberUpdatePendingEvent event) {
        try {
            Member member = event.getMember();
            TextChannel rolesChannel;
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Welcome " + member.getAsMention() + "!");
            if (!event.getGuild().getTextChannelsByName("roles", true).isEmpty()) {
                rolesChannel = event.getGuild().getTextChannelsByName("roles", true).get(0);
                stringBuilder.append(" Please visit " + rolesChannel.getAsMention() + " to select roles!");
            }
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(stringBuilder).queue();
        } catch (Exception ignored){}
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        try {
            long epochMilli = event.getMember().getTimeCreated().toInstant().toEpochMilli();
            Date created = new Date(epochMilli);
            long duration = System.currentTimeMillis() - created.getTime();
            String longAgo = "";

            if (duration < 2 * MINUTE_MILLIS) {
                longAgo = "a minute ago";
            } else if (duration < 60 * MINUTE_MILLIS && duration > MINUTE_MILLIS) {
                longAgo = duration / MINUTE_MILLIS + " minutes ago";
            } else if (duration < 2 * HOUR_MILLIS) {
                longAgo = "an hour ago";
            } else if (duration < 24 * HOUR_MILLIS && duration > HOUR_MILLIS) {
                longAgo = duration / HOUR_MILLIS + " hours ago";
            } else if (duration < 2 * DAY_MILLIS) {
                longAgo = "yesterday";
            } else if (duration < 30L * DAY_MILLIS && duration > DAY_MILLIS) {
                longAgo = duration / DAY_MILLIS + " days ago";
            } else if (duration < 2 * MONTH_MILLIS) {
                longAgo = "a month ago";
            } else if (duration < 12L * MONTH_MILLIS && duration > MONTH_MILLIS) {
                longAgo = duration / MONTH_MILLIS + " months ago";
            } else if (duration < 2L * YEAR_MILLIS && duration > YEAR_MILLIS) {
                longAgo = "a year ago";
            } else if (duration > YEAR_MILLIS) {
                longAgo = duration / YEAR_MILLIS + " years ago";
            }

            event.getGuild().getTextChannelsByName("join-logs", true).get(0).sendMessage("<:entry:918599108502102036> " + event.getMember().getUser().getAsTag() +
                    " | Created " + longAgo + " | ID: " + event.getMember().getId()).queue();

            if (!event.getGuild().getFeatures().contains("WELCOME_SCREEN_ENABLED")) {
                Member member = event.getMember();
                TextChannel rolesChannel;
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Welcome " + member.getAsMention() + "!");
                if (!event.getGuild().getTextChannelsByName("roles", true).isEmpty()) {
                    rolesChannel = event.getGuild().getTextChannelsByName("roles", true).get(0);
                    stringBuilder.append(" Please visit " + rolesChannel.getAsMention() + " to select roles!");
                }
                event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(stringBuilder).queue();
            }
        }catch (Exception ignored){}
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        try {
            event.getGuild().getTextChannelsByName("join-logs", true).get(0).sendMessage("<:exit:918599099484373042> " + event.getMember().getUser().getAsTag()).queue();
        }catch (Exception ignored){}
    }
}
