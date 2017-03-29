package com.ruchira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Configuration
{
    public enum ConfigurationKey
    {
        HOST_NAME("lastFM.host"),
        URL_PATH("lastFM.apiVersion"),
        API_KEY("lastFM.apiKey");

        private String m_key;

        ConfigurationKey(String p_key)
        {
            m_key = p_key;
        }

        @Override
        public String toString()
        {
            return m_key;
        }
    }

    private static final String CONFIGURATION_FILE = "config.properties";

    public static Map<ConfigurationKey, String> getConfigValues()
    {
        File file = new File(CONFIGURATION_FILE);
        BufferedReader bufferedReader = null;

        try
        {
            bufferedReader = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return bufferedReader.lines()
                .filter(line -> !line.startsWith("#"))
                .map(line ->
                {
                    String[] strings = line.split("=");
                    String key = strings[0];
                    String value = strings[1];

                    ConfigurationKey configKey = Arrays.stream(ConfigurationKey.values())
                            .filter(configurationKey -> configurationKey.toString().equalsIgnoreCase(key))
                            .findFirst().get();

                    HashMap<ConfigurationKey, String> configValue = new HashMap<>();
                    configValue.put(configKey, value);

                    return configValue;
                })
                .reduce(new HashMap<>(), (output, entry) ->
                {
                    output.putAll(entry);
                    return output;
                });
    }
}
