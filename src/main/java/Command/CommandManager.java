package Command;

import Command.Commands.*;
import Command.Commands.RWH.*;
import Main.DataBase;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new Ping());
        addCommand(new Prefix());
        addCommand(new SetPrefix());
        addCommand(new AddChannel());
        addCommand(new Stats());
        addCommand(new Avatar());
        addCommand(new ConnectToVC());
        addCommand(new LeaveVC());
        addCommand(new WhoIs());
        addCommand(new ConcentrationLeaderBoard());
        addCommand(new Purge());
        addCommand(new Help());
        addCommand(new PurgeVCText());
        addCommand(new YearRoles());
        addCommand(new Giveaway());
        addCommand(new DrawGiveaway());
        addCommand(new ConcentrationRoles());
        addCommand(new CCIEventsRole());
        addCommand(new Me());
    }

    private void addCommand(ICommand c) {
        if(!commands.containsKey(c.getCommandName())){

            commands.put(c.getCommandName(), c);

            if(!(c.getCommandAlias().isEmpty())){
                for(String k : c.getCommandAlias()){
                    commands.put(k, c);
                }
            }else{
                System.out.println(c.getCommandName() + " had no aliases");
            }
        }
    }

    void run(GuildMessageReceivedEvent event) throws Exception {
        final DataBase db = new DataBase();
        final String msg = event.getMessage().getContentRaw();
        final String prefix = db.getPrefix(String.valueOf(event.getGuild().getIdLong()));
        if((!msg.startsWith(prefix)) || event.getAuthor().isBot()){
            return;
        }
        final String[] split = msg.replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String command = split[0].toLowerCase();
        if(commands.containsKey(command)){
            final List<String> args = Arrays.asList(split).subList(1, split.length);
            commands.get(command).run(args, event);
        }
    }
}