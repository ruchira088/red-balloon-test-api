package com.ruchira.server.routes;

import com.ruchira.services.LastFmQuery;
import com.ruchira.services.LastFmService;

public class CountryRoute extends AbstractRoute
{
    public CountryRoute(LastFmService p_lastFmService)
    {
        super(p_lastFmService);
    }

    @Override
    public LastFmQuery getLastFmQuery(String p_country)
    {
        LastFmQuery lastFmQuery = new LastFmQuery(LastFmQuery.ApiMethod.GET_TOP_ARTISTS_IN_COUNTRY);
        lastFmQuery.addParameter(LastFmQuery.QueryParameter.COUNTRY, p_country);

        return lastFmQuery;
    }
}
