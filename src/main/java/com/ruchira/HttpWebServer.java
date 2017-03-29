package com.ruchira;

import com.ruchira.services.LastFmService;
import com.sun.net.httpserver.HttpServer;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class HttpWebServer
{
    private final HttpServer m_httpServer;

    public void listen(Integer p_port) throws IOException
    {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(p_port);
        m_httpServer.bind(inetSocketAddress, 0);
        m_httpServer.start();

        System.out.println("The server is listening on port: " + p_port);
    }

    public HttpWebServer() throws IOException
    {
        m_httpServer = HttpServer.create();
        m_httpServer.createContext("/last-fm", httpExchange ->
        {
            LastFmService lastFmService = new LastFmService(Configuration.getApiKey());
            CompletableFuture<InputStream> completableFuture = lastFmService.perform(httpExchange.getRequestURI().getQuery());
            completableFuture.thenAccept(inputStream ->
            {
                OutputStream responseBody = httpExchange.getResponseBody();

                try
                {
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
        });
    }

}
