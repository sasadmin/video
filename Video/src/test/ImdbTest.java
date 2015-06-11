package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

public class ImdbTest
{

    public static void main( String[] args ) throws Exception
    {
        sendGet();
    }

    private static void sendGet() throws Exception
    {

        String url = "http://www.omdbapi.com/?t=hobbit";

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
                System.out.println( "aaaaaaaaaaaaa" );
            }
        }

    }
}
