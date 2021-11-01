package Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class StarBoardListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getReaction().getReactionEmote().toString().equals("RE:starfroot(468218976430981140)")){

            int stars = 0;

            try{
                stars = tesMessageID(event.getMessageId());
            }catch (Exception ignored){}

            if(stars > 0){
                addStar(event.getMessageId());
            }else{
                addMessage(event);
            }

            event.getChannel().sendMessage(stars + " <:PauseChamp:890080347793010750>").queue();

            if(stars >= 5){
                RestAction<Message> action = event.getChannel().retrieveMessageById(event.getMessageId());
                Message message = action.complete();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("__" + message.getAuthor().getName() + "__");
                embed.setFooter("ID: " + message.getId());
                embed.setDescription(message.getJumpUrl());
                embed.addField("",message.getContentDisplay(),false);
                embed.addField("",message.getAttachments().get(0).getProxyUrl(),false);
                event.getGuild().getTextChannelsByName("star-board", true)
                        .get(0).sendMessageEmbeds(embed.build()).queue();
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        if(event.getReaction().getReactionEmote().toString().equals("RE:starfroot(468218976430981140)")){

            int stars = 0;

            try{
                stars = tesMessageID(event.getMessageId());
            }catch (Exception ignored){}

            event.getChannel().sendMessage(stars + " <:PauseChamp:890080347793010750>").queue();

            if(stars > 0){
                revokeStar(event.getMessageId());
            }
        }
    }

    private int tesMessageID(String messageID){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared = connect.createStatement();
            ResultSet inStarBoard = prepared.executeQuery("SELECT Stars FROM StarBoard WHERE MessageID =  " + messageID + ";");
            int messageIn = inStarBoard.getInt(1);
            connect.close();
            return messageIn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    private void addMessage(GuildMessageReactionAddEvent event){
        RestAction<Message> action = event.getChannel().retrieveMessageById(event.getMessageId());
        Message message = action.complete();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            PreparedStatement prepared;
            prepared = connect.prepareStatement("INSERT INTO StarBoard values(?,?,?);");
            prepared.setString(1,event.getMessageId());
            prepared.setInt(2,1);
            prepared.setString(3,message.getAuthor().getAsMention());
            prepared.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
