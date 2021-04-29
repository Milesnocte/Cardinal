package Command.Commands.RWH;

import Command.ICommand;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class Giveaway implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getGuild().getId().equals("433825343485247499")  && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            try {
                String content = event.getMessage().getContentRaw();
                String emojis = EmojiParser.extractEmojis(content).get(0);
                Listeners.Giveaway.createReaction(event, args.get(0), emojis);
            } catch (Exception e) {
                event.getChannel().sendMessage("Missing emoji, please use the command as `giveaway [message id] [emoji]`. Note that the emoji " +
                        "can not be an emote. It needs to be a discord emoji, from no server.").queue();

            }
        }
    }

    @Override
    public String getCommandName() {
        return "giveaway";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }

    @Override
    public String getHelp() {
        return null;
    }
}
