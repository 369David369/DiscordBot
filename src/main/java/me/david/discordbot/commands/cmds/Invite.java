package me.david.discordbot.commands.cmds;

import me.david.discordbot.Cons;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class Invite extends Command {

    public Invite() {
        super("invite", "Gives you the invite link", "!invite");
    }

    @Override
    public void execute(CommandContext context) {
        context.channel.sendMessage("You can invite me with this link: " + Cons.AUTH_URL).queue();
    }
}
