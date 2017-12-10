package me.david.discordbot.commands.cmds;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.commands.Command;
import me.david.discordbot.commands.CommandContext;

public class Help extends Command {


    public Help() {
        super("help", "Provides a Help", "help [-/cmd]", new String[]{"?", "hilfe"});
    }

    @Override
    public void execute(CommandContext context) {
        if(context.args.length == 0) {
            StringBuilder message = new StringBuilder();
            message.append("Do !help [cmd] for Specific Command Info\n");
            message.append("List of commands [").append(DiscordBot.instance.commandmanager.getCommandCount()).append("]\n");
            for(Command cmd : DiscordBot.instance.commandmanager.getCommands())
                message.append(cmd.name).append(" -> ").append(cmd.descripton).append("\n");
            message.append("List of commands [").append(DiscordBot.instance.commandmanager.getCommandCount()).append("]");
            context.channel.sendMessage(message.toString()).queue();
        }else if(context.args.length == 1){
            Command command = DiscordBot.instance.commandmanager.getCommandbyNameorAlias(context.args[0]);
            if(command == null){
                context.channel.sendMessage("Command '" + context.args[0] + "' not found!").queue();
                return;
            }
            StringBuilder message = new StringBuilder();
            message.append("----[").append(command.name).append("]----\n");
            message.append("Usage: ").append(command.usage).append("\n");
            message.append("Description: ").append(command.descripton).append("\n");
            StringBuilder builder = new StringBuilder();
            for(String alias : command.aliases)
                builder.append(alias).append(", ");
            String alias = builder.toString();
            if(alias.endsWith(", ")) alias = alias.substring(0, aliases.length-2);
            message.append("Aliases[").append(command.aliases.length).append("]: ").append(alias).append("\n");
            message.append("----[").append(command.name).append("]----");
            context.channel.sendMessage(message.toString()).queue();
        }else error(context);
    }
}
