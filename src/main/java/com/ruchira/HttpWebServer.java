package com.ruchira;

import com.ruchira.services.LastFmService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;


public class HttpWebServer
{
    private final HttpServer m_httpServer;

    public HttpWebServer() throws IOException
    {
        m_httpServer = HttpServer.create();

        m_httpServer.createContext("/last-fm/country", httpExchange ->
        {
            String country = getLastPathValue(httpExchange);
            getHttpHandler("country=" + country + "&method=geo.gettoptracks&", httpExchange
            );
        });

        m_httpServer.createContext("/last-fm/artist", httpExchange ->
        {
            String artist = getLastPathValue(httpExchange);
            getHttpHandler("artist=" + artist +"&method=artist.gettoptracks&", httpExchange);
        });
    }

    public void listen(Integer p_port) throws IOException
    {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(p_port);
        m_httpServer.bind(inetSocketAddress, 0);
        m_httpServer.start();

        System.out.println("The server is listening on port: " + p_port);
    }

    private String getLastPathValue(HttpExchange p_httpExchange)
    {
        String[] strings = p_httpExchange.getRequestURI().getPath().split("/");

        return strings[strings.length-1];
    }

    private void getHttpHandler(String p_query, HttpExchange httpExchange)
    {
        String urlQueryParameters = httpExchange.getRequestURI().getQuery();
        String query = p_query + (urlQueryParameters != null ? urlQueryParameters : "");

        LastFmService lastFmService = new LastFmService(Configuration.getApiKey());
        CompletableFuture<InputStream> completableFuture = lastFmService.perform(query);
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

}
