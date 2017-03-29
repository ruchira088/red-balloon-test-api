package com.ruchira;

import com.ruchira.server.HttpWebServer;
import com.ruchira.server.routes.ArtistRoute;
import com.ruchira.server.routes.CountryRoute;
import com.ruchira.services.LastFmQuery;
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

        httpWebServer.listen(8000);

//        LastFmQuery lastFmQuery = new LastFmQuery(LastFmQuery.ApiMethod.GET_ARTIST);
//        lastFmQuery.addParameter(LastFmQuery.QueryParameter.ARTIST, "cher");
//
//
//        System.out.println(lastFmService.getUrl(lastFmQuery));
    }
}
