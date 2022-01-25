package Command.SlashCommands;

import Command.ISlashCommand;
import Main.Credentials;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class WhoIs implements ISlashCommand {
    @Override
    public void run(SlashCommandEvent event) throws Exception {
        try {
            event.deferReply(false).queue();
            String userid;
            if (event.getOption("user") != null) {
                userid = event.getOption("user").getAsMember().getId();
            } else {
                userid = event.getMember().getId();
            }
            User user = event.getGuild().getMemberById(userid).getUser();
            Member member = event.getGuild().getMemberById(userid);
            EmbedBuilder embed = new EmbedBuilder();
            StringBuilder roles = new StringBuilder();

            for (int k = 0; k < member.getRoles().size(); k++) {
                roles.append(member.getRoles().get(k).getAsMention()).append(" ");
            }

            embed.setThumbnail(user.getAvatarUrl());
            embed.setTitle(user.getAsTag());
            embed.addField("Created", "" + member.getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd yyyy")), true);
            embed.addField("Joined", "" + member.getTimeJoined().format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "            ", true);

            if (member.getTimeBoosted() != null) {
                embed.addField("Booster", "Yes", true);
            } else {
                embed.addField("Booster", "No", true);
            }

            if (member.getNickname() != null) {
                embed.addField("NickName", member.getNickname(), true);
            } else {
                embed.addField("NickName", member.getEffectiveName(), true);
            }

            embed.addField("ID", user.getId(), true);
            embed.addField("Roles", roles.toString(), false);
            if (member.getId().equals(Credentials.OWNER)) {
                embed.setFooter("\u2B50 Woody dev \u2B50");
            }
            event.getHook().editOriginalEmbeds(embed.build()).queue();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return "whois";
    }

    @Override
    public Boolean enabled() {
        return true;
    }
}
