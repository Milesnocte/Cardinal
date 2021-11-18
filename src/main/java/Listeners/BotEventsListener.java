package Listeners;

import Main.DataBase;
import Main.ScheduledTask;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.managers.RoleManager;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.Date;

public class BotEventsListener extends ListenerAdapter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final Long MONTH_MILLIS = 30L * DAY_MILLIS; // Not perfect
    private static final Long YEAR_MILLIS = 12L * MONTH_MILLIS;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        // Create scheduled task that runs once a day
        new ScheduledTask(event);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        DataBase db = new DataBase();
        try {db.addGuild("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}

        boolean roleExists = (event.getGuild().getRolesByName("InVoiceChannel", true).isEmpty());
        if(roleExists) {
            RoleAction ra = event.getGuild().createRole().setMentionable(true).setName("InVoiceChannel").setColor(Color.CYAN);
            ra.complete();
            RoleManager roleManager = event.getGuild().getRolesByName("InVoiceChannel", true).get(0).getManager();
            roleManager.setPermissions(Permission.VOICE_CONNECT,Permission.VOICE_SPEAK,Permission.MESSAGE_HISTORY,Permission.MESSAGE_READ,Permission.MESSAGE_WRITE,
                    Permission.VOICE_STREAM,Permission.VOICE_USE_VAD,Permission.MESSAGE_EMBED_LINKS,Permission.MESSAGE_EXT_EMOJI,Permission.MESSAGE_ADD_REACTION,
                    Permission.MESSAGE_ATTACH_FILES,Permission.NICKNAME_CHANGE).queue();
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        DataBase db = new DataBase();
        try {db.dropGuild("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if(event.getGuild().getId().equals("433825343485247499")) {
            long epochMilli = event.getMember().getTimeCreated().toInstant().toEpochMilli();
            Date created = new Date(epochMilli);
            long duration = System.currentTimeMillis() - created.getTime();
            String longAgo = "";

            if (duration < 2 * MINUTE_MILLIS) {
                longAgo = "a minute ago";
            } else if(duration < 60 * MINUTE_MILLIS && duration > MINUTE_MILLIS){
                longAgo = duration/MINUTE_MILLIS + " minutes ago";
            } else if(duration < 2 * HOUR_MILLIS){
                longAgo = "an hour ago";
            } else if(duration < 24 * HOUR_MILLIS && duration > HOUR_MILLIS){
                longAgo = duration/HOUR_MILLIS + " hours ago";
            } else if(duration < 2 * DAY_MILLIS){
                longAgo = "yesterday";
            } else if(duration < 30L * DAY_MILLIS && duration > DAY_MILLIS){
                longAgo = duration/DAY_MILLIS + " days ago";
            } else if(duration < 2 * MONTH_MILLIS){
                longAgo = "a month ago";
            } else if(duration < 12L * MONTH_MILLIS && duration > MONTH_MILLIS){
                longAgo = duration/MONTH_MILLIS + " months ago";
            } else if(duration < 2L * YEAR_MILLIS && duration > YEAR_MILLIS){
                longAgo = "a year ago";
            } else if(duration > YEAR_MILLIS){
                longAgo = duration/YEAR_MILLIS + " years ago";
            }

            event.getGuild().getTextChannelById("582399523649880065").sendMessage("<:entry:866757078130753606> " + event.getMember().getUser().getAsTag() +
                    " | Created " + longAgo + " | ID: " + event.getMember().getId()).queue();

            if((event.getMember().getUser().getAsTag().contains("||") && duration < 60 * MINUTE_MILLIS) ||
                    (event.getMember().getUser().getAsTag().toLowerCase().contains("discord.gg/") ||
                            event.getMember().getUser().getAsTag().toLowerCase().contains("discordgate"))) {

                event.getMember().ban(7).queue();
                event.getGuild().getTextChannelById("582399523649880065").sendMessage("<:exit:866757078143991838> Discordgate bot banned").queue();
            }
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if(event.getGuild().getId().equals("433825343485247499") && !event.getMember().getUser().getAsTag().contains("||")){ // Dont send this message if its a discord gate bot
            event.getGuild().getTextChannelById("582399523649880065").sendMessage("<:exit:866757078143991838> " + event.getMember().getUser().getAsTag()).queue();
        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if(!event.getJDA().getSelfUser().getId().equals(event.getAuthor().getId())) {
            event.getJDA().getGuildById("869677292610285628").getTextChannelById("869677556373258320").sendMessage(
                    "From: " + event.getAuthor().getAsTag() + " - " + event.getMessage().getContentDisplay()).queue();
        }
    }
}
