/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import com.video.data.Item;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class ItemManager
{
    private static ItemManager instance;
    
    public static ItemManager getInstance()
    {
        if ( instance == null )
        {
            instance = new ItemManager();
        }
        
        return instance;
    }
    
    private ItemManager()
    {
    }
    
    public void addItem( Item item ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "insert into video_items " +
                     "( " +
                        "ref_title," +
                        "midia," +
                        "state" +
                     ") " +
                     "values " +
                     "( " +
                        item.getRef_title() + "," +
                        item.getMidia() + "," +
                        item.getState() +
                     ") ";
        
        db.executeCommand( sql );
    }
    
    public void updateItem( Item item ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "update video_items set " +
                     "ref_title = " + item.getRef_title() + "," +
                     "midia = " + item.getMidia() + "," +
                     "state = " + item.getState() +
                     " where id = " + item.getId();
        
        db.executeCommand( sql );
    }
    
    public Item getItem( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, ref_title, midia, state " +
                     " from video_items where id = " + id;
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            Item i = new Item();
            
            i.setId( rs.getInt( "id" ) );
            i.setRef_title( rs.getInt( "ref_title" ) );
            i.setMidia( rs.getInt( "midia" ) );
            i.setState( rs.getInt( "state" ) );
            
            return i;
        }
        
        return null;
    }
    
    public List<Item> getItems() throws Exception
    {
        List<Item> items = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, ref_title, midia, state " +
                     " from video_items";
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Item i = new Item();
            
            i.setId( rs.getInt( "id" ) );
            i.setRef_title( rs.getInt( "ref_title" ) );
            i.setMidia( rs.getInt( "midia" ) );
            i.setState( rs.getInt( "state" ) );
            
            items.add( i );
        }
        
        return items;
    }
    
    public List<Item> getItemsForTitle( int title, int midia ) throws Exception
    {
        List<Item> items = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, ref_title, midia, state " +
                     " from video_items" +
                     " where ref_title = " + title;
        
        if ( midia != -1 )
        {
            sql += " and midia = " + midia;
        }
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Item i = new Item();
            
            i.setId( rs.getInt( "id" ) );
            i.setRef_title( rs.getInt( "ref_title" ) );
            i.setMidia( rs.getInt( "midia" ) );
            i.setState( rs.getInt( "state" ) );
            
            items.add( i );
        }
        
        return items;
    }
    
    public List<Item> getItems( String text, int state ) throws Exception
    {
        List<Item> items = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select i.id, i.ref_title, i.midia, i.state " +
                     " from video_items i, video_titles t " +
                     " where t.id = i.ref_title and lower(t.name) like '%" + text.toLowerCase() + "%'";
        
        if ( state != -1 )
        {
            sql += " and i.state = " + state;
        }
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Item i = new Item();
            
            i.setId( rs.getInt( "id" ) );
            i.setRef_title( rs.getInt( "ref_title" ) );
            i.setMidia( rs.getInt( "midia" ) );
            i.setState( rs.getInt( "state" ) );
            
            items.add( i );
        }
        
        return items;
    }
    
    public List<Item> getAvailableItems() throws Exception
    {
        List<Item> items = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, ref_title, midia, state " +
                     " from video_items " +
                     " where state = " + Item.STATE_AVAILABE;
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Item i = new Item();
            
            i.setId( rs.getInt( "id" ) );
            i.setRef_title( rs.getInt( "ref_title" ) );
            i.setMidia( rs.getInt( "midia" ) );
            i.setState( rs.getInt( "state" ) );
            
            items.add( i );
        }
        
        return items;
    }
    
    public void returnItems() throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "update video_items set " +
                     "state = " + Item.STATE_AVAILABE +
                     " where id not in (select ref_item from video_renting_items where dt_return is not null)";
        
        db.executeCommand( sql );
    }
    
    public void deleteItem( Item item ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_items " +
                     " where id = " + item.getId();
        
        db.executeCommand( sql );
    }
}
