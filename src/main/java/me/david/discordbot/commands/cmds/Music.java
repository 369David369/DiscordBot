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

        else if(args.length > 0 && "-m".equals(args[0]))
        {
            Music.connect(e, false);
            String input = "";
            for(int i = 0; i < args.length; i++){
                if(i != 0)
                    input += args[i] + "+";
            }

            try {
                results = Search.youtubeSearch(num, input);

                String choices = "Top 5 results for `" + input.replaceAll("\\+", " ") + "` on YouTube:\n";

                for(int i = 0; i < 5; i++)
                {
                    SearchResult choice = results.get(i);
                    choices += "\n**" + (i+1) + ":** " + choice.getTitle();
                }

                choices += "\nUse `1~5` to select the song to play. Type `c` or `cancel` to cancel this selection.";

                selecter.put(e.getGuild().getId(), e.getAuthor());

                if(e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    e.getChannel().sendMessage(choices).queue( message -> {
                        message.delete().queueAfter(60, TimeUnit.SECONDS);
                    });
                }
            } catch (IOException ioe) {
                AILogger.errorLog(ioe, e, this.getClass().getName(), "IOException at getting Youtube search result (-m).");
            } catch (IndexOutOfBoundsException ioobe) {
                e.getChannel().sendMessage(Emoji.ERROR + " No results.").queue();
                AILogger.errorLog(ioobe, e, this.getClass().getName(), "Cannot get Yt search result correctly (-m). Input: " + input);
            }
        }

        else
        {
            Music.connect(e, false);
            if("random".equals(args[0]) || "r".equals(args[0])) {
                action(new String[]{WebScraper.randomBillboardSong()}, e);
            } else if(!Global.urlPattern.matcher(args[0]).find()) {
                String input = "";
                for(int i = 0; i < args.length; i++){
                    input += args[i] + "+";
                }

                input = input.substring(0, input.length() - 1);

                try {
                    List<SearchResult> result = Search.youtubeSearch(num, input);
                    //Do it twice because sometimes it wont get result the first time
                    if(result.isEmpty())
                        result = Search.youtubeSearch(num, input);
                    player.play(result.get(0).getLink(), e.getAuthor(), AudioTrackWrapper.TrackType.NORMAL_REQUEST);
                    result.clear();
                } catch (IOException ioe) {
                    AILogger.errorLog(ioe, e, this.getClass().getName(), "IOException at getting Youtube search result.");
                } catch (IndexOutOfBoundsException ioobe) {
                    e.getChannel().sendMessage(Emoji.ERROR + " No results.").queue();
                }
            } else {
                player.play(args[0], e.getAuthor(), AudioTrackWrapper.TrackType.NORMAL_REQUEST);
            }
        }
    }
}
