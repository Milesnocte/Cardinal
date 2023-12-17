using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands;

public interface ISlashCommand
{
    public Task Run(SocketSlashCommand command);
    public CommandInfo CommandInfo();
}