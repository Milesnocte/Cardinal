package Listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Giveaway extends ListenerAdapter {

    private static final long INCOMING = 822305355828559893L;
    private static final long FRESHMAN = 822305557426864170L;
    private static final long SOPHOMORE = 822305633327120404L;
    private static final long JUNIOR = 822305682882691094L;
    private static final long SENIOR = 822305742476148786L;
    private static final long GRADUATE = 822305775967141930L;
    private static final long ALUMNI = 822305835086512178L;

    private static final List<Long> yearRoles = Arrays.asList(INCOMING, FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, GRADUATE, ALUMNI);
    private static Connection connect = null;
    private static String messageId;

    public static void createReaction(GuildMessageReceivedEvent event, String inMessageId, String emoji) throws ClassNotFoundException, SQLException {

        event.getChannel().addReactionById(inMessageId,emoji).queue();

        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("INSERT INTO giveaway values(?,?);");
        prepared.setString(1,"null");
        prepared.setString(2,inMessageId);
        prepared.execute();
        connect.close();
        messageId = inMessageId;
        event.getMessage().delete().queue();
    }
    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event)  {
        boolean hasRole = false;

        for (int k = 0; k < yearRoles.size(); k++) {
            if (event.getMember().getRoles().get(k).getIdLong() == yearRoles.get(k)) {
                hasRole = true;
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
            Statement prepared1 = connect.createStatement();
            Statement preparedName = connect.createStatement();
            ResultSet guildPrefix = prepared1.executeQuery("SELECT M_id FROM giveaway WHERE rowid = 1;");
            messageId = guildPrefix.getString("M_id");
            ResultSet containsName = preparedName.executeQuery("SELECT names FROM giveaway WHERE names='" + event.getUser().getAsMention() + "';");

            if (event.getMessageId().equals(messageId) && !event.getUser().isBot() && hasRole && !containsName.next()) {
                PreparedStatement prepared2;
                prepared2 = connect.prepareStatement("INSERT INTO giveaway values(?,?);");
                prepared2.setString(1, event.getUser().getAsMention());
                prepared2.setString(2, messageId);
                prepared2.execute();
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
