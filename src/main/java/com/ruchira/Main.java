package com.ruchira;

import com.ruchira.server.HttpWebServer;
import com.ruchira.server.routes.ArtistRoute;
import com.ruchira.server.routes.CountryRoute;
import com.ruchira.services.LastFmService;

import java.util.Map;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Map<Configuration.ConfigurationKey, String> configValues = Configuration.getConfigValues();
        LastFmService lastFmService = new LastFmService(configValues);

        HttpWebServer httpWebServer = new HttpWebServer();

        httpWebServer.route("/last-fm/country", new CountryRoute(lastFmService));
        httpWebServer.route("/last-fm/artist", new ArtistRoute(lastFmService));

        httpWebServer.listen(Integer.parseInt(configValues.get(Configuration.ConfigurationKey.HTTP_PORT)));
    }
}
