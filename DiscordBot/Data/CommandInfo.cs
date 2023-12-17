using Discord;

namespace DiscordBot.Data;

public class CommandInfo
{
    public string CommandName { get; set; }
    public string CommandDescription { get; set; }
    public List<SlashCommandOptionBuilder> CommandOption { get; set; } = new();
}