package com.ruchira.services;

import java.util.HashMap;
import java.util.Map;

public class LastFmQuery
{
    public enum QueryParameter
    {
        FORMAT("format"),
        LIMIT("limit"),
        PAGE("page"),
        METHOD("method"),
        API_KEY("api_key"),
        COUNTRY("country"),
        ARTIST("artist");

        private String m_key = null;

        QueryParameter(String p_key)
        {
            m_key = p_key;
        }

        @Override
        public String toString()
        {
            return m_key;
        }
    }

    public enum ApiMethod
    {
        GET_ARTIST("artist.gettoptracks"),
        GET_TOP_ARTISTS_IN_COUNTRY("geo.gettopartists");

        private String m_value;

        ApiMethod(String p_value)
        {
            m_value = p_value;
        }

        @Override
        public String toString()
        {
            return m_value;
        }
    }

    private final String DEFAULT_FORMAT = "json";
    private final Integer DEFAULT_LIMIT = 5;
    private final Integer DEFAULT_PAGE = 1;


    private Map<QueryParameter, String> m_queryParameters = new HashMap<>();

    public void addParameter(QueryParameter p_queryParameter, String p_value)
    {
        m_queryParameters.put(p_queryParameter, p_value);
    }

    public String getQuery()
    {
        return m_queryParameters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce(null, (output, value) ->
                {
                    if(output != null)
                    {
                        return output + "&" + value;
                    } else {
                        return value;
                    }
                });
    }

    public LastFmQuery(ApiMethod p_apiMethod)
    {
        m_queryParameters.put(QueryParameter.METHOD, p_apiMethod.toString());
        m_queryParameters.put(QueryParameter.FORMAT, DEFAULT_FORMAT);
        m_queryParameters.put(QueryParameter.LIMIT, DEFAULT_LIMIT.toString());
        m_queryParameters.put(QueryParameter.PAGE, DEFAULT_PAGE.toString());
    }
}
