using Dapper;
using Discord;
using Discord.WebSocket;
using DiscordBot.Data;
using DiscordBot.Data.Models;
using Fergun.Interactive;
using Fergun.Interactive.Pagination;
using static Program;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class LevelTop : ISlashCommand
{
    
    public static string Name = "leveltop";

    public async Task Run(SocketSlashCommand command)
    {
        var guild = GetClient().GetGuild(command.GuildId.Value);
        var cachedUserIds = guild.Users.Select(u => u.Id.ToString()).ToArray();

        var context = new AppDBContext();
        var xpUsers = (await context.Connection().QueryAsync<XpUser>(
            "SELECT * FROM levels WHERE server = @Server AND member = ANY(@UserIds) ORDER BY xp DESC FETCH FIRST 100 ROWS ONLY",
            new { Server = command.GuildId.ToString(), UserIds = cachedUserIds }
        )).ToList();

        List<IPageBuilder> pages = new List<IPageBuilder>();
        int usersPerPage = 10;
        for (int pageIndex = 0; pageIndex < xpUsers.Count; pageIndex += usersPerPage)
        {
            var pageBuilder = new PageBuilder
            {
                Title = "Top 100 Users"
            };
            
            var usersOnPage = xpUsers.Skip(pageIndex).Take(usersPerPage).ToList();

            string userListString = "";
            foreach (var xpUser in usersOnPage)
            {
                int level = 0;
                int xpfornext = 0;

                for (int i = 0; i < 10000; i++)
                {
                    xpfornext += (int)(5 * Math.Pow(i, 2) + 50 * i + 100);
                    if (xpfornext > xpUser.Xp)
                    {
                        level = i + 1;
                        break;
                    }
                }

                string rank = $"#{xpUsers.IndexOf(xpUser) + 1}".PadRight(3);
                string userXp = $"`{rank}` <@{xpUser.Member}> - Level {level}\n";
                userListString += userXp;
            }
            
            pageBuilder.AddField(new EmbedFieldBuilder()
            {
                Name = $"Top {xpUsers.IndexOf(usersOnPage.First()) + 1} - {xpUsers.IndexOf(usersOnPage.Last()) + 1}",
                Value = userListString
            });
                
            pageBuilder.WithFooter(
                $"Page {pageIndex / usersPerPage + 1}/{usersPerPage} | Requested By: {command.User.Username ?? command.User.GlobalName}");
            pages.Add(pageBuilder);
        }

        var paginator = new StaticPaginatorBuilder()
            .AddUser(command.User)
            .WithPages(pages.ToArray())
            .WithOptions(new Dictionary<IEmote, PaginatorAction>()
            {
                { new Emoji("\u23ea"), PaginatorAction.SkipToStart },
                { new Emoji("\u2b05"), PaginatorAction.Backward },
                { new Emoji("\u27a1"), PaginatorAction.Forward },
                { new Emoji("\u23e9"), PaginatorAction.SkipToEnd },
            })
            .WithFooter(PaginatorFooter.None)
            .WithActionOnTimeout(ActionOnStop.DeleteInput)
            .Build();
        await GetInteractiveService().SendPaginatorAsync(paginator, command, TimeSpan.FromMinutes(10));
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Get top 100 users"
        };
        return commandInfo;
    }
}