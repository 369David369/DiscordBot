package me.david.discordbot.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandContext {

    public String rawmessage;
    public Member user;
    public Guild server;
    public TextChannel channel;
    public Message message;
    public String[] args;
    public boolean mention;
    public String[] rawargs;
    public String argstring;
    public JDA jda;

    public CommandContext(MessageReceivedEvent event){
        jda = event.getJDA();
        mention = false;
        message = event.getMessage();
        rawmessage = message.getRawContent().substring(1);
        rawargs = rawmessage.split(" ");
        args = Arrays.copyOfRange(rawargs, 1, rawargs.length);
        StringBuilder builder = new StringBuilder();
        for(String arg : args)
            builder.append(arg).append(" ");
        argstring = builder.toString();
        if(argstring.endsWith(" ")) argstring = argstring.substring(0, argstring.length()-1);
        user = event.getMember();
        server = event.getGuild();
        channel = event.getTextChannel();
        for(User user : message.getMentionedUsers())
            if(user.getId().equals(user.getJDA().getSelfUser().getId()))
                mention = true;
    }

    public List<User> getOtherMentionedUsers() {
        if (mention) {
            List<User> mentions = new ArrayList<>(message.getMentionedUsers());
            if (!mentions.isEmpty()) {
                mentions.remove(0);
            }
            return mentions;
        } else {
            return message.getMentionedUsers();
        }
    }

    public User getUser(){
        return user.getUser();
    }

    public boolean hasArguments() {
        return args.length > 0 && !rawmessage.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandContext that = (CommandContext) o;

        if (rawmessage != null ? !rawmessage.equals(that.rawmessage) : that.rawmessage != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (server != null ? !server.equals(that.server) : that.server != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = rawmessage != null ? rawmessage.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (server != null ? server.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
