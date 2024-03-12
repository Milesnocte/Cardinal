using Discord;
using Discord.WebSocket;
using DiscordBot.Bot;
using DiscordBot.Bot.Interactions;
using DiscordBot.Bot.SlashCommands;
using Fergun.Interactive;
using Serilog;
using Serilog.Events;

class Program
{
    private static DiscordSocketClient Client;
    private static InteractiveService InteractiveService;
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
            
            var config = new DiscordSocketConfig
            {
                MessageCacheSize = 500,
                GatewayIntents = GatewayIntents.AllUnprivileged | GatewayIntents.MessageContent | GatewayIntents.GuildMembers
            };

            Client = new DiscordSocketClient(config);
            Client.Log += LogAsync;

            // Call constructors
            var commandHandler = new CommandHandler();
            var messages = new Messages(Client);
            var component = new Component(Client);
            var guild = new Guild(Client);
            
            InteractiveService = new InteractiveService(client: GetClient(),
                config: new InteractiveConfig()
                {
                    DefaultTimeout = TimeSpan.FromMinutes(10),
                    ReturnAfterSendingPaginator = true
                });
            
            await Client.LoginAsync(TokenType.Bot, Environment.GetEnvironmentVariable("BOT_TOKEN"));
            await Client.StartAsync();

            Client.Ready += new ClientReady(Client).OnClientReady;
            Client.SlashCommandExecuted += CommandHandler.SlashCommandHandler;
            Client.ButtonExecuted += Component.ButtonExecuted;
            Client.SelectMenuExecuted += Component.SelectMenuExecuted;
            Client.ReactionAdded += Reactions.ReactionAdd;
            Client.ReactionRemoved += Reactions.ReactionRemove;
            Client.MessageReceived += Messages.MessageReceived;
            Client.MessageDeleted += Messages.MessageDeleted;
            Client.MessageUpdated += Messages.MessageUpdated;
            Client.JoinedGuild += Guild.Joined;
            Client.LeftGuild += Guild.Left;
            Client.GuildUpdated += Guild.Updated;
            Client.UserLeft += Guild.UserLeft;
            Client.UserJoined += Guild.UserJoined;
            Client.AuditLogCreated += Guild.Audit;

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
    
    public static InteractiveService GetInteractiveService()
    {
        return InteractiveService;
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
