package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StarCheck implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        String author = event.getOption("user").getAsMentionable().getAsMention();
        HashMap<String, Integer> userlist = getStarsSum();
        int stars = userlist.get(author);
        event.reply(event.getOption("user").getAsMember().getAsMention() + " has " + stars + "<:starfroot:468218976430981140>")
                .allowedMentions(Collections.emptyList()).queue();
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {}

    @Override
    public List<String> buttons() {return Collections.emptyList();}

    @Override
    public String commandName() {
        return "starcheck";
    }

    public HashMap<String, Integer> getStarsSum() throws ClassNotFoundException, SQLException {
        ArrayList<String> topStars = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        Statement prepared = connect.createStatement();
        ResultSet result = prepared.executeQuery("SELECT Author, SUM(Stars) FROM StarBoard GROUP BY Author ORDER BY SUM(Stars);");
        HashMap<String, Integer> users = new HashMap<>();
        while(result.next()){
            users.put(result.getString("Author"), result.getInt("SUM(Stars)"));
        }
        return users;
    }
}
