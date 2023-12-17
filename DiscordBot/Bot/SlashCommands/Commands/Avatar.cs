using Discord;
using System;
using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Avatar : ISlashCommand
{

    public static string Name = "avatar";

    public Task Run(SocketSlashCommand command)
    {
        SocketGuildUser user;
        if (command.Data.Options.Count != 0)
        {
            user = (SocketGuildUser) command.Data.Options.First().Value;
        }
        else
        {
            user = (SocketGuildUser) command.User;
        }
        
        EmbedBuilder embedBuilder = new EmbedBuilder
        {
            Title = $"{user.GlobalName}'s Avatar",
            ImageUrl = user.GetAvatarUrl(size: 4096)
        };
        command.RespondAsync(embed: embedBuilder.Build());

        return Task.CompletedTask;
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Get a users avatar"
        };
        commandInfo.CommandOption.Add(
            new SlashCommandOptionBuilder()
                {
                    Name = "user",
                    Type = ApplicationCommandOptionType.User,
                    Description = "User who's avatar you want",
                    IsRequired = false
                });
        return commandInfo;
    }
}