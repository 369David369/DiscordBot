package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.GuildWrapper;
import me.david.discordbot.audio.GuildPlayer;
import me.david.discordbot.audio.MusicController;
import me.david.discordbot.audio.Track;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;
import me.david.discordbot.util.StringUtil;

public class Music extends Command {

    public Music() {
        super("music", "Plays Music!", "!music <url/skip/next/disconnect/leave/pause/play/clear/list/queune/join/move/jump/setposition/stop> <volume>");
    }

    @Override
    public void execute(CommandContext context) {
        GuildPlayer player = DiscordBot.instance.getPlayer(context.server);
        GuildWrapper guild = DiscordBot.instance.getGuild(context.server);
        DiscordBot.instance.getGuild(context.server).tc = context.channel;

        if(!context.user.getVoiceState().inVoiceChannel()) {
            context.channel.sendMessage("You are not in a voice channel!").queue();
            return;
        }
        player.setTc(context.channel);
        if(context.args.length == 1)
            switch (context.args[0]) {
                case "skip":
                case "next":
                    player.nextTrack();
                    break;
                case "disconnect":
                case "leave":
                    player.disconnect();
                    break;
                case "pause":
                    player.setPaused(true);
                    break;
                case "play":
                    player.setPaused(false);
                    break;
                case "clear":
                    player.clearQueue();
                    context.channel.sendMessage("Queune cleared!").queue();
                    break;
                case "list":
                case "queune":
                    StringBuilder builder = new StringBuilder();
                    int count  = 0;
                    for(Track track : player.getQueue()) {
                        builder.append('\'').append(track.getTrack().getInfo().title).append("' ").append('(').append(StringUtil.fromTime(track.getTrack().getDuration())).append(')').append(" from '").append(track.getTrack().getInfo().author).append("' requested by '").append(track.getRequester().getAsMention()).append("\n");
                        count++;
                        if(count == 20){
                            builder.append("And ").append(player.getQueue().size()-count).append(" other!");
                            break;
                        }
                    }
                    if(!player.getQueue().isEmpty()) context.channel.sendMessage(builder.toString()).queue();
                    break;
                case "join":
                case "move":
                    if(!context.user.getVoiceState().inVoiceChannel()) {
                        context.channel.sendMessage("You must connect to a voice channel first!").queue();
                        return;
                    }
                    guild.vc = context.user.getVoiceState().getChannel();
                    if(guild.guildPlayer.connect(context.user.getVoiceState().getChannel())){
                        context.channel.sendMessage("Joined Voice Channel!").queue();
                    }else context.channel.sendMessage("Already connected to this Voice Channel!").queue();
                    break;
                case "stop":
                    player.stopPlayer();
                    context.channel.sendMessage("Stopped song and cleaned Queune").queue();
                    break;
                default:
                    MusicController.connect(context);
                    player.play(context.args[0], context.getUser());
                    break;
            }
        else if(context.args.length == 2)
            switch (context.args[0].toLowerCase()){
                case "volume":
                    if(!StringUtil.isNumber(context.args[1])) {context.channel.sendMessage("Volume should be an an Number").queue();return;}
                    int volume = Integer.valueOf(context.args[1]);
                    if(volume < 0 || volume > 100) {context.channel.sendMessage("Volume should be a percentage").queue();return;}
                    player.getPlayer().setVolume(volume);
                    context.channel.sendMessage("Volume set to " + volume).queue();
                    break;
                case "jump":
                case "setposition":
                    if(context.args[1].contains(":")){
                        long time = StringUtil.toLong(context.args[1]);
                        if(time == -1){
                            context.channel.sendMessage("Timeformat is invalid use 2:20(m, s) or 2:2:2(h, m, s) or 22(this is ms) you can also use 0 before(02:02:02)!").queue();
                            return;
                        }
                        context.channel.sendMessage("Time set to '" + context.args[1] + "'!").queue();
                        player.jump(time);
                    }else {
                        if(StringUtil.isLong(context.args[1])) {
                            player.jump(Long.valueOf(context.args[1]));
                        }else context.channel.sendMessage("The time is no number!").queue();
                    }
                    break;
                default:
                    error(context);
                    break;
            }
        else error(context);
    }
}
