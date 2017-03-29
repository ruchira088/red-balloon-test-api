package com.ruchira.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpWebServer
{
    private final HttpServer m_httpServer;

    public HttpWebServer() throws IOException
    {
        m_httpServer = HttpServer.create();
    }

    public void route(String p_path, HttpHandler p_httpHandler)
    {
        m_httpServer.createContext(p_path, p_httpHandler);
    }

    public void listen(Integer p_port) throws IOException
    {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(p_port);
        m_httpServer.bind(inetSocketAddress, 0);
        m_httpServer.start();

        System.out.println("The server is listening on port: " + p_port);
    }
}
