package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StarCheck implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        String author;
        if (event.getOption("user") != null) {
            author = event.getOption("user").getAsUser().getAsMention();
        } else {
            author = event.getMember().getUser().getAsMention();
        }
        int stars = 0;
        try {
            HashMap<String, Integer> userlist = getStarsSum(event);
            stars = userlist.get(author);
        } catch (Exception ignored) {
        }

        event.reply(author + " has " + stars + "<:starfroot:991751462302519316>")
                .allowedMentions(Collections.emptyList()).queue();
    }

    @Override
    public void run(ButtonInteractionEvent event) throws Exception {
    }

    @Override
    public void run(SelectMenuInteractionEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "starcheck";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }

    public HashMap<String, Integer> getStarsSum(SlashCommandInteractionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        Statement prepared = connect.createStatement();
        ResultSet result = prepared.executeQuery("SELECT Author, SUM(Stars) FROM StarBoard WHERE GuildID ='" + event.getGuild().getId() + "' GROUP BY Author ORDER BY SUM(Stars);");
        HashMap<String, Integer> users = new HashMap<>();
        while (result.next()) {
            users.put(result.getString("Author"), result.getInt("SUM(Stars)"));
        }
        return users;
    }
}