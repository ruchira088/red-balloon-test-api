package com.ruchira;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class HttpClient
{
    public CompletableFuture<InputStream> getInputStream(String p_url)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                URL url = new URL(p_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                return httpURLConnection.getInputStream();
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
                return null;
            }
        });
    }
}
