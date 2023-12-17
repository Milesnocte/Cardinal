using Dapper;
using Discord;
using Discord.WebSocket;
using DiscordBot.Data;
using Models;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Starcheck : ISlashCommand
{
    
    public static string Name = "starcheck";

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
        
        int stars = await context.Connection().QuerySingleAsync<int>("SELECT get_user_stars(@Message)", new { Message = user.Mention });;

        await command.RespondAsync($"{user.Mention} has {string.Format($"{stars:n0}")}<:starfroot:991751462302519316>", allowedMentions: AllowedMentions.None);
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Get a user star count"
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