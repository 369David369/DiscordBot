package me.david.discordbot;

import java.util.regex.Pattern;

public final class Cons {

    public static final String TOKEN = "Mzg5Mzg0NDM5Mzg3NTIxMDI2.DQ62OQ.FrWqfwxAenXIkh7ADdpnMQFygy8";
    public static final String AUTH_URL = "https://discordapp.com/api/oauth2/authorize?client_id=389384439387521026&permissions=1870134483&scope=bot";
    public static final int USER_ID_LENGTN = 18;

    public static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://([A-Za-z0-9-._~/?#\\\\[\\\\]:!$&'()*+,;=]+)$");

}
