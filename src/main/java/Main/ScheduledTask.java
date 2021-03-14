package Main;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.jetbrains.annotations.NotNull;

public class ScheduledTask extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Timer timer = new Timer();
        TimerTask tt = new TimerTask(){
            public void run(){

                Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
                int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day, from 0 to 23

                if(hour == 7){
                    event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).createCopy().queue();

                    try { TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) { }

                    event.getJDA().getGuildById("433825343485247499").getTextChannelsByName("vc-text", true).get(0).delete().queue();

                    event.getJDA().getGuildById("433825343485247499").getTextChannelById("582399042240118814").sendMessage("VC-Text purged").queue();
                }
            }
        };
        timer.schedule(tt, 1000, 1000*60);
    }

}