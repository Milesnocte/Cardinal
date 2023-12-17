using Discord;
using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Restart : ISlashCommand
{
    public static string Name = "restart";
    public async Task Run(SocketSlashCommand command)
    {
        if (!Program.GetClient().GetGuild(command.GuildId.Value).GetUser(command.User.Id).GuildPermissions.Administrator)
        {
            await command.RespondAsync($"No. :)", ephemeral: false);
            return;
        }
        
        await command.RespondAsync($"Restarting...", ephemeral: true);
        Environment.Exit(0);
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Restart the bot"
        };
        return commandInfo;
    }
}