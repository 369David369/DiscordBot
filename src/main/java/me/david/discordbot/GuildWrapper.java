package me.david.discordbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.david.discordbot.audio.GuildPlayer;
import me.david.discordbot.audio.SendHandler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class GuildWrapper {

    public String guildId;

    private final JDA jda;
    public final Guild guild;

    public VoiceChannel vc;
    public TextChannel tc;

    private final AudioPlayer player;
    public final GuildPlayer guildPlayer;

    public GuildWrapper(JDA jda, String guildId) {
        this.jda = jda;
        this.guild = jda.getGuildById(guildId);
        this.guildId = guildId;
        player = DiscordBot.instance.audiomanager.createPlayer();
        guildPlayer = new GuildPlayer(player, tc);
    }

    public SendHandler getSendHandler() {
        return new SendHandler(player);
    }


}
