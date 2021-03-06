package Main;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.jetbrains.annotations.NotNull;

public class ScheduledTask extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR, 7);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        refreshVCText(event);
                    }
                },
                date.getTime(),
                1000 * 60 * 60 * 24
        );
    }

    public void refreshVCText(Event event){
        event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).createCopy().queue();

        try { TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) { }

        event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).delete().queue();
    }
}