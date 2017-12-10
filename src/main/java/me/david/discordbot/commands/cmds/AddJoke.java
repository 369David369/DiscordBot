package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;
import me.david.discordbot.database.objects.DatabaseJoke;

public class AddJoke extends Command {

    public AddJoke() {
        super("addjoke", "Adds a Joke to the JokeList", "!joke [joke]");
    }

    @Override
    public void execute(CommandContext context) {
        DiscordBot.instance.asyncconnection.addJoke(new DatabaseJoke(context.user.getUser().getId(), context.argstring), () -> context.channel.sendMessage("Joke wurde hinzugefügt!").queue());
    }
}
