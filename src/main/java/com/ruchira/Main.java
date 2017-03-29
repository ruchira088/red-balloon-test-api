package com.ruchira;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        HttpWebServer httpWebServer = new HttpWebServer();
        httpWebServer.listen(8000);
    }
}
