package Command.Commands;

import Command.ICommand;
import Main.DataBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Help implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        String prefix = "$";
        DataBase db = new DataBase();
        try { prefix = db.getPrefix("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.cyan);
        embed.setDescription("Woody, use `" + prefix + "help` to show this message again!");
        embed.addField("__" + prefix + "prefix or @the bot__", "Will return the prefix the bot is using in this server\n", false);
        embed.addField("__" + prefix + "stats__", "Will display some debug statistics\n", false);
        embed.addField("__" + prefix + "reactrole__", "Create a reaction role menu! THIS WILL DELETE THE PREVIOUS MENU\n", false);
        embed.addField("__" + prefix + "ccievents__", "Create a cci events role menu! THIS WILL DELETE THE PREVIOUS MENU\n", false);
        embed.addField("__" + prefix + "reactions__", "Create a concentrations role menu! THIS WILL DELETE THE PREVIOUS MENU\n", false);
        embed.addField("__" + prefix + "giveaway__", "Create a giveaway! THIS WILL OVERWRITE THE PREVIOUS GIVEAWAY\n", false);
        embed.addField("__" + prefix + "setprefix__","Will set the prefix the bot uses, requires the manage roles permission\n", false);
        embed.addField("__" + prefix + "addchannel {TextChannelMention}__","Set the channel as a voice text channel, requires the manage channel permission\n", false);
        embed.addField("__" + prefix + "purge-vctext__","Delete and then create a new vc-text, requires the manage channel permission\n", false);
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return "returns a list of commands for Woody";
    }
}
