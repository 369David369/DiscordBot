package me.david.discordbot.commands;

public abstract class Command {

   public String name, descripton, usage;
   public String[] aliases;

   public Command(String name, String descripton, String usage){
       this.name = name;
       aliases = new String[]{};
       this.descripton = descripton;
       this.usage = usage;
   }

    public Command(String name, String descripton, String usage, String[] aliases) {
        this.name = name;
        this.aliases = aliases;
        this.descripton = descripton;
        this.usage = usage;
    }

    public abstract void execute(CommandContext context);

   public void error(CommandContext context){
       context.channel.sendMessage("Wrong Usage! Right Usage: " + usage).queue();
   }
}
