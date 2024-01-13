namespace DiscordBot.Data.Models;

public class Server
{
    public string guild_id { get; set; }
    public string guild_name { get; set; }
    public string? log_channel { get; set; }
    public string? chat_logs { get; set; }
    public string? star_emote { get; set; }
    public string? star_channel { get; set; }
    public int? star_count { get; set; }
}