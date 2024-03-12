using System.Data;
using Dapper;
using Discord;
using Discord.WebSocket;
using DiscordBot.Data.Models;

namespace DiscordBot.Bot.Interactions;

public class Guild
{
    private static DiscordSocketClient _socketClient;
    
    public Guild(DiscordSocketClient client)
    {
        try
        {
            _socketClient = client;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }
    
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

    public static async Task Audit(SocketAuditLogEntry auditLog, SocketGuild guild)
    {
        List<string> actions = new List<string>() { "Ban", "Kick", "Unban" };
        var context = new AppDBContext();
        var guildId = guild.Id;
        string action = "";
        SocketUser mod = auditLog.User;
        IUser user = null;
            
        Server server = await context.Connection()
            .QuerySingleAsync<Server>($"SELECT * FROM servers WHERE guild_id = '{guildId}' LIMIT 1");
        
        if(server.log_channel == null) return;
        if(!actions.Contains(auditLog.Action.ToString())) return;
        EmbedBuilder embedBuilder = new EmbedBuilder();

        switch (auditLog.Action.ToString())
        {
            case "Ban":
                user = await ((SocketBanAuditLogData)auditLog.Data).Target.GetOrDownloadAsync();
                action = "Banned";
                embedBuilder.Color = Color.Red;
                break;
            case "Unban":
                user = await ((SocketUnbanAuditLogData)auditLog.Data).Target.GetOrDownloadAsync();
                action = "Unbanned";
                embedBuilder.Color = Color.Orange;
                break;
            case "Kick":
                user = await ((SocketKickAuditLogData)auditLog.Data).Target.GetOrDownloadAsync();
                action = "Kicked";
                embedBuilder.Color = Color.Red;
                break;
        }
      
        embedBuilder.Title = $"User {action}";
        embedBuilder.ThumbnailUrl = user.GetDisplayAvatarUrl();

        List<EmbedFieldBuilder> fields = new List<EmbedFieldBuilder>();
        fields.Add(new EmbedFieldBuilder()
        {
            Name = "User",
            Value = user.GlobalName ?? user.Username,
            IsInline = false
        });
        fields.Add(new EmbedFieldBuilder()
        {
            Name = "Reason",
            Value = auditLog.Reason ?? "None",
            IsInline = false
        });
        embedBuilder.Fields = fields;

        embedBuilder.Footer = new EmbedFooterBuilder() { Text = $"Id: {user.Id} | Bot: {user.IsBot}" };
        await _socketClient.GetGuild(Convert.ToUInt64(server.guild_id))
            .GetTextChannel(Convert.ToUInt64(server.log_channel))
            .SendMessageAsync(embed: embedBuilder.Build());
    }

    public static async Task UserLeft(SocketGuild guild, SocketUser user)
    {
        var context = new AppDBContext();
            
        Server server = await context.Connection()
            .QuerySingleAsync<Server>($"SELECT * FROM servers WHERE guild_id = '{guild.Id.ToString()}' LIMIT 1");
        
        if(server.log_channel == null) return;
        
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.Description = $"<:exit:918599099484373042> {user.GlobalName ?? user.Username} left";
        embedBuilder.Footer = new EmbedFooterBuilder() { Text = $"Total Members: {string.Format($"{guild.MemberCount:n0}")}" };
        embedBuilder.Color = Color.Red;
        await _socketClient.GetGuild(Convert.ToUInt64(server.guild_id))
            .GetTextChannel(Convert.ToUInt64(server.log_channel))
            .SendMessageAsync(embed: embedBuilder.Build());
    }

    public static async Task UserJoined(SocketGuildUser user)
    {
        var context = new AppDBContext();
            
        Server server = await context.Connection()
            .QuerySingleAsync<Server>($"SELECT * FROM servers WHERE guild_id = '{user.Guild.Id.ToString()}' LIMIT 1");
        
        if(server.log_channel == null) return;

        TimeSpan difference = DateTime.UtcNow.Subtract(user.CreatedAt.DateTime);
        
        string createdAt = difference.Days switch
        {
            0 => difference.Hours switch
            {
                0 => (difference.Minutes == 1 ? $"{difference.Minutes} minute ago" : $"{difference.Minutes} minutes ago"),
                1 => $"{difference.Hours} hour ago",
                _ => $"{difference.Hours} hours ago"
            },
            < 2 => $"1 day ago",
            < 30 => $"{difference.Days} days ago",
            < 60 => $"1 month ago",
            < 365 => $"{difference.Days / 30} months ago",
            < 730  => $"1 year ago",
            _ => $"{difference.Days / 365} years ago"
        };
        
        EmbedBuilder embedBuilder = new EmbedBuilder();
        List<EmbedFieldBuilder> fields = new List<EmbedFieldBuilder>();
        embedBuilder.Description = $"<:entry:918599108502102036> {user.GlobalName ?? user.Username} joined";
        fields.Add(new EmbedFieldBuilder()
        {
            Name = "Created",
            Value = createdAt,
            IsInline = false
        });
        embedBuilder.Fields = fields;
        embedBuilder.Color = Color.Green;
        embedBuilder.Footer = new EmbedFooterBuilder() { Text = $"Total Members: {string.Format($"{user.Guild.MemberCount:n0}")}"};
        await _socketClient.GetGuild(Convert.ToUInt64(server.guild_id))
            .GetTextChannel(Convert.ToUInt64(server.log_channel))
            .SendMessageAsync(embed: embedBuilder.Build());
    }
}