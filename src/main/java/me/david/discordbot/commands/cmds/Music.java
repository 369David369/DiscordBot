package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.audio.GuildPlayer;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class Music extends Command {

    public Music() {
        super("music", "Plays Music!", "!music <url/keyword>");
    }

    @Override
    public void execute(CommandContext context) {
        GuildPlayer player = DiscordBot.instance.getPlayer(context.server);
        DiscordBot.instance.getGuild(context.server).tc = context.channel;

        if(!context.user.getVoiceState().inVoiceChannel()) {
            context.channel.sendMessage("You are not in a voice channel!").queue();
            return;
        }

        if(context.args.length == 0)
            if(player.getPlayer().isPaused()) player.pauseOrPlay();
        else if(context.args.length > 0) {
            player.play(context.args[0], context.getUser());
        }
    }
}
