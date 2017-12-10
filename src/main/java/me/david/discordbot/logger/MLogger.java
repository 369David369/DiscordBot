package me.david.discordbot.logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class MLogger {

    private Logger logger;
    private Level level;

    public MLogger(final Level level) {
        this.level = level;
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        this.logger = root;
        FileHandler txt = null;
        try {
            String filename = new SimpleDateFormat("dd MM yyyy").format(new Date()) + "-1";
            File logdir = new File("Logs/");
            if(!logdir.exists()) {
                logdir.mkdirs();
            }
            while(new File("Logs/" + filename + ".mcslog").exists()) {
                int next = Integer.valueOf(filename.split("-")[1]);
                next++;
                filename = filename.substring(0, filename.split("-")[0].length()) + "-" + next;
            }
            txt = new FileHandler("Logs/" + filename + ".mcslog");
            root.setLevel(level);
            txt.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String ret = "";
                    if(record.getLevel().intValue() >= Level.WARNING.intValue()) {
                        ret = "WARNUNG ";
                    }
                    ret += record.getLevel() + ":";
                    ret += new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(record.getMillis());
                    ret += " " + record.getMessage();
                    ret += "\r\n";
                    return ret;
                }
            });
            root.addHandler(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MLogger info(String info) {
        logger.info(info);
        System.out.println(info);
        return this;
    }

    public MLogger printbanner(String prefix){
        info(" _   __                                \n" +
                "| | / /                                \n" +
                "| |/ / _ __ _   _  ___  __ _  ___ _ __ \n" +
                "|    \\| '__| | | |/ _ \\/ _` |/ _ \\ '__|\n" +
                "| |\\  \\ |  | |_| |  __/ (_| |  __/ |   \n" +
                "\\_| \\_/_|   \\__,_|\\___|\\__, |\\___|_|   \n" +
                "                        __/ |          \n" +
                "                       |___/ ");
        return this;
    }

    public MLogger infoConsole(String info, boolean in) {
        logger.info("Console " + (in ? "<-" : "->") + " " + info);
        System.out.println(info);
        return this;
    }

    public MLogger infoLog(String message) {
        logger.info(message);
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
