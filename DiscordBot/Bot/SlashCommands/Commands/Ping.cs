using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Ping : ISlashCommand
{
    
    public static string Name = "ping";

    public Task Run(SocketSlashCommand command)
    {
        command.RespondAsync("Pong!");
        return Task.CompletedTask;
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Ping the bot"
        };
        return commandInfo;
    }
}