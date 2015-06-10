/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import com.video.data.User;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class UserManager
{
    private static UserManager instance;
    
    public static UserManager getInstance()
    {
        if ( instance == null )
        {
            instance = new UserManager();
        }
        
        return instance;
    }
    
    private UserManager()
    {
    }
    
    public void addUser( User u ) throws Exception
    {
        Database db = Database.getInstance();
        
        u.setDt_created( new Date( System.currentTimeMillis() ) );
        
        String sql = "insert into video_users " +
                     "( " +
                        "login," +
                        "password," +
                        "type," +
                        "state," +
                        "dt_created," +
                        "rg," +
                        "name" +
                     ") " +
                     "values " +
                     "( " +
                        db.quote( u.getLogin() ) + "," +
                        db.quote( u.getPassword()) + "," +
                        u.getType() + "," +
                        u.getState() + "," +
                        db.quote( u.getDt_created() ) + "," +
                        db.quote( u.getRg()) + "," +
                        db.quote( u.getName()) +
                     ") ";
        
        db.executeCommand( sql );
    }
    
    public void updateUser( User u ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "update video_users set " +
                     "login = " + db.quote( u.getLogin() ) + "," +
                     "password = " + db.quote( u.getPassword() ) + "," +
                     "type = " + u.getType() + "," +
                     "state = " + u.getState() + "," +
                     "dt_created = " + db.quote( u.getDt_created() ) + "," +
                     "rg = " + db.quote( u.getRg() ) + "," +
                     "name = " + db.quote( u.getName() ) +
                     " where id = " + u.getId();
        
        db.executeCommand( sql );
    }
    
    public User getUserByLogin( String login ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, login, password, type, state, dt_created, rg, name " +
                     " from video_users " +
                     " where " + 
                     " login = " + db.quote( login );
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            User u = new User();
            
            u.setId( rs.getInt( "id" ) );
            u.setLogin( rs.getString( "login" ) );
            u.setName( rs.getString( "name" ) );
            u.setPassword( rs.getString( "password" ) );
            u.setRg( rs.getString( "rg" ) );
            u.setState( rs.getInt( "state" ) );
            u.setType( rs.getInt( "type" ) );
            u.setDt_created( rs.getDate( "dt_created" ) );
            
            return u;
        }
        
        return null;
    }
    
    public User getUser( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, login, password, type, state, dt_created, rg, name " +
                     " from video_users " +
                     " where " + 
                     " id = " + id;
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            User u = new User();
            
            u.setId( rs.getInt( "id" ) );
            u.setLogin( rs.getString( "login" ) );
            u.setName( rs.getString( "name" ) );
            u.setPassword( rs.getString( "password" ) );
            u.setRg( rs.getString( "rg" ) );
            u.setState( rs.getInt( "state" ) );
            u.setType( rs.getInt( "type" ) );
            u.setDt_created( rs.getDate( "dt_created" ) );
            
            return u;
        }
        
        return null;
    }
    
    public List<User> getUsers() throws Exception
    {
        List<User> users = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, login, password, type, state, dt_created, rg, name " +
                     " from video_users " +
                     " where " + 
                     " state <> " + User.STATE_DELETED;
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            User u = new User();
            
            u.setId( rs.getInt( "id" ) );
            u.setLogin( rs.getString( "login" ) );
            u.setName( rs.getString( "name" ) );
            u.setPassword( rs.getString( "password" ) );
            u.setRg( rs.getString( "rg" ) );
            u.setState( rs.getInt( "state" ) );
            u.setType( rs.getInt( "type" ) );
            u.setDt_created( rs.getDate( "dt_created" ) );
            
            users.add( u );
        }
        
        return users;
    }
    
    public List<User> getNormalUsers() throws Exception
    {
        List<User> users = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, login, password, type, state, dt_created, rg, name " +
                     " from video_users " +
                     " where " + 
                     " type = " + User.TYPE_USER +
                     " and " +
                     " state <> " + User.STATE_DELETED;
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            User u = new User();
            
            u.setId( rs.getInt( "id" ) );
            u.setLogin( rs.getString( "login" ) );
            u.setName( rs.getString( "name" ) );
            u.setPassword( rs.getString( "password" ) );
            u.setRg( rs.getString( "rg" ) );
            u.setState( rs.getInt( "state" ) );
            u.setType( rs.getInt( "type" ) );
            u.setDt_created( rs.getDate( "dt_created" ) );
            
            users.add( u );
        }
        
        return users;
    }
    
    public void deleteUser( User user ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_users " +
                     " where id = " + user.getId();
        
        db.executeCommand( sql );
    }
}
