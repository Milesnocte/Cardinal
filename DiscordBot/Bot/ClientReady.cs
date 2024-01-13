using Dapper;
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

    public async Task OnClientReady()
    {
        try
        {
            var context = new AppDBContext();
            
            Timer timer = new Timer(UpdateMemberCount, null, TimeSpan.FromSeconds(0), TimeSpan.FromHours(12));
            var commandFactory = new CommandFactory(_discordClient);
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Ready] {e}");
        }
    }

    private async void UpdateMemberCount(object? state)
    {
        int usersCount = _discordClient.Guilds.Sum(discordClientGuild => discordClientGuild.MemberCount);
        string usersFormatted = string.Format($"{usersCount:n0}");
        IActivity activity = new Game($"{usersFormatted} users", ActivityType.Watching);
        await _discordClient.SetActivityAsync(activity);
    }
}