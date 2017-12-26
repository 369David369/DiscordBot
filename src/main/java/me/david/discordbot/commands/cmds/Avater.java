package me.david.discordbot.commands.cmds;

import me.david.discordbot.constants.Cons;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;

public class Avater extends Command {

    public Avater() {
        super("avater", "Givs you the Avater of a User!", "avater [-/id/mention]");
    }

    @Override
    public void execute(CommandContext context) {
        User user = null;

        if(context.args.length == 0) user = context.getUser();
        else if(context.args[0].length() == Cons.USER_ID_LENGTN) user = context.jda.getUserById(context.args[0]);
        else if(context.message.getMentionedUsers().size() != 0) user = context.message.getMentionedUsers().get(0);

        if(user == null) {
            context.channel.sendMessage("Count not find user '" + context.args[0] + "'!").queue();
            return;
        }
        context.channel.sendMessage(new EmbedBuilder()
                .setDescription("Avater from " + user.getAsMention())
                .setImage(user.getEffectiveAvatarUrl()).build()).queue();
    }
}
