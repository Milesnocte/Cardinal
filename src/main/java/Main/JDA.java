package Main;

import Listeners.BotEventsListener;
import Listeners.CheatManager;
import Listeners.MessageListener;
import Listeners.VoiceChannelListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class JDA extends ListenerAdapter implements EventListener {
    public static void main(String[] args) throws Exception {

        JDABuilder jda = JDABuilder.createDefault("Nzc2NjQzNjczMjY1MDEyNzY2.X633yQ.CzjTT7i6AnLmWsHrVoutbfxUM70");

        jda.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        jda.setMemberCachePolicy(MemberCachePolicy.ALL);
        jda.setChunkingFilter(ChunkingFilter.ALL);

        jda.addEventListeners(new VoiceChannelListener());
        jda.addEventListeners(new BotEventsListener());
        jda.addEventListeners(new MessageListener());
        jda.addEventListeners(new CheatManager());
        jda.addEventListeners(new ReactRoles());
        jda.addEventListeners(new CCIEvents());
        jda.setActivity(Activity.watching("for Cheaters"));

        // build the bot
        jda.build().awaitReady();

        System.out.println("https://discord.com/api/oauth2/authorize?client_id=" + "776643673265012766" + "&permissions=268446736&scope=bot");

        DataBase db = new DataBase();
        db.databaseCycle();

    }
}
