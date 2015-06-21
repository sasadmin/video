/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.reports.csv;

import com.video.controllers.ConfigurationManager;
import com.video.data.Item;
import com.video.data.Title;
import com.video.db.CategoryManager;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 *
 * @author Galimberti
 */
public class TitlesReport
{
    public static File generateReport()
    {
        File f = null;
        
        try
        {
            FileWriter writer = new FileWriter( f = new File( ConfigurationManager.getInstance().getProperty( "downloadFolder" ) + File.separator + "Titulos" + UUID.randomUUID() + ".csv" ) );

            writer.append("Título");
            writer.append(';');
            writer.append("Gênero");
            writer.append(';');
            writer.append("Lançamento");
            writer.append(';');
            writer.append("DVDs");
            writer.append(';');
            writer.append("BLURAYs");

            for ( Title t : TitleManager.getInstance().getTitles() )
            {
                writer.append('\n');
                writer.append( t.getName() );
                writer.append(';');
                writer.append( CategoryManager.getInstance().getCategory( t.getCategory() ).getName() );
                writer.append(';');
                writer.append( new SimpleDateFormat( "dd/MM/yyyy" ).format( t.getDtReleased() ) );
                writer.append(';');
                writer.append( "" + ItemManager.getInstance().getItemsForTitle( t.getId(), Item.MIDIA_DVD ).toString().replace( "[", "" ).replace( "]", "" ) );
                writer.append(';');
                writer.append( "" + ItemManager.getInstance().getItemsForTitle( t.getId(), Item.MIDIA_BLURAY ).toString().replace( "[", "" ).replace( "]", "" ) );
                writer.append(';');
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
