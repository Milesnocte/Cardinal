using Discord;
using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class Whois : ISlashCommand
{
    
    public static string Name = "whois";

    public Task Run(SocketSlashCommand command)
    {
        SocketGuildUser user;
        if (command.Data.Options.Count != 0)
        {
            user = (SocketGuildUser) command.Data.Options.First().Value;
        }
        else
        {
            user = (SocketGuildUser) command.User;
        }

        string roles = "";
        int counter = 1;
        foreach (var socketRole in user.Roles.OrderBy(x => x.Position).OrderDescending())
        {
            if(socketRole.IsEveryone) continue;
            roles += (socketRole.Mention);
            
            if (counter % 2 == 0)
            {
                roles += "\n";
            }
            else if (socketRole.Name.Length > 10)
            {
                roles += "\n";
            }
            counter++;
        }

        List<EmbedFieldBuilder> fields = new List<EmbedFieldBuilder>();
        
        fields.Add( new EmbedFieldBuilder()
        {
            Name = "Created",
            Value = user.CreatedAt.ToString("MMM dd yyyy"),
            IsInline = true
        });
        fields.Add( new EmbedFieldBuilder()
        {
            Name = "Joined",
            Value = user.JoinedAt.Value.ToString("MMM dd yyyy"),
            IsInline = true
        });

        if (user.Nickname is null)
        {
            fields.Add( new EmbedFieldBuilder()
            {
                Name = "Nickname",
                Value = user.Username,
                IsInline = false
            });
        }
        else
        {
            fields.Add( new EmbedFieldBuilder()
            {
                Name = "Nickname",
                Value = user.Nickname,
                IsInline = false
            });
        }
        
        fields.Add( new EmbedFieldBuilder()
        {
            Name = "Id",
            Value = user.Id,
            IsInline = true
        });
        
        if (roles.Length < 5)
        {
            fields.Add( new EmbedFieldBuilder()
            {
                Name = "Roles",
                Value = "None",
                IsInline = false
            });
        }
        else
        {
            fields.Add( new EmbedFieldBuilder()
            {
                Name = "Roles",
                Value = roles,
                IsInline = false
            });
        }
        
        EmbedBuilder embedBuilder = new EmbedBuilder
        {
            Title = $"{user.GlobalName}",
            ThumbnailUrl = user.GetAvatarUrl(size: 4096),
            Fields = fields
        };
        command.RespondAsync(embed: embedBuilder.Build());
        
        return Task.CompletedTask;
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Get info about a user"
        };
        commandInfo.CommandOption.Add(
            new SlashCommandOptionBuilder()
            {
                Name = "user",
                Type = ApplicationCommandOptionType.User,
                Description = "User to get",
                IsRequired = false
            });
        return commandInfo;
    }
}