package com.video.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager
{
    private static ConfigurationManager instance = new ConfigurationManager();

    private String filename = "config.properties";

    public static ConfigurationManager getInstance()
    {
        return instance;
    }

    private final Properties properties = new Properties();

    public ConfigurationManager()
    {
        try
        {
            loadProperties( properties, getClass().getClassLoader().getResourceAsStream( filename ) );
        }
        catch ( Exception e )
        {
            e.printStackTrace( System.err );
        }
    }

    private void loadProperties( Properties props, InputStream in ) throws Exception
    {
        byte[] buffer = new byte[ 1024 ];

        BufferedInputStream bis = new BufferedInputStream( in );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ( true )
        {
            int count = bis.read( buffer );

            if ( count == -1 )
            {
                break;
            }

            baos.write( buffer, 0, count );
        }

        baos.close();

        byte[] data = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream( data );

        props.load( bais );

        bais.close();
    }

    public String getProperty( String key )
    {
        return properties.getProperty( key );
    }
    
    public String getProperty( String key, String value )
    {
        return properties.getProperty( key, value );
    }

    public void setProperty( String key, String value )
    {
        if ( value != null )
        {
            properties.setProperty( key, value );
        }
    }
}
