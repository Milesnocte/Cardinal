using System.Data;
using Dapper;
using Discord.WebSocket;

namespace DiscordBot.Bot.Interactions;

public class Guild
{
    public static async Task Joined(SocketGuild arg)
    {
        var context = new AppDBContext();

        DynamicParameters parameters = new DynamicParameters();
        parameters.Add("p_guild_id", arg.Id.ToString());
        parameters.Add("p_guild_name", arg.Name.ToString());
        parameters.Add("p_log_channel", null);
        parameters.Add("p_chat_logs", null);
        parameters.Add("p_star_emote", "⭐");
        parameters.Add("p_star_channel", null);
        parameters.Add("p_star_count", 5);
        await context.Connection().ExecuteAsync("SELECT insert_or_update_server(@p_guild_id, @p_guild_name, @p_log_channel, @p_chat_logs, @p_star_emote, @p_star_channel, @p_star_count)", parameters);
    }
    
    public static Task Left(SocketGuild arg)
    {
        // Probably dont need to do anything here
        return Task.CompletedTask;
    }

    public static async Task Updated(SocketGuild arg1, SocketGuild arg2)
    {
        var context = new AppDBContext();
        
        DynamicParameters parameters = new DynamicParameters();
        parameters.Add("p_guild_id", arg2.Id.ToString());
        parameters.Add("p_guild_name", arg2.Name.ToString());
        parameters.Add("p_log_channel", null);
        parameters.Add("p_chat_logs", null);
        parameters.Add("p_star_emote", null);
        parameters.Add("p_star_channel", null);
        parameters.Add("p_star_count", null);
        await context.Connection().ExecuteAsync("SELECT insert_or_update_server(@p_guild_id, @p_guild_name, @p_log_channel, @p_chat_logs, @p_star_emote, @p_star_channel, @p_star_count)", parameters);
    }
}