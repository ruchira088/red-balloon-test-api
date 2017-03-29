package com.ruchira.services;

import com.ruchira.HttpClient;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class LastFmService
{
    private final String API_KEY_PARAMETER = "api_key";
    private final String HOST_NAME = "http://ws.audioscrobbler.com";
    private final String URL_PATH = "/2.0/";

    private final String m_apiKey;

    public LastFmService(String p_apiKey)
    {
        m_apiKey = p_apiKey;
    }

    private String getUrl(String p_queryParameters)
    {
        return HOST_NAME + URL_PATH + "?" + p_queryParameters + "&" + API_KEY_PARAMETER + "=" + m_apiKey;
    }

    public CompletableFuture<InputStream> perform(String p_queryParameters)
    {
        HttpClient httpClient = new HttpClient();
        return httpClient.getInputStream(getUrl(p_queryParameters));
    }
}
