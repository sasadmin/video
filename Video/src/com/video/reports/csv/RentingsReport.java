/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.reports.csv;

import com.video.controllers.ConfigurationManager;
import com.video.data.Renting;
import com.video.data.RentingItem;
import com.video.data.User;
import com.video.db.RentingManager;
import com.video.db.UserManager;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Galimberti
 */
public class RentingsReport
{
    public static File generateReport( User u )
    {
        File f = null;
        
        try
        {
            FileWriter writer = new FileWriter( f = new File( ConfigurationManager.getInstance().getProperty( "downloadFolder" ) + File.separator + ( u != null ? u.getName() : "Todos" ) + UUID.randomUUID() + ".csv" ) );

            writer.append("Operador");
            writer.append(';');
            writer.append("Situação");
            writer.append(';');
            writer.append("Data");
            writer.append(';');
            writer.append("Itens");
            
            List<Renting> rentings = u != null 
                    ? RentingManager.getInstance().getRentings( u.getId() ) 
                    : RentingManager.getInstance().getRentings();

            for ( Renting r : rentings )
            {
                writer.append('\n');
                writer.append( UserManager.getInstance().getUser( r.getRef_operator() ).getName() );
                writer.append(';');
                writer.append( Renting.STATES[r.getState()] );
                writer.append(';');
                writer.append( new SimpleDateFormat( "dd/MM/yyyy" ).format( r.getDt_rent() ) );
                writer.append(';');
                
                String items = "";
                
                for ( RentingItem ri : r.getRentingItems() )
                {
                    if ( !items.isEmpty() )
                    {
                        items += ", ";
                    }
                    
                    items += ri.getRef_item();
                }
                
                writer.append( items );
            }

            writer.flush();
            writer.close();
        }
        
        catch( Exception e )
        {
             e.printStackTrace();
        } 
        
        return f;
    }
}
