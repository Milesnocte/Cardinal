using Discord;
using Discord.WebSocket;
using DiscordBot.Data;
using static Program;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Purge : ISlashCommand
{
    public static string Name = "purge";

    public Task Run(SocketSlashCommand command)
    {
        if (!GetClient().GetGuild(command.GuildId.Value).GetUser(command.User.Id).GuildPermissions.ManageMessages)
        {
            command.RespondAsync($"You do not have permission to use this command!", ephemeral: true);
            return Task.CompletedTask;
        }

       
        int num = (int)(long)command.Data.Options.First().Value;
        var channel = (ITextChannel) command.GetChannelAsync().GetAwaiter().GetResult();
        var messages = channel.GetMessagesAsync(num).FlattenAsync().GetAwaiter().GetResult();
            
        messages = messages.Where(x =>
            (DateTimeOffset.UtcNow - x.Timestamp).TotalDays < 14 && x.Flags is not MessageFlags.Ephemeral);
            
        channel.DeleteMessagesAsync(messages).GetAwaiter().GetResult();
        if (messages.Count() != 0)
        {
            command.RespondAsync($"Deleted " + messages.Count() + " message(s).", ephemeral: true);
        }
        else
        {
            command.RespondAsync($"No Messages found! You can only delete message younger than 2 weeks old.", ephemeral: true);
        }
        
        return Task.CompletedTask;
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Bulk delete messages"
        };
        commandInfo.CommandOption.Add(
            new SlashCommandOptionBuilder()
            {
                Name = "number",
                Type = ApplicationCommandOptionType.Integer,
                Description = "Number of messages to delete",
                IsRequired = false
            });
        return commandInfo;
    }
}