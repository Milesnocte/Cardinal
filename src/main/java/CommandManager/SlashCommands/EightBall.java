package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EightBall implements ISlashCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        Random rng = new Random();
        int roll = rng.nextInt(15);
        String[] answers = new String[]{"It is certain", "Without a doubt", "You may rely on it", "Yes definitely", "It is decidedly so",
                "As I see it, yes", "Most likely", "Yes", "Outlook good", "Signs point to yes", "Reply hazy try again", "Better not tell you now",
                "Ask again later", "Cannot predict now", "Concentrate and ask again", "Don’t count on it", "Outlook not so good",
                "My sources say no", "Very doubtful", "My reply is no"};

        event.reply("\uD83D\uDE4B: " + event.getOption("question").getAsString() + "\n\uD83C\uDFB1: " + answers[roll])
                .allowedMentions(Collections.singleton(Message.MentionType.USER)).queue();
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
        return "eightball";
    }

    @Override
    public Boolean enabled() {
        return true;
    }

    @Override
    public String description() {
        return null;
    }
}
