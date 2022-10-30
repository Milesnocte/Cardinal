package Listeners;

import CommandManager.SlashCommands.UNCC;
import Main.Credentials;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarBoardListener extends ListenerAdapter {

    private ArrayList<String> UNCCServers = new ArrayList<>(List.of(
            "433825343485247499", // Party Hours
            "433825343485247499", // Woodward Hours
            "935650201291620392", // Charlotte Haven
            "778743841187823636" // Fretwell hours
    ));


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (UNCCServers.contains(event.getGuild().getId())) {
            if (!event.getChannel().getName().equals("star-board")) {
                switch (event.getReaction().getEmoji().getAsReactionCode()) {
                    case "starfroot:991751462302519316" -> {
                        if (!getExists(event.getMessageId())) {
                            addMessage(event);
                        }
                        addStar(event.getMessageId());
                    }
                    case "antifroot:991751461144887337" -> {
                        if (!getExists(event.getMessageId())) {
                            addMessage(event);
                        }
                        revokeStar(event.getMessageId());
                    }
                    case "debugfroot:936344152004755456" -> {
                        if (event.getMember().getId().equals(Credentials.OWNER)) {
                            postMessage(event);
                        }
                    }
                    default -> {
                        return;
                    }
                }

                int stars = getStars(event.getMessageId());

                if (stars >= 5 && !getPosted(event.getMessageId())) {
                    setPosted(event.getMessageId());
                    postMessage(event);
                }
            }
        }
    }


    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (!event.getChannel().getName().equals("star-board")) {
            switch (event.getReaction().getEmoji().getAsReactionCode()) {
                case "starfroot:991751462302519316" -> revokeStar(event.getMessageId());
                case "antifroot:991751461144887337" -> addStar(event.getMessageId());
            }
        }
    }

    private boolean getExists(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared = connect.createStatement();
            ResultSet inStarBoard = prepared.executeQuery("SELECT EXISTS(SELECT 1 FROM StarBoard WHERE MessageID =  " + messageID + ");");
            boolean exists = inStarBoard.getBoolean(1);
            connect.close();
            return exists;
        } catch (Exception ignored) {}
        return false;
    }

    private int getStars(String messageID) {
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
        }catch (Exception ignored){}
    }

    private void revokeStar(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared = connect.prepareStatement("UPDATE StarBoard SET Stars = Stars - 1 WHERE MessageID = " + messageID);
            prepared.execute();
            connect.close();
        }catch (Exception ignored){}
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

    private void addMessage(MessageReactionAddEvent event) {
        RestAction<Message> action = event.getChannel().retrieveMessageById(event.getMessageId());
        Message message = action.complete();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared;
            prepared = connect.prepareStatement("INSERT INTO StarBoard values(?,?,?,?,?);");
            prepared.setString(1,event.getGuild().getId());
            prepared.setString(2, event.getMessageId());
            prepared.setString(3, message.getAuthor().getAsMention());
            prepared.setInt(4, 0);
            prepared.setBoolean(5, false);
            prepared.execute();
        } catch (Exception ignored) {
        }
    }

    private void postMessage(MessageReactionAddEvent event){
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

        event.getGuild().getTextChannelsByName("star-board", true).get(0)
                .sendMessageEmbeds(embed.build()).setActionRow(Button.link(message.getJumpUrl(),"Jump to message")).queue();
    }
}