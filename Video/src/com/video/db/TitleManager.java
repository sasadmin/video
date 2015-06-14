/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import com.video.data.Title;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class TitleManager
{
    private static TitleManager instance;
    
    public static TitleManager getInstance()
    {
        if ( instance == null )
        {
            instance = new TitleManager();
        }
        
        return instance;
    }
    
    private TitleManager()
    {
    }
    
    public void addTitle( Title title ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "insert into video_titles " +
                     "( " +
                        "name," +
                        "original_title," +
                        "dt_released," +
                        "ref_category" +
                     ") " +
                     "values " +
                     "( " +
                        db.quote( title.getName() ) + "," +
                        db.quote( title.getOriginal_title() ) + "," +
                        db.quote( title.getDtReleased() ) + "," +
                        title.getCategory() +
                     ") ";
        
        db.executeCommand( sql );
    }
    
    public void updateTitle( Title title ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "update video_titles set " +
                     "name = " + db.quote( title.getName() ) + "," +
                     "original_title = " + db.quote( title.getOriginal_title() ) + "," +
                     "dt_released = " + db.quote( title.getDtReleased() ) + "," +
                     "ref_category = " + title.getCategory() +
                     " where id = " + title.getId();
        
        db.executeCommand( sql );
    }
    
    public List<Title> getTitles() throws Exception
    {
        List<Title> titles = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, name, original_title, dt_released, ref_category " +
                     " from video_titles";
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Title t = new Title();
            
            t.setId( rs.getInt( "id" ) );
            t.setName( rs.getString( "name" ) );
            t.setOriginal_title( rs.getString( "original_title" ) );
            t.setDtReleased( rs.getDate( "dt_released" ) );
            t.setCategory( rs.getInt( "ref_category" ) );
            
            titles.add( t );
        }
        
        return titles;
    }
    
    public Title getTitle( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, name, original_title, dt_released, ref_category " +
                     " from video_titles " +
                     " where " + 
                     " id = " + id;
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            Title t = new Title();
            
            t.setId( rs.getInt( "id" ) );
            t.setName( rs.getString( "name" ) );
            t.setOriginal_title( rs.getString( "original_title" ) );
            t.setDtReleased( rs.getDate( "dt_released" ) );
            t.setCategory( rs.getInt( "ref_category" ) );
            
            return t;
        }
        
        return null;
    }
    
    public void deleteTitle( Title title ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_titles " +
                     " where id = " + title.getId();
        
        db.executeCommand( sql );
    }
}
