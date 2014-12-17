package com.bluedotinnovation.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class BDCommon 
{	
	public static byte[] readStream(InputStream stream) throws IOException
	{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try
		{
			byte[] block     =  new byte[1024];
			int bytesRead    = 0;
			while((bytesRead = stream.read(block))>0)
			{
				byteStream.write(block, 0, bytesRead);
			}
			return byteStream.toByteArray();
		}
        catch(Exception e)
        {
            throw new IOException(e);
        }
        finally
        {
            byteStream.close();
        }
    }

}
