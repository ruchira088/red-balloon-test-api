package com.ruchira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuration
{
    public static String getApiKey() throws FileNotFoundException
    {
        File file = new File("apiKey.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().findFirst().get();
    }
}
