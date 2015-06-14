package com.video.controllers;

import com.video.data.ImdbData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

public class ImdbController
{

    public ImdbController()
    {
    }

    public static ImdbData getImdbData( String originalTitle )
    {
        try
        {
            String url = "http://www.omdbapi.com/?t=" + originalTitle.trim().replaceAll( " ", "%20" );

            URL obj = new URL( url );
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            StringBuffer returnValue;
            try ( BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) ) )
            {
                String inputLine;
                returnValue = new StringBuffer();
                while ( ( inputLine = in.readLine() ) != null )
                {
                    returnValue.append( inputLine );
                }

                JSONParser jSONParser = new JSONParser();
                JSONObject jSONObject = (JSONObject) jSONParser.parse( returnValue.toString() );

                jSONObject.forEach( ( Object t, Object u ) ->
                {
                    System.out.println( t + " - " + u );
                } );

                Object response = jSONObject.get( "Response" );

                if ( response != null && response.toString().equalsIgnoreCase( "true" ) )
                {
                    ImdbData imdbData = new ImdbData();
                    
                    jSONObject.forEach( ( Object name, Object value ) ->
                    {
                        imdbData.addInfo( name, value );
                    } );
                    
                    return imdbData;                    
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }
}
