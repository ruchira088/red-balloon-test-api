package com.ruchira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuration
{
    public static String getApiKey()
    {
        File file = new File("apiKey.txt");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader.lines().findFirst().get();
    }
}
