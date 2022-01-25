package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopStars implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {

        ArrayList<String> topStars = getTopStars();
        StringBuilder starList = new StringBuilder();
        for (int k = 0; k < 15; k++) {
            starList.append(topStars.get(k)).append("\n");
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.cyan);
        embed.setDescription("Check out the <#904851888460152863>!");
        embed.addField("Starfroot Leaderboard", starList.toString(), false);
        event.replyEmbeds(embed.build()).queue();
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "topstars";
    }

    @Override
    public Boolean enabled() {
        return false;
    }

    public ArrayList<String> getTopStars() throws ClassNotFoundException, SQLException {
        ArrayList<String> topStars = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        Statement prepared = connect.createStatement();
        ResultSet result = prepared.executeQuery(
                "SELECT Author, SUM(Stars) FROM StarBoard GROUP BY Author ORDER BY SUM(Stars) DESC LIMIT 15;"
        );
        int counter = 1;
        while (result.next()) {
            String author = result.getString("Author");
            int stars = result.getInt("SUM(Stars)");
            topStars.add(stars + " <:starfroot:468218976430981140> - " + author);
            counter++;
        }
        connect.close();
        return topStars;
    }
}
