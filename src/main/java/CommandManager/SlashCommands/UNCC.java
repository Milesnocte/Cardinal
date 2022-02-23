package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class UNCC implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws IOException, InterruptedException {

        event.deferReply().queue();

        String percent = "";
        switch (event.getSubcommandName()) {
            case "sovi" -> percent = getCapacity("sovi");
            case "crown" -> percent = getCapacity("crown");
        }
        String[] rawCapacity = percent.replaceAll(" ", "").split("/");
        double occupancy = Integer.parseInt(rawCapacity[0]);
        double capacity = Integer.parseInt(rawCapacity[1]);
        occupancy = capacity - occupancy;
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(event.getSubcommandName().toUpperCase() + " - " + (int) occupancy + "/" + (int) capacity);
        double capPercent = (occupancy / capacity) * 100;
        String showCap = showCapacity(capPercent);
        embedBuilder.setFooter(event.getTimeCreated().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").withZone(ZoneId.of("America/New_York"))));
        embedBuilder.addField("capacity",showCap, true);
        event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void run(ButtonInteractionEvent event) throws Exception {

    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "uncc";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }

    private String getCapacity(String location) throws IOException {

        /* turn off annoying htmlunit warnings */
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

        String id = "";
        if(location.equals("sovi")) id = "7a9c0a24";
        if(location.equals("crown")) id = "15da3cfa";

        String percent = "";
        while (percent.equals("")) {
            WebClient client = new WebClient(BrowserVersion.BEST_SUPPORTED);
            client.getOptions().setJavaScriptEnabled(true); // enable javascript
            client.setAjaxController(new NicelyResynchronizingAjaxController());
            client.getOptions().setThrowExceptionOnScriptError(false); //even if there is error in js continue
            client.getOptions().setCssEnabled(true);
            client.getOptions().setTimeout(30000);
            client.waitForBackgroundJavaScript(3000);
            HtmlPage page = client.getPage("https://app.safespace.io/api/display/live-occupancy/" + id);
            percent = page.querySelector("span#capacity.text-lg").asNormalizedText();
            client.close();
        }
        return percent;

    }

    private String showCapacity(double capPercent) {
        String showCap = null;
        if(capPercent == 0){
            showCap = "\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent > 0 && capPercent < 10) {
            showCap = "\u2593\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent < 20) {
            showCap = "\u2593\u2593\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent < 30) {
            showCap = "\u2593\u2593\u2593\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent < 40) {
            showCap = "\u2593\u2593\u2593\u2593\u2591\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent <= 50) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2591\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent <= 60) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2591\u2591\u2591\u2591\u2591";
        } else if(capPercent <= 70) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2591\u2591\u2591\u2591";
        } else if(capPercent <= 80) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2591\u2591\u2591";
        } else if(capPercent <= 90) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2591\u2591";
        } else if(capPercent <= 100) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2591";
        } else if(capPercent == 100) {
            showCap = "\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593";
        }
        return showCap;
    }
}
