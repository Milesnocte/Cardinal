package Listeners;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Giveaway extends ListenerAdapter {
    private static Role Incoming;
    private static Role Freshman;
    private static Role Sophomore;
    private static Role Junior;
    private static Role Senior;
    private static Role Graduate;
    private static Role Alumni;
    private static List<Role> classRoles;
    private static Connection connect = null;
    private static String m_id;
    public static void createReaction(GuildMessageReceivedEvent event, String mid, String emote) throws ClassNotFoundException, SQLException {
        String content = event.getMessage().getContentRaw();
        String emojis = EmojiParser.extractEmojis(content).get(0);
        event.getChannel().addReactionById(mid,emojis).queue();

        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared = null;
        prepared = connect.prepareStatement("INSERT INTO giveaway values(?,?);");
        prepared.setString(1,"null");
        prepared.setString(2,mid);
        prepared.execute();
        connect.close();
        m_id = mid;
        event.getMessage().delete().queue();
    }
    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event)  {
        boolean hasRole = false;
        Incoming = event.getGuild().getRolesByName("Incoming Student", true).get(0);
        Freshman = event.getGuild().getRolesByName("Freshman", true).get(0);
        Sophomore = event.getGuild().getRolesByName("Sophomore", true).get(0);
        Junior = event.getGuild().getRolesByName("Junior", true).get(0);
        Senior = event.getGuild().getRolesByName("Senior", true).get(0);
        Graduate = event.getGuild().getRolesByName("Graduate Student", true).get(0);
        Alumni = event.getGuild().getRolesByName("Alumni", true).get(0);
        classRoles = Arrays.asList(Incoming,Freshman,Sophomore,Junior,Senior,Graduate,Alumni);
        for(int k = 0; k < classRoles.size(); k++){
            if(event.getMember().getRoles().contains(classRoles.get(k))){
                hasRole = true;
            }
        }
        try{
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared1 = connect.createStatement();
            Statement preparedName = connect.createStatement();
            ResultSet guildPrefix = prepared1.executeQuery("SELECT M_id FROM giveaway WHERE rowid = 1;");
            m_id = guildPrefix.getString("M_id");
            ResultSet containsName = preparedName.executeQuery("SELECT names FROM giveaway WHERE names='" + event.getUser().getAsMention() + "';");

            if(event.getMessageId().equals(m_id) && !event.getUser().isBot() && hasRole && !containsName.next()) {
                PreparedStatement prepared2 = null;
                prepared2 = connect.prepareStatement("INSERT INTO giveaway values(?,?);");
                prepared2.setString(1, event.getUser().getAsMention());
                prepared2.setString(2, m_id);
                prepared2.execute();
            }
            connect.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
