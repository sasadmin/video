/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Galimberti
 */
public class Database
{
    private static Database instance;
    
    public static Database getInstance()
    {
        if ( instance == null )
        {
            instance = new Database();
        }
        
        return instance;
    }
    
    private Connection connection = null;
    
    private Database()
    {
        try
        {
            Class.forName( "org.postgresql.Driver" );
        }
        
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
            return;
        }

        try
        {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/video", "postgres",
                    "post" );
        }
        
        catch ( SQLException e )
        {
            e.printStackTrace();
            return;
        }
    }
    
    public ResultSet executeQuery( String sql ) throws Exception
    {
        return connection.createStatement().executeQuery( sql );
    }
    
    public void executeCommand( String sql ) throws Exception
    {
        connection.createStatement().execute( sql );
    }
    
    public String quote( Date dt )
    {
        if ( dt != null )
        {
            return quote( new SimpleDateFormat( "yyyy-MM-dd" ).format( dt ) );
        }
        
        return "null";
    }
    
    public String quote ( String s )
    {
        return "'" + s + "'";
    }
}
