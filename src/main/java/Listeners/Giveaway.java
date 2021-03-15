package Listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Giveaway extends ListenerAdapter {
    private static Connection connect = null;
    private static String m_id;
    public static void createReaction(GuildMessageReceivedEvent event, String mid, String emoji) throws ClassNotFoundException, SQLException {

        event.getChannel().addReactionById(mid,emoji).queue();

        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
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
        Role incoming = event.getGuild().getRolesByName("Incoming Student", true).get(0);
        Role freshman = event.getGuild().getRolesByName("Freshman", true).get(0);
        Role sophomore = event.getGuild().getRolesByName("Sophomore", true).get(0);
        Role junior = event.getGuild().getRolesByName("Junior", true).get(0);
        Role senior = event.getGuild().getRolesByName("Senior", true).get(0);
        Role graduate = event.getGuild().getRolesByName("Graduate Student", true).get(0);
        Role alumni = event.getGuild().getRolesByName("Alumni", true).get(0);
        List<Role> classRoles = Arrays.asList(incoming, freshman, sophomore, junior, senior, graduate, alumni);
        for (Role classRole : classRoles) {
            if (event.getMember().getRoles().contains(classRole)) {
                hasRole = true;
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared1 = connect.createStatement();
            Statement preparedName = connect.createStatement();
            ResultSet guildPrefix = prepared1.executeQuery("SELECT M_id FROM giveaway WHERE rowid = 1;");
            m_id = guildPrefix.getString("M_id");
            ResultSet containsName = preparedName.executeQuery("SELECT names FROM giveaway WHERE names='" + event.getUser().getAsMention() + "';");

            if (event.getMessageId().equals(m_id) && !event.getUser().isBot() && hasRole && !containsName.next()) {
                PreparedStatement prepared2;
                prepared2 = connect.prepareStatement("INSERT INTO giveaway values(?,?);");
                prepared2.setString(1, event.getUser().getAsMention());
                prepared2.setString(2, m_id);
                prepared2.execute();
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
