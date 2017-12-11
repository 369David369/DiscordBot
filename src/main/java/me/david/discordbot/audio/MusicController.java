package me.david.discordbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.david.discordbot.DiscordBot;
import me.david.discordbot.GuildWrapper;
import me.david.discordbot.commands.CommandContext;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class MusicController {

    public static void connect(CommandContext context) {
        try {
            GuildWrapper guild = DiscordBot.instance.getGuild(context.server);
            GuildPlayer player = DiscordBot.instance.getPlayer(context.server);
            VoiceChannel vc = context.user.getVoiceState().getChannel();
            Member self = context.server.getSelfMember();

            if(!context.user.getVoiceState().inVoiceChannel()) {
                context.channel.sendMessage("You must connect to a voice channel first!").queue();
                return;
            }
            if(self.getVoiceState().inVoiceChannel() && !vc.getId().equals(self.getVoiceState().getChannel().getId()) && player.isPlaying()) {
                context.channel.sendMessage("Already playing songs!").queue();
                return;
            }
            if(vc.getUserLimit() != 0 &&
                    vc.getUserLimit() <= vc.getMembers().size()) {
                context.channel.sendMessage("Cannot connect to channel due to the user limit!").queue();
                return;
            }

            guild.tc = context.channel;
            guild.vc = vc;
            if(guild.guildPlayer.connect(vc)){
                context.channel.sendMessage("Joined Voice Channel!").queue();
            }
        } catch (PermissionException pe) {
            context.channel.sendMessage("I don't have the access to join!").queue();
        }
    }

    public static void disconnect(CommandContext context) {
        VoiceChannel vc = context.user.getVoiceState().getChannel();
        if(context.server.getSelfMember().getVoiceState().getChannel() == null) {
            context.channel.sendMessage("I am not in a voice channel!").queue();
            return;
        }
        AudioPlayer player = DiscordBot.instance.getPlayer(context.server).getPlayer();
        Member self = context.server.getSelfMember();
        if(self.getVoiceState().inVoiceChannel()
                && !self.getVoiceState().getChannel().getId().equals(vc.getId())
                && player.getPlayingTrack() != null
                && !player.isPaused() ) {
            context.channel.sendMessage(" Already playing songs!").queue();
            return;
        }
        GuildWrapper guild = DiscordBot.instance.getGuild(context.server);
        guild.guildPlayer.disconnect();
        context.channel.sendMessage("Left Voice Channel!").queue();
    }
}
