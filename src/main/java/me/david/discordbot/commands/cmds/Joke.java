package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class Joke extends Command {

    public Joke() {
        super("joke", "Provides you with a Random Joke", "!joke");
    }

    @Override
    public void execute(CommandContext context) {
        DiscordBot.instance.asyncconnection.getRandomJoke((joke) -> context.channel.sendMessage("Joke: '" + joke.getJoke() + "' from " + context.channel.getJDA().getUserById(joke.getCreator()).getAsMention()).submit());
    }
}
