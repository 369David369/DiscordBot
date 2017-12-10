package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;
import me.david.discordbot.util.StringUtil;

public class Uptime extends Command {

    public Uptime() {
        super("uptime", "Returns the Time since the Bot is running", "!uptime");
    }

    @Override
    public void execute(CommandContext context) {
        final long delay = System.currentTimeMillis()-DiscordBot.instance.START_TIME;
        context.channel.sendMessage("Krüger running for " + StringUtil.fromTime(delay) + "(" + (delay) + "ms)").queue();
    }
}
