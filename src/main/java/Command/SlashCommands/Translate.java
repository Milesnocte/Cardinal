package Command.SlashCommands;

import Command.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static Main.Credentials.RapidAPIKey;

public class Translate implements ISlashCommand {

    @Override
    public void run(SlashCommandEvent event) throws Exception {
        switch (event.getSubcommandName()){
            case ("korean_to_english"):
                TranslateTo(event, "ko", "en",event.getOption("text").getAsString());
                break;
            case("english_to_korean"):
                TranslateTo(event, "en", "ko", event.getOption("text").getAsString());
                break;
            case ("spanish_to_english"):
                TranslateTo(event, "es", "en",event.getOption("text").getAsString());
                break;
            case("english_to_spanish"):
                TranslateTo(event, "en", "es", event.getOption("text").getAsString());
                break;


        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {}

    @Override
    public List<String> buttons() {return Collections.emptyList();}

    @Override
    public String commandName() {
        return "translate";
    }

    public void TranslateTo(SlashCommandEvent event, String source, String target, String translateText) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("accept-encoding", "application/gzip")
                .header("x-rapidapi-key", RapidAPIKey)
                .header("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("q="+translateText+"&target="+target+"&source="+source))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseConcat = response.body().substring(response.body().indexOf("translatedText")+17,response.body().length()-5);

        event.reply("**" + source + ":** " + translateText + "\n" +
                "**" + target + ":** " + responseConcat).queue();
    }
}
