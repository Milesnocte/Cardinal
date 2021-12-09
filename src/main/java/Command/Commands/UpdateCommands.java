package Command.Commands;

import Command.ICommand;
import Main.Credentials;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Collections;
import java.util.List;

public class UpdateCommands implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) throws Exception {
        if (event.getAuthor().getId().equals(Credentials.OWNER)) {

            event.getChannel().sendMessage("This might take a moment").queue();

            event.getJDA().updateCommands()
                    .addCommands(
                            new CommandData("ping", "Ping the bot"),

                            new CommandData("purgevctxt", "Purge vc text"),

                            new CommandData("stats", "Get Woody's status"),

                            new CommandData("avatar", "Get user avatar")
                                    .addOption(OptionType.USER, "user", "user to get avatar from", false),

                            new CommandData("whois", "Get information about user")
                                    .addOption(OptionType.USER, "user", "user to get information about", false),

                            new CommandData("purge", "bulk delete")
                                    .addOption(OptionType.INTEGER, "num_messages", "number of messages to delete 1-100", true),

                            new CommandData("menus", "Role Menus")
                                    .addSubcommands(
                                            new SubcommandData("yearroles", "create a year role menu"),
                                            new SubcommandData("pronounroles", "create a pronoun role menu"),
                                            new SubcommandData("polirole", "create a debate role menu"),
                                            new SubcommandData("collegeroles", "create a college role menu"),
                                            new SubcommandData("concentration", "create a concentration role menu")
                                    ),

                            new CommandData("define", "Get definition of word")
                                    .addOption(OptionType.STRING, "word_to_define", "A word to define", true),

                            new CommandData("eightball", "Shake the 8-ball")
                                    .addOption(OptionType.STRING, "question", "Ask the 8-ball a question.", true),

                            new CommandData("shutdown", "shutdown the bot"),

                            new CommandData("restart", "restart the bot"),

                            new CommandData("topconcentrations", "Get concentrations leaderboard"),

                            new CommandData("topstars", "Get starfroot leaderboard"),

                            new CommandData("addchannel", "Set text channel as vc-text channel")
                                    .addOption(OptionType.CHANNEL, "channel", "The channel to modify", true)
                    ).queue();
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "updatecommands";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }
}
