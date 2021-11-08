package Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class StarBoardListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getReaction().getReactionEmote().toString().equals("RE:starfroot(468218976430981140)")){
            if(!event.getChannel().getName().equals("star-board")) {

                int stars = 0;

                try {
                    stars = getSars(event.getMessageId());
                } catch (Exception ignored) {
                }

                if (stars > 0) {
                    addStar(event.getMessageId());
                } else {
                    addMessage(event);
                }

                // Add the star that was just reacted
                stars += 1;

                if (stars >= 5 && !getPosted(event.getMessageId())) {
                    setPosted(event.getMessageId());
                    RestAction<Message> action = event.getChannel().retrieveMessageById(event.getMessageId());
                    Message message = action.complete();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor(message.getAuthor().getAsTag(), message.getJumpUrl(), message.getAuthor().getAvatarUrl());
                    if (message.getReferencedMessage() != null) {
                        embed.addField("**Reply to " + message.getReferencedMessage().getAuthor().getAsTag() + "**",
                                message.getReferencedMessage().getContentDisplay(), false);
                    }
                    if (!message.getContentDisplay().isBlank()) {
                        embed.addField("**Message**", message.getContentDisplay(), false);
                    }
                    if (message.getAttachments().size() > 0) {
                        embed.setImage(message.getAttachments().get(0).getUrl());
                    }
                    embed.setFooter("Sent on: " + message.getTimeCreated().atZoneSameInstant(ZoneId.of("America/New_York"))
                            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")));
                    event.getGuild().getTextChannelsByName("star-board", true).get(0).sendMessageEmbeds(embed.build()).queue();
                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        if(event.getReaction().getReactionEmote().toString().equals("RE:starfroot(468218976430981140)")){

            int stars = 0;

            try{
                stars = getSars(event.getMessageId());
            }catch (Exception ignored){}

            if(stars > 0){
                revokeStar(event.getMessageId());
            }
        }
    }

    private int getSars(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared = connect.createStatement();
            ResultSet inStarBoard = prepared.executeQuery("SELECT Stars FROM StarBoard WHERE MessageID =  " + messageID + ";");
            int messageIn = inStarBoard.getInt(1);
            connect.close();
            return messageIn;
        } catch (Exception ignored) {}
        return 0;
    }

    private boolean getPosted(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared = connect.createStatement();
            ResultSet inStarBoard = prepared.executeQuery("SELECT Posted FROM StarBoard WHERE MessageID =  " + messageID + ";");
            boolean messagePosted = inStarBoard.getBoolean(1);
            connect.close();
            return messagePosted;
        } catch (Exception ignored) {}
        return true;
    }

    private void addStar(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared = connect.prepareStatement("UPDATE StarBoard SET Stars = Stars + 1 WHERE MessageID = " + messageID);
            prepared.execute();
            connect.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void revokeStar(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared = connect.prepareStatement("UPDATE StarBoard SET Stars = Stars - 1 WHERE MessageID = " + messageID);
            prepared.execute();
            connect.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPosted(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared = connect.prepareStatement("UPDATE StarBoard SET Posted = true WHERE MessageID = " + messageID);
            prepared.execute();
            connect.close();
        }catch (Exception ignored){}
    }

    private void addMessage(GuildMessageReactionAddEvent event){
        RestAction<Message> action = event.getChannel().retrieveMessageById(event.getMessageId());
        Message message = action.complete();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared;
            prepared = connect.prepareStatement("INSERT INTO StarBoard values(?,?,?,?);");
            prepared.setString(1,event.getMessageId());
            prepared.setInt(2,1);
            prepared.setString(3,message.getAuthor().getAsMention());
            prepared.setBoolean(4,false);
            prepared.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
