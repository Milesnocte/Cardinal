package Command.Commands;

import Command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AddChannel implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getMember().hasPermission(Permission.MANAGE_CHANNEL)){

            if(args.isEmpty()){
                event.getChannel().sendMessage("Invalid command input, you must mention a channel!").queue();
                return;
            }

            String channelId = args.get(0).replace("<", "")
                    .replace("#", "").replace(">", "");
            TextChannel channel = event.getGuild().getTextChannelById(channelId);
            Role inVoiceChannel = event.getGuild().getRolesByName("InVoiceChannel", true).get(0);
            event.getGuild().addRoleToMember(event.getGuild().getSelfMember(), inVoiceChannel).queue();

            try{
                assert channel != null;
                channel.createPermissionOverride(inVoiceChannel).setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_EMBED_LINKS
                        , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
            }catch (Exception e){
                System.out.println("Override already exists, upsert new role");
                channel.upsertPermissionOverride(inVoiceChannel).setAllow().setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_EMBED_LINKS
                        , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
            }

            event.getChannel().sendMessage(Objects.requireNonNull(event.getGuild().getTextChannelById(channelId)).getAsMention() +
                    " is now a Voice Text Channel!").queue();

            channel.upsertPermissionOverride(event.getGuild().getPublicRole()).deny(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ).queue();
        }else {
            event.getChannel().sendMessage("You do not have permission to run this command!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "addchannel";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.emptyList();
    }
}
