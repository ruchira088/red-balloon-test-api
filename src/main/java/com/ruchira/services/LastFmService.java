package com.ruchira.services;

import com.ruchira.Configuration;
import com.ruchira.HttpClient;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LastFmService
{
    private final String m_apiKey;
    private final String m_hostName;
    private final String m_apiVersion;

    public LastFmService(Map<Configuration.ConfigurationKey, String> p_configValues)
    {
        m_apiKey = p_configValues.get(Configuration.ConfigurationKey.API_KEY);
        m_hostName = p_configValues.get(Configuration.ConfigurationKey.HOST_NAME);
        m_apiVersion = p_configValues.get(Configuration.ConfigurationKey.URL_PATH);
    }

    public CompletableFuture<InputStream> perform(LastFmQuery p_lastFmQuery)
    {
        HttpClient httpClient = new HttpClient();
        return httpClient.getInputStream(getUrl(p_lastFmQuery));
    }

    public String getUrl(LastFmQuery p_lastFmQuery)
    {
        p_lastFmQuery.addParameter(LastFmQuery.QueryParameter.API_KEY, m_apiKey);
        return m_hostName + "/" +  m_apiVersion + "?" + p_lastFmQuery.getQuery();
    }
}
