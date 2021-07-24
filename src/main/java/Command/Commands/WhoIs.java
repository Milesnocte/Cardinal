package Command.Commands;

import Command.ICommand;
import Main.Credentials;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class WhoIs implements ICommand {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        try{
            String userid;
            if(args.isEmpty()){
                userid = event.getMember().getId();
            } else {
                userid = args.get(0).replace("<","").replace("@","").replace(">","").replace("!","");
            }
            User user = event.getGuild().getMemberById(userid).getUser();
            Member member = event.getGuild().getMemberById(userid);
            EmbedBuilder embed = new EmbedBuilder();
            StringBuilder roles = new StringBuilder();

            for(int k = 0; k < member.getRoles().size(); k++){
                roles.append(member.getRoles().get(k).getAsMention()).append(" ");
            }

            embed.setThumbnail(user.getAvatarUrl());
            embed.setTitle(user.getAsTag());
            embed.addField("Created", "" + member.getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd yyyy")), true);
            embed.addField("Joined", "" + member.getTimeJoined().format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "            ", true);

            if(member.getTimeBoosted() != null){ embed.addField("Booster", "Yes", true);
            }else{ embed.addField("Booster", "No", true); }

            if(member.getNickname() != null){embed.addField("NickName", member.getNickname(),true);}
            else { embed.addField("NickName", member.getEffectiveName(), true); }

            embed.addField("ID",user.getId(), true);
            embed.addField("Roles", roles.toString(), false);
            if(member.getId().equals(Credentials.OWNER)){
                embed.setFooter("\u2B50 Woody dev \u2B50");
            }else if(member.getId().equals("573339588442193930")){
                embed.setFooter("\uD83E\uDD16 Miles' bot \uD83E\uDD16");
            }
            event.getChannel().sendMessage(embed.build()).queue();

        }catch (Exception e){
            e.printStackTrace();
            event.getChannel().sendMessage("Invalid userid argument, please copy the id for a valid user!").queue();
        }
    }

    @Override
    public String getCommandName() {
        return "whois";
    }

    @Override
    public List<String> getCommandAlias() {
        return Collections.singletonList("who");
    }

    @Override
    public String getHelp() {
        return null;
    }
}
