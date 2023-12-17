using Discord;
using Discord.WebSocket;
using DiscordBot.Data;

namespace DiscordBot.Bot.SlashCommands.Commands;

public class EightBall : ISlashCommand
{
    public static string Name = "eightball";
    public async Task Run(SocketSlashCommand command)
    {
        Random rng = new Random();
        int roll = rng.Next(0,15);
        String[] answers = {"It is certain", "Without a doubt", "You may rely on it", "Yes definitely", "It is decidedly so",
                "As I see it, yes", "Most likely", "Yes", "Outlook good", "Signs point to yes", "Reply hazy try again", "Better not tell you now",
                "Ask again later", "Cannot predict now", "Concentrate and ask again", "Don’t count on it", "Outlook not so good",
                "My sources say no", "Very doubtful", "My reply is no"};

        await command.RespondAsync("\uD83D\uDE4B: " + command.Data.Options.FirstOrDefault(o => o.Name == "question" )!.Value + "\n\uD83C\uDFB1: " + answers[roll]);
    }

    public CommandInfo CommandInfo()
    {
        CommandInfo commandInfo = new CommandInfo
        {
            CommandName = Name,
            CommandDescription = "Ask the EightBall"
        };
        commandInfo.CommandOption.Add(
            new SlashCommandOptionBuilder()
            {
                Name = "question",
                Type = ApplicationCommandOptionType.String,
                Description = "Question to ask",
                IsRequired = true
            });
        return commandInfo;
    }
}