package Managers;

import Listeners.Giveaway;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import Main.*;
import net.dv8tion.jda.api.managers.AudioManager;


public class CommandManager {
    public CommandManager(GuildMessageReceivedEvent event, String[] args) throws Exception {

        //TODO: This needs some serious refactoring

        String prefix = "$";
        DataBase db = new DataBase();
        try { prefix = db.getPrefix("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}
        boolean hasManagePermission = Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_PERMISSIONS);

        if(args[0].equalsIgnoreCase("prefix")) {
            try {
                event.getChannel().sendMessage("`" + db.getPrefix("" + event.getGuild().getIdLong()) + "` is my prefix!").queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(args[0].equalsIgnoreCase("setprefix") && hasManagePermission) {
            try {
                if (args[1].length() <= 2) {
                    try {
                        db.updateGuildPrefix("" + event.getGuild().getIdLong(), "" + args[1]);
                        event.getChannel().sendMessage("`" + args[1] + "` is now my prefix!").queue();
                    } catch (Exception e) {event.getChannel().sendMessage("Please specify a prefix").queue();}
                } else {
                    event.getChannel().sendMessage("`" + args[1] + "` is too long, max is 2 characters!").queue();
                }
            }catch (Exception e){
                event.getChannel().sendMessage("Please specify a prefix").queue();
            }

        }else if(args[0].equalsIgnoreCase("setprefix") && !event.getMember().getPermissions().contains(Permission.MANAGE_ROLES)){
            event.getChannel().sendMessage("You do not have enough permission to run this command!").queue();
        }

        if(args[0].equalsIgnoreCase("stats")) {

            long currentTime = System.currentTimeMillis();
            String command ="ping 8.8.8.8";
            Process process = Runtime.getRuntime().exec(command);
            process.getOutputStream();
            currentTime = System.currentTimeMillis() - currentTime;

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.cyan);
            embed.setDescription("**Woody By MilesNocte**");
            int trim = event.getJDA().getRateLimitPool().toString().indexOf("[");
            embed.addField("**Statistics**","`Ping:` " + currentTime + "ms" +
                    "\n`Guilds:` " + event.getJDA().getGuilds().size() +
                    "\n`Bot ID:` " + event.getJDA().getSelfUser().getId() +
                    "\n`Rate Pool:` \n" + event.getJDA().getRateLimitPool().toString().substring(trim) +
                    "\nBot API: `JDA 4.2.0_227`", false);
            event.getChannel().sendMessage(embed.build()).queue();

        }

        if(args[0].equalsIgnoreCase("addchannel") && hasManagePermission) {

            String channelId = args[1].replace("<", "")
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

        }else if(args[0].equalsIgnoreCase("addchannel") && !hasManagePermission){
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " you do not have high enough permissions in the server to run this command!").queue();
        }

        if(args[0].equalsIgnoreCase("help")) {

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

        // RWH specific commands
        if(event.getGuild().getId().equals("433825343485247499")) {

            if (args[0].equalsIgnoreCase("purge-vctext") && hasManagePermission) {
                event.getMessage().delete().queue();

                Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).createCopy().queue();

                TimeUnit.SECONDS.sleep(1);

                Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).delete().queue();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setDescription("VC-Text purged by " + event.getMessage().getAuthor().getAsTag() + "!");

                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelById("582399042240118814")).sendMessage(embed.build()).queue();
            }

            if (args[0].equalsIgnoreCase("yearroles") && hasManagePermission) {
                YearMenu.createMenu(event);
            }

            if (args[0].equalsIgnoreCase("ccievents") && hasManagePermission) {
                CCIEvents.createMenu(event);
            }

            if (args[0].equalsIgnoreCase("giveaway") && hasManagePermission) {
                try {
                    String content = event.getMessage().getContentRaw();
                    String emojis = EmojiParser.extractEmojis(content).get(0);
                    Giveaway.createReaction(event, args[1], emojis);
                } catch (Exception e) {
                    event.getChannel().sendMessage("Missing emoji, please use the command as `giveaway [message id] [emoji]`. Note that the emoji " +
                            "can not be an emote. It needs to be a discord emoji, from no server.").queue();
                }
            }

            if (args[0].equalsIgnoreCase("draw") && hasManagePermission) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Congratulations " + DataBase.getDrawing() + ", you won the giveaway! <@!573339588442193930> will be in contact as soon as possible!").queue();
            }

            if (args[0].equalsIgnoreCase("connect") && event.getMember().getId().equals("225772174336720896")) {
                event.getMessage().delete().queue();
                VoiceChannel myChannel = Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getVoiceChannelById("793689353703653386");
                AudioManager audioManager = Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getAudioManager();
                audioManager.openAudioConnection(myChannel);
            }
            if (args[0].equalsIgnoreCase("leave") && event.getMember().getId().equals("225772174336720896")) {
                event.getGuild().getAudioManager().closeAudioConnection();
            }
            if (args[0].equalsIgnoreCase("concentration") && hasManagePermission) {
                Concentrations.createMenu(event);
            }
        }

        if(args[0].equalsIgnoreCase("me") && event.getMember().getId().equals("225772174336720896")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(event.getMessage().getContentRaw().replace("$me","")).queue();
        }

        if(args[0].equalsIgnoreCase("whois")) {
            try{
                String userid = args[1].replace("<","").replace("@","").replace(">","").replace("!","");
                User user = event.getGuild().getMemberById(userid).getUser();
                Member member = event.getGuild().getMemberById(userid);
                EmbedBuilder embed = new EmbedBuilder();
                String roles = "";

                for(int k = 0; k < member.getRoles().size(); k++){
                    roles += member.getRoles().get(k).getAsMention() + " ";
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
                embed.addField("Roles",roles, false);
                embed.addField("Perms",member.getPermissions().toString(), false);
                if(member.getId().equals("225772174336720896")){
                    embed.setFooter("\u2B50 BeanBot dev \u2B50");
                }
                event.getChannel().sendMessage(embed.build()).queue();

            }catch (Exception e){
                e.printStackTrace();
                event.getChannel().sendMessage("Invalid userid argument, please copy the id for a valid user!").queue();
            }

        }

    }
}
