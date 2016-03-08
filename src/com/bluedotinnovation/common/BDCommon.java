package com.bluedotinnovation.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
	
	public static SSLContext getSSLContext() throws NoSuchAlgorithmException, KeyManagementException
    {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { new X509TrustManager() 
        {
           @Override
		public X509Certificate[] getAcceptedIssuers() 
           {
                   return null;
           }

           @Override
		public void checkClientTrusted(X509Certificate[] certs,
                           String authType) 
           {
           }

           @Override
		public void checkServerTrusted(X509Certificate[] certs,
                           String authType) 
           {
           }
      } }, new SecureRandom());
        
        return sslContext;
    }

}
