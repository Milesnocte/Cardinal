package Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScheduledTask extends ListenerAdapter  {

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Timer timer = new Timer();
        TimerTask tt = new TimerTask(){
            public void run(){

                Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
                int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day
                int minute = cal.get(Calendar.MINUTE);//get the minute of the hour

                if(hour == 7 && minute == 0){
                    Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).createCopy().queue();

                    try { TimeUnit.SECONDS.sleep(1);
                    }catch (Exception ignored){}

                    Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelsByName("vc-text", true).get(0).delete().queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.GREEN);
                    embed.setDescription("VC-Text automatically purged!");

                    Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("433825343485247499")).getTextChannelById("582399042240118814")).sendMessage(embed.build()).queue();
                }
            }
        };
        timer.schedule(tt, 1000, 1000*60);
    }

}