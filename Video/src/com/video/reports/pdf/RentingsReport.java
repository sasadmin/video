/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.reports.pdf;

import com.video.data.Renting;
import com.video.data.RentingItem;
import com.video.db.RentingManager;
import com.video.db.UserManager;
import java.text.SimpleDateFormat;
import java.util.List;
import org.zkoss.json.JSONObject;

/**
 *
 * @author Galimberti
 */
public class RentingsReport
{
    public static String getJSONReport() throws Exception
    {
        JSONObject json = new JSONObject();
        
        List<Renting> rentings = RentingManager.getInstance().getRentings();
        
        json.put( "cols", 5 );
        json.put( "items", rentings.size() );
        
        json.put( "header.1", "Operador" );
        json.put( "header.2", "Cliente" );
        json.put( "header.3", "Situação" );
        json.put( "header.4", "Data" );
        json.put( "header.5", "Itens" );
        
        for ( int i = 1; i <= rentings.size(); i++ )
        {
            Renting r = rentings.get( i-1 );
            
            json.put( "col." + i + ".1", quote( UserManager.getInstance().getUser( r.getRef_operator() ).getName() ) );
            json.put( "col." + i + ".2", quote( UserManager.getInstance().getUser( r.getRef_user() ).getName() ) );
            json.put( "col." + i + ".3", quote( Renting.STATES[r.getState()] ) );
            json.put( "col." + i + ".4", quote( new SimpleDateFormat( "dd/MM/yyyy" ).format( r.getDt_rent() ) ) );

            String items = "";

            for ( RentingItem ri : r.getRentingItems() )
            {
                if ( !items.isEmpty() )
                {
                    items += ", ";
                }

                items += ri.getRef_item();
            }

            json.put( "col." + i + ".5", quote( items ) );
        }
        
        return json.toJSONString();
    }
    
    public static String quote( String s )
    {
        return "" + s;
    }
}
