package com.ruchira;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils
{
//    public static void copy1(InputStream p_inputStream, OutputStream p_outputStream) throws IOException
//    {
//        byte[] buffer = new byte[1024];
//        int length;
//
//        while((length = p_inputStream.read(buffer)) != -1)
//        {
//            System.out.println(length);
//            p_outputStream.write(buffer, 0, length);
//        }
//
//    }

    public static void copy(InputStream p_inputStream, OutputStream p_outputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int length = p_inputStream.read(buffer);

        if(length != -1)
        {
            p_outputStream.write(buffer, 0, length);
            copy(p_inputStream, p_outputStream);
        }
    }
}
