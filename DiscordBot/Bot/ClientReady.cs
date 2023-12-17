using Discord;
using Discord.WebSocket;
using DiscordBot.Bot.SlashCommands;
using Microsoft.Extensions.Logging;
using Serilog;

namespace DiscordBot.Bot;

public class ClientReady
{
    private DiscordSocketClient _discordClient;

    public ClientReady(DiscordSocketClient discordClient)
    {
        _discordClient = discordClient;
    }

    public Task OnClientReady()
    {
        try
        {
            var commandFactory = new CommandFactory(_discordClient);
            int usersCount = _discordClient.Guilds.Sum(discordClientGuild => discordClientGuild.MemberCount);
            string usersFormatted = string.Format($"{usersCount:n0}");
            IActivity activity = new Game($"{usersFormatted} users", ActivityType.Watching, ActivityProperties.None,
                null);
            return _discordClient.SetActivityAsync(activity);
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Ready] {e}");
            return Task.CompletedTask;
        }
    }
}