package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class JokeCount extends Command {

    public JokeCount() {
        super("jokecount", "Return the Number of Jokes", "!jokecount");
    }

    @Override
    public void execute(CommandContext context) {
        DiscordBot.instance.asyncconnection.getJokeCount((count) -> context.channel.sendMessage("The Number of Jokes is: " + count).queue());
    }
}
