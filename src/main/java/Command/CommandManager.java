package Command;

import Command.Commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new Echo());
        addCommand(new UpdateCommands());
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

    public Collection<ICommand> getCommands(){
        return commands.values();
    }

    void run(GuildMessageReceivedEvent event) throws Exception {
        final String msg = event.getMessage().getContentRaw();
        final String prefix = "$";
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