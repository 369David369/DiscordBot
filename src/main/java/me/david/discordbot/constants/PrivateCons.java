package me.david.discordbot.constants;

import me.david.discordbot.yaml.YamlConfiguration;

import java.io.File;

public final class PrivateCons {

    public static final String AUTH_TOKEN = YamlConfiguration.loadConfiguration(new File("config.yaml")).getString("token");

}
