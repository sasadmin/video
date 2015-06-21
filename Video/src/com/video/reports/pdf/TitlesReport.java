/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.reports.pdf;

import com.video.data.Item;
import com.video.data.Title;
import com.video.db.CategoryManager;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import java.text.SimpleDateFormat;
import java.util.List;
import org.zkoss.json.JSONObject;

/**
 *
 * @author Galimberti
 */
public class TitlesReport
{
    public static String getJSONReport() throws Exception
    {
        JSONObject json = new JSONObject();
        
        List<Title> titles = TitleManager.getInstance().getTitles();
        
        json.put( "cols", 5 );
        json.put( "items", titles.size() );
        
        json.put( "header.1", "Título" );
        json.put( "header.2", "Gênero" );
        json.put( "header.3", "Lançamento" );
        json.put( "header.4", "DVDs" );
        json.put( "header.5", "BLURAYs" );
        
        for ( int i = 1; i <= titles.size(); i++ )
        {
            Title t = titles.get( i-1 );
            
            json.put( "col." + i + ".1", quote( t.getName() ) );
            json.put( "col." + i + ".2", quote( CategoryManager.getInstance().getCategory( t.getCategory() ).getName() ) );
            json.put( "col." + i + ".3", quote( new SimpleDateFormat( "dd/MM/yyyy" ).format( t.getDtReleased() ) ) );
            json.put( "col." + i + ".4", quote( ItemManager.getInstance().getItemsForTitle( t.getId(), Item.MIDIA_DVD ).toString().replace( "[", "" ).replace( "]", "" ) ) );
            json.put( "col." + i + ".5", quote( ItemManager.getInstance().getItemsForTitle( t.getId(), Item.MIDIA_BLURAY ).toString().replace( "[", "" ).replace( "]", "" ) ) );
        }
        
        return json.toJSONString();
    }
    
    public static String quote( String s )
    {
        return "" + s;
    }
}
