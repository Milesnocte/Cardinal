using System.Data;
using Dapper;
using Discord;
using Discord.WebSocket;
using Models;
using Serilog;

namespace DiscordBot.Bot.Interactions;

public class Messages
{
    private static Dictionary<string, List<string>> _xpDictionary = new();
    private DiscordSocketClient _socketClient;
    private AppDBContext _context = new();
    private Random _rng = new();
    private Timer _processMessages;
    private Timer _processRoles;
    
    public Messages(DiscordSocketClient client)
    {
        try
        {
            _socketClient = client;
            _processMessages = new Timer(ProcessMessages, null, TimeSpan.Zero, TimeSpan.FromMinutes(1));
            _processRoles = new Timer(DistributeRoles, null, TimeSpan.Zero, TimeSpan.FromMinutes(15));
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            Log.Error($"[Messages] {e}");
        }
    }
    
    public static Task MessageReceived(SocketMessage arg)
    {
        try
        {
            if (arg.Author.IsBot) return Task.CompletedTask;

            var guildId = arg.GetJumpUrl().Split("/")[4];

            if (!_xpDictionary.ContainsKey(guildId)) _xpDictionary[guildId] = new List<string>();
            if (_xpDictionary[guildId].Contains(arg.Author.Id.ToString())) return Task.CompletedTask;

            _xpDictionary[guildId].Add(arg.Author.Id.ToString());

            return Task.CompletedTask;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            Log.Logger.Error($"[Messages] {e}");
            return Task.CompletedTask;
        }
    }

    private async void ProcessMessages(object? state)
    { 
        try
        {
            Dictionary<string, List<string>> processList = new(_xpDictionary);
            _xpDictionary.Clear();

            foreach (var guild in processList.Keys)
            {
                foreach (var user in processList[guild])
                {
                    int xp = _rng.Next(7, 30);
             
                    DynamicParameters parameters = new DynamicParameters();
                    parameters.Add("p_member", user);
                    parameters.Add("p_server", guild);
                    parameters.Add("p_xp", xp);
                    await _context.Connection().ExecuteAsync("SELECT insert_or_update_xp(@Member, @Server, @Xp)", new {Member = user, Server = guild, Xp = xp});
                }
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }

    private async void DistributeRoles(Object? state)
    {
        try
        {
            var guild = _socketClient.GetGuild(776380239961260052);
            if (guild == null) return;
            
            List<Levels> members = (await _context.Connection().QueryAsync<Levels>("SELECT * FROM get_guild_users_xp(@Server)", new{Server = "776380239961260052"}, commandType: CommandType.Text)).AsList();

            //Ranks
            ulong tadpole = 1114237294988230808;
            ulong froglet = 857136982692724756;
            ulong frog = 857137043355992106;
            ulong bullfrog = 857137261706346537;
            ulong suitedFrog = 857137344318668810;
            ulong rainbowFrog = 882488807776202762;

            foreach (var member in members)
            {
                var user = guild.GetUser(Convert.ToUInt64(member.Member));
                if (user == null) continue;
                
                List<ulong> rolesToAdd = new();
                int level = GetLevel(member.Xp);

                rolesToAdd.Add(tadpole);
                if (level >= 5) rolesToAdd.Add(froglet);
                if (level >= 15) rolesToAdd.Add(frog);
                if (level >= 30) rolesToAdd.Add(bullfrog);
                if (level >= 50) rolesToAdd.Add(suitedFrog);
                if (level >= 75) rolesToAdd.Add(rainbowFrog);
                
                await user.ModifyAsync(x => x.RoleIds = user.Roles.Select(r => r.Id)
                    .Except(new[] { user.Guild.Id })
                    .Concat(rolesToAdd)
                    .Distinct()
                    .ToList());
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }

    private int GetLevel(int xp)
    {
        try
        {
            int level = 0;
            int xpfornext = 0;

            for (int i = 0; i < 10000; i++)
            {
                xpfornext += (int)(5 * Math.Pow(i, 2) + 50 * i + 100);
                if (xpfornext > xp)
                {
                    level = i + 1;
                    break;
                }
            }

            return level;
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Messages] {e}");
            return 0;
        }
    }
}