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
	
     public static SSLConnectionSocketFactory getSSLContextFactory() throws NoSuchAlgorithmException, KeyManagementException
     {
        SSLContext sslContext         = SSLContexts.custom()
			    .useTLS()
			    .build();

			SSLConnectionSocketFactory sslContextFactory = new SSLConnectionSocketFactory(
			    sslContext,
			    new String[]{"TLSv1.2"},   
			    null,
			    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        return sslContextFactory;
     }
}
