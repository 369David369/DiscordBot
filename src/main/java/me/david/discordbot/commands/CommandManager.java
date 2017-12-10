package me.david.discordbot.commands;

import me.david.discordbot.commands.cmds.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandManager {

    private ArrayList<Command> commands = new ArrayList<>();

    public CommandManager(){
        commands.clear();
        commands.add(new Help());
        commands.add(new Invite());
        commands.add(new Ping());
        commands.add(new Joke());
        commands.add(new AddJoke());
        commands.add(new Uptime());
        commands.add(new Avater());
        commands.add(new JokeCount());
    }

    public Command getCommandbyNameorAlias(String name){
        for(Command command : commands) {
            if (command.name.equals(name))
                return command;
            for(String alias : command.aliases)
                if(name.equals(alias))
                    return command;
        }
        return null;
    }

    public void handleCommand(MessageReceivedEvent event){
        CommandContext context = new CommandContext(event);
        String label = context.rawargs[0];
        Command command = getCommandbyNameorAlias(label);
        if(command != null) command.execute(context);
        else event.getTextChannel().sendMessage("Es konnte kein Command mit dem Namen/Alias '" + label + "' gefunden werden!").queue();
    }

    public int getCommandCount(){
        return commands.size();
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

}
