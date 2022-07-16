package Main;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.Timer;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ScheduledTask extends ListenerAdapter
{
    public ScheduledTask(final ReadyEvent event) {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                if (minute % 10 == 0) {
                    try {
                        new FetchUNCC().screenshot();
                        int users = 0;
                        for (final Guild guild : event.getJDA().getGuilds()) {
                            users += guild.getMemberCount();
                        }
                        event.getJDA().getPresence().setActivity(Activity.watching( users + "users"));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.schedule(tt, 1000L, 60000L);
    }
}
