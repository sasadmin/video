/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import com.video.data.Category;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class CategoryManager
{
    private static CategoryManager instance;
    
    public static CategoryManager getInstance()
    {
        if ( instance == null )
        {
            instance = new CategoryManager();
        }
        
        return instance;
    }
    
    private CategoryManager()
    {
    }
    
    public void addCategory( Category category ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "insert into video_categories " +
                     "( " +
                        "name" +
                     ") " +
                     "values " +
                     "( " +
                        db.quote( category.getName() ) +
                     ") ";
        
        db.executeCommand( sql );
    }
    
    public List<Category> getCategories() throws Exception
    {
        List<Category> categories = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, name " +
                     " from video_categories";
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Category c = new Category();
            
            c.setId( rs.getInt( "id" ) );
            c.setName( rs.getString( "name" ) );
            
            categories.add( c );
        }
        
        return categories;
    }
    
    public Category getCategory( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, name " +
                     " from video_categories " +
                     "where id = " + id;
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            Category c = new Category();
            
            c.setId( rs.getInt( "id" ) );
            c.setName( rs.getString( "name" ) );
            
            return c;
        }
        
        return null;
    }
    
    public void deleteCategory( Category category ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_categories " +
                     " where id = " + category.getId();
        
        db.executeCommand( sql );
    }
}
