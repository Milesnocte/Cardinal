package Listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Giveaway extends ListenerAdapter {

    private static final long INCOMING = 618643756580601857L;
    private static final long FRESHMAN = 581614531789455363L;
    private static final long SOPHOMORE = 618643636665712640L;
    private static final long JUNIOR = 618643685432623135L;
    private static final long SENIOR = 618643717384831017L;
    private static final long GRADUATE = 618643752327708682L;
    private static final long ALUMNI = 449689803219271681L;

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

        for (int k = 0; k < event.getMember().getRoles().size(); k++) {
            for (Long yearRole : yearRoles) {
                if (event.getMember().getRoles().get(k).getId().equals("" + yearRole)) {
                    hasRole = true;
                    break;
                }
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
        } catch (Exception ignored) { }
    }
}
