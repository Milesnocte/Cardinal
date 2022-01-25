package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class UpdateDatabase implements ICommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        Statement prepared = connect.createStatement();
        prepared.execute("PLACEHOLDER");
    }

    @Override
    public String getCommandName() {
        return "updatedatabase";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }
}
