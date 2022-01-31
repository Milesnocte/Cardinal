package Main;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScheduledTask extends ListenerAdapter  {

    public ScheduledTask(ReadyEvent event){

        Timer timer = new Timer();
        TimerTask tt = new TimerTask(){
            public void run(){

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day
                int minute = cal.get(Calendar.MINUTE);//get the minute of the hour
                if(hour == 7 && minute == 0){
                    for(Guild guild : event.getJDA().getGuilds()) {
                        try {
                            guild.getTextChannelsByName("vc-text", true).get(0).createCopy().queue();
                            TimeUnit.SECONDS.sleep(1);
                            guild.getTextChannelsByName("vc-text", true).get(0).delete().queue();
                        } catch (Exception e) {
                            System.out.println("Server doesnt have a VC-Text");
                        }
                    }
                }
            }
        };
        timer.schedule(tt, 1000, 1000*60);
    }

}