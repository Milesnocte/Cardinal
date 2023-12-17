using System.Collections;
using System.Data;
using Dapper;
using Discord;
using Discord.WebSocket;
using DiscordBot.Data;
using Models;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Rank : ISlashCommand
{
    
    public static string Name = "rank";

    public async Task Run(SocketSlashCommand command)
    {
        var context = new AppDBContext();
        SocketGuildUser user;
        
        if (command.Data.Options.Count != 0)
        {
            user = (SocketGuildUser) command.Data.Options.First().Value;
        }
        else
        {
            user = (SocketGuildUser) command.User;
        }
        
        List<Levels> members = (await context.Connection().QueryAsync<Levels>("SELECT * FROM get_guild_users_xp(@Server)", new{Server = command.GuildId.Value.ToString()}, commandType: CommandType.Text)).AsList();
        
        Levels xpUser = members
            .Where(m => m.Member == user.Id.ToString())
            .Where(s => s.Server == command.GuildId.Value.ToString())!
            .FirstOrDefault()!;
        int xp = xpUser.Xp;

        List<Levels> rankList = members
            .Where(s => s.Server == command.GuildId.Value.ToString())
            .ToList();

        rankList = rankList.OrderByDescending(x => x.Xp).ToList();
        
        int rank = rankList.IndexOf(xpUser)+1;
        
        int level = 0;
        int xpfornext = 0;
        int lastRes = 0;
        
        for (int i = 0; i < 10000; i++) {
            lastRes = xpfornext;
            xpfornext += (int)(5 * Math.Pow(i, 2) + 50 * i + 100);
            if (xpfornext > xp) {
                level = i+1;
                break;
            }
        }
        
        double current = xp - lastRes;
        int xpForNext = xpfornext - lastRes;

        int percentage = (int)((current/xpForNext) * 100);
        
        List<EmbedFieldBuilder> fields = new List<EmbedFieldBuilder>();
        EmbedFooterBuilder footerBuilder = new EmbedFooterBuilder();
        footerBuilder.Text = $"Total xp: {xp}";
        
        EmbedBuilder embedBuilder = new EmbedBuilder
        {
            Title = $"{user.GlobalName}",
            Description = $"Rank {rank}\nLevel {level} - {(int) current}/{xpForNext}\n{percentage}% of the way to level up!",
            Footer = footerBuilder
        };
        
        command.RespondAsync(embed: embedBuilder.Build(), allowedMentions: AllowedMentions.None);
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Get a user's rank"
        };
        commandInfo.CommandOption.Add(
            new SlashCommandOptionBuilder()
            {
                Name = "user",
                Type = ApplicationCommandOptionType.User,
                Description = "User to get",
                IsRequired = false
            });
        return commandInfo;
    }
}