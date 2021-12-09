import Command.CommandListener;
import Command.SlashCommandListener;
import Listener.JoinListener;
import Listeners.BotEventsListener;
import Listeners.StarBoardListener;
import Listeners.VoiceChannelListener;
import Main.Credentials;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class JDA extends ListenerAdapter implements EventListener {
    public static void main(String[] args) throws Exception {

        //Online the bot and create the listeners
        DefaultShardManagerBuilder.create(
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_EMOJIS
        ).setToken(Credentials.TOKEN)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(
                        new SlashCommandListener(),
                        new VoiceChannelListener(),
                        new BotEventsListener(),
                        new CommandListener(),
                        new JoinListener(),
                        new Giveaway(),
                        new StarBoardListener()
                )
                //Set the bot activity to "Watching for cheaters"
                .setActivity(Activity.watching("for Cheaters"))
                .setStatus(OnlineStatus.ONLINE)
                .setRawEventsEnabled(true)
                .build();

        //Print invite link to console
        System.out.println("https://discord.com/api/oauth2/authorize?client_id=" + Credentials.BOTID + "&permissions=268446736&scope=bot");

    }
}
