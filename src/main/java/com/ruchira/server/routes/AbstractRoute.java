package com.ruchira.server.routes;

import com.ruchira.Configuration;
import com.ruchira.Utils;
import com.ruchira.services.LastFmQuery;
import com.ruchira.services.LastFmService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractRoute implements HttpHandler
{
    private final LastFmService m_lastFmService;

    public abstract LastFmQuery getLastFmQuery(String p_pathParameter);

    protected AbstractRoute(LastFmService p_lastFmService)
    {
        m_lastFmService = p_lastFmService;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        LastFmQuery lastFmQuery = updateQueryParameters(httpExchange, getLastFmQuery(getPathParameter(httpExchange)));
        CompletableFuture<InputStream> completableFuture = m_lastFmService.perform(lastFmQuery);

        completableFuture.thenAccept(inputStream ->
        {
            OutputStream responseBody = httpExchange.getResponseBody();

            try
            {
                Headers responseHeaders = httpExchange.getResponseHeaders();
                responseHeaders.add("Access-Control-Allow-Origin", "*");
                httpExchange.getResponseHeaders().add("content-type", "application/json");
                httpExchange.sendResponseHeaders(200, 0);
                Utils.copy(inputStream, responseBody);
                responseBody.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    private LastFmQuery updateQueryParameters(HttpExchange p_httpExchange, LastFmQuery p_lastFmQuery)
    {
        String query = p_httpExchange.getRequestURI().getQuery();

        if(query != null)
        {
            Arrays.stream(query.split("&"))
                    .forEach(keyValue ->
                    {
                        String[] values = keyValue.split("=");

                        LastFmQuery.QueryParameter queryParameter =
                                Arrays.stream(LastFmQuery.QueryParameter.values())
                                        .filter(value -> values[0].equalsIgnoreCase(value.toString()))
                                        .findFirst().get();

                        String value = values[1];

                        p_lastFmQuery.addParameter(queryParameter, value);
                    });
        }

        return p_lastFmQuery;
    }

    private String getPathParameter(HttpExchange p_httpExchange)
    {
        String[] strings = p_httpExchange.getRequestURI().getPath().split("/");

        return strings[strings.length-1];
    }
}
