package com.ruchira.server.routes;

import com.ruchira.services.LastFmQuery;
import com.ruchira.services.LastFmService;

public class ArtistRoute extends AbstractRoute
{
    public ArtistRoute(LastFmService p_lastFmService)
    {
        super(p_lastFmService);
    }

    @Override
    public LastFmQuery getLastFmQuery(String p_artist)
    {
        LastFmQuery lastFmQuery = new LastFmQuery(LastFmQuery.ApiMethod.GET_ARTIST);
        lastFmQuery.addParameter(LastFmQuery.QueryParameter.ARTIST, p_artist);

        return lastFmQuery;
    }
}
