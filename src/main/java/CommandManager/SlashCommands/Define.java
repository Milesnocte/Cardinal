package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class Define implements ISlashCommand {

    @Override
    public void run(SlashCommandEvent event) throws Exception {
        try {
            // Set up variables
            String responseContent;
            EmbedBuilder embed = new EmbedBuilder();
            String word = event.getOption("word_to_define").getAsString();

            // Connect to merriam
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en_US/" + word);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Get response
            int status = connection.getResponseCode();

            if (status == 200) {

                //Get output json
                responseContent = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                responseContent = responseContent.substring(1, responseContent.length() - 1);
                JSONTokener tokener = new JSONTokener(responseContent);
                JSONObject responseJSON = new JSONObject(tokener);

                embed.setTitle(word.toUpperCase() + " DEFINITION");
                for (int k = 0; k < responseJSON.getJSONArray("meanings").length(); k++) {

                    JSONObject meanings = responseJSON.getJSONArray("meanings").getJSONObject(k);
                    JSONObject definitions = meanings.getJSONArray("definitions").getJSONObject(0);

                    String definition = definitions.getString("definition");
                    try {
                        definition += "\n" + "EX: *" + definitions.getString("example") + "*";
                    } catch (Exception ignored) {
                    }

                    embed.addField("**" + meanings.getString("partOfSpeech") + "**", definition, false);
                }
                embed.setFooter("Using https://dictionaryapi.dev/");
                event.replyEmbeds(embed.build()).queue();

            } else {
                event.reply("Word not found. Error: " + status).queue();
            }


        } catch (Exception ignored) {
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {
    }

    @Override
    public List<String> buttons() {
        return Collections.emptyList();
    }

    @Override
    public String commandName() {
        return "define";
    }

    @Override
    public Boolean enabled() {
        return true;
    }
}
