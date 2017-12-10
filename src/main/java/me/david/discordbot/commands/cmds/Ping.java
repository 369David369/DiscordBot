package me.david.discordbot.commands.cmds;

import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class Ping extends Command {

    public Ping() {
        super("ping", "Shows the Bots Ping", "!ping");
    }

    @Override
    public void execute(CommandContext context) {
        context.channel.sendMessage("Ping: " + context.channel.getJDA().getPing() + "ms!").queue();
    }
}
