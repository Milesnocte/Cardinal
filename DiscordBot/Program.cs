using Discord;
using Discord.WebSocket;
using DiscordBot.Bot;
using DiscordBot.Bot.Interactions;
using DiscordBot.Bot.SlashCommands;
using Serilog;
using Serilog.Events;

class Program
{
    private static DiscordSocketClient Client;
    
    public static Task Main(string[] args)
    {
        new Program().MainAsync().GetAwaiter().GetResult();
        return Task.CompletedTask;
    }
    
    public async Task MainAsync()
    {
        try
        {
            Log.Logger = new LoggerConfiguration()
                .MinimumLevel.Verbose()
                .Enrich.FromLogContext()
                .WriteTo.Console()
                .WriteTo.PostgreSQL(
                    connectionString:Environment.GetEnvironmentVariable("DB_CONNECTION"),
                    tableName: "logs",
                    useCopy: true,
                    needAutoCreateTable: true
                )
                .CreateLogger();

            Client = new DiscordSocketClient();
            Client.Log += LogAsync;

            // Call constructors
            var commandHandler = new CommandHandler();
            var messages = new Messages(Client);
            var component = new Component(Client);
            
            Console.Write($"Token: {Environment.GetEnvironmentVariable("BOT_TOKEN")}");
            
            await Client.LoginAsync(TokenType.Bot, Environment.GetEnvironmentVariable("BOT_TOKEN"));
            await Client.StartAsync();

            Client.Ready += new ClientReady(Client).OnClientReady;
            Client.SlashCommandExecuted += CommandHandler.SlashCommandHandler;
            Client.ButtonExecuted += Component.ButtonExecuted;
            Client.SelectMenuExecuted += Component.SelectMenuExecuted;
            Client.ReactionAdded += Reactions.ReactionAdd;
            Client.ReactionRemoved += Reactions.ReactionRemove;
            Client.MessageReceived += Messages.MessageReceived;

            var users = Client.Guilds.Sum(x => x.MemberCount);
            IActivity activity = new Game($"{users} users", ActivityType.Watching, ActivityProperties.None, null);
            await Client.SetActivityAsync(activity);

            await Task.Delay(-1);
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Program] {e}");
        }
    }

    public static DiscordSocketClient GetClient()
    {
        return Client;
    }
    
    private static async Task LogAsync(LogMessage message)
    {
        var severity = message.Severity switch
        {
            LogSeverity.Critical => LogEventLevel.Fatal,
            LogSeverity.Error => LogEventLevel.Error,
            LogSeverity.Warning => LogEventLevel.Warning,
            LogSeverity.Info => LogEventLevel.Information,
            LogSeverity.Verbose => LogEventLevel.Verbose,
            LogSeverity.Debug => LogEventLevel.Debug,
            _ => LogEventLevel.Information
        };
        Log.Write(severity, message.Exception, "[{Source}] {Message}", message.Source, message.Message);
        await Task.CompletedTask;
    }
}
