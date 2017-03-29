package com.ruchira;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils
{
    public static int copy(InputStream p_inputStream, OutputStream p_outputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int length;

        while((length = p_inputStream.read(buffer)) != -1)
        {
            p_outputStream.write(buffer, 0, length);
        }

        return length;
    }
}
