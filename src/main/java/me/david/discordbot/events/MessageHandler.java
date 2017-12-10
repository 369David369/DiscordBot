package me.david.discordbot.events;

import me.david.discordbot.Cons;
import me.david.discordbot.DiscordBot;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;

public class MessageHandler extends ListenerAdapter {

    /* Last Message Times key=user value=time */
    private HashMap<String, Long> spam = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(event.getChannel().getJDA().getSelfUser().getId()))return;
        final String id = event.getAuthor().getId();
        switch (event.getChannelType()) {
            case TEXT:
                if(event.getMessage().getRawContent().startsWith("!"))
                    if(spam.containsKey(id) && System.currentTimeMillis()-spam.get(id) < 750)
                        event.getChannel().sendMessage("Please wait " + (750-(System.currentTimeMillis()-spam.get(id))) + "ms util the next command " + event.getAuthor().getAsMention() + "!").queue();
                    else DiscordBot.instance.commandmanager.handleCommand(event);
                break;
            case PRIVATE:
                event.getChannel().sendMessage("Du kannst mich nicht privat Anschreiben!\n" +
                        "Aber du kannst mich hiermit einladen: " + Cons.AUTH_URL).queue();
                break;
        }
        spam.put(id, System.currentTimeMillis());
    }

    @Override
    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
        Member member = event.getGuild().getMember(event.getUser());
        if(spam.containsKey(member.getUser().getId()) && member.getOnlineStatus() == OnlineStatus.OFFLINE)
            spam.remove(member.getUser().getId());
    }
}
