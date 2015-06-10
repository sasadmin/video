/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.db;

import com.video.data.Item;
import com.video.data.Renting;
import com.video.data.RentingItem;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class RentingManager
{
    private static RentingManager instance;
    
    public static RentingManager getInstance()
    {
        if ( instance == null )
        {
            instance = new RentingManager();
        }
        
        return instance;
    }
    
    private RentingManager()
    {
    }
    
    public void addRenting( Renting renting ) throws Exception
    {
        Database db = Database.getInstance();
        
        int state = Renting.STATE_WITHIN;
        
        int countPending = 0;
        
        for ( RentingItem ri : renting.getRentingItems() )
        {
            if ( ri.getDt_return() == null )
            {
                countPending++;
            }
            
            if ( ri.getDt_return() == null && ri.getDt_due() != null && ri.getDt_due().before( new Date( System.currentTimeMillis() ) ) )
            {
                state = Renting.STATE_DELAYED;
                break;
            }
        }
        
        if ( countPending == 0 )
        {
            state = Renting.STATE_FINISHED;
        }
        
        String sql = "insert into video_rentings " +
                     "( " +
                        "ref_user," +
                        "state," +
                        "cost," +
                        "dt_rent," +
                        "current_payment," +
                        "ref_operator" +
                     ") " +
                     "values " +
                     "( " +
                        renting.getRef_user() + "," +
                        state + "," +
                        renting.getCost() + "," +
                        db.quote( new Date( System.currentTimeMillis() ) ) + "," +
                        renting.getCurrent_payment() + "," +
                        renting.getRef_operator() +
                     ") ";
        
        db.executeCommand( sql );
        
        addRentingItems( renting.getRentingItems(), null );
    }
    
    public void updateRenting( Renting renting ) throws Exception
    {
        Database db = Database.getInstance();
        
        int state = Renting.STATE_WITHIN;
        
        int countPending = 0;
        
        for ( RentingItem ri : renting.getRentingItems() )
        {
            if ( ri.getDt_return() == null )
            {
                countPending++;
            }
            
            if ( ri.getDt_return() == null && ri.getDt_due() != null && ri.getDt_due().before( new Date( System.currentTimeMillis() ) ) )
            {
                state = Renting.STATE_DELAYED;
                break;
            }
        }
        
        if ( countPending == 0 )
        {
            state = Renting.STATE_FINISHED;
        }
        
        String sql = "update video_rentings set " +
                     "ref_user = " + renting.getRef_user() + "," +
                     "state = " + state + "," +
                     "cost = " + renting.getCost() + "," +
                     "dt_rent = " + db.quote( renting.getDt_rent() ) + "," +
                     "current_payment = " + renting.getCurrent_payment() + "," +
                     "ref_operator = " + renting.getRef_operator() +
                     " where id = " + renting.getId();
        
        db.executeCommand( sql );
        
        deleteRentingItems( renting );
        addRentingItems( renting.getRentingItems(), renting );
    }
    
    public Renting getRenting( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "select id, ref_user, state, cost, dt_rent, current_payment, ref_operator " +
                     " from video_rentings";
        
        ResultSet rs = db.executeQuery( sql );
        
        if ( rs.next() )
        {
            Renting r = new Renting();
            
            r.setId( rs.getInt( "id" ) );
            r.setRef_user( rs.getInt( "ref_user" ) );
            r.setState( rs.getInt( "state" ) );
            r.setCost( rs.getInt( "cost" ) );
            r.setDt_rent( rs.getDate( "dt_rent" ) );
            r.setCurrent_payment( rs.getInt( "current_payment" ) );
            r.setRef_operator( rs.getInt( "ref_operator" ) );
            r.setRentingItems( getRentingItems( r ) );
            
            return r;
        }
        
        return null;
    }
    
    public List<Renting> getRentings() throws Exception
    {
        List<Renting> rentings = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select id, ref_user, state, cost, dt_rent, current_payment, ref_operator " +
                     " from video_rentings";
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            Renting r = new Renting();
            
            r.setId( rs.getInt( "id" ) );
            r.setRef_user( rs.getInt( "ref_user" ) );
            r.setState( rs.getInt( "state" ) );
            r.setCost( rs.getInt( "cost" ) );
            r.setDt_rent( rs.getDate( "dt_rent" ) );
            r.setCurrent_payment( rs.getInt( "current_payment" ) );
            r.setRef_operator( rs.getInt( "ref_operator" ) );
            r.setRentingItems( getRentingItems( r ) );
            
            rentings.add( r );
        }
        
        return rentings;
    }
    
    public List<RentingItem> getRentingItems( Renting r ) throws Exception
    {
        List<RentingItem> rentingItems = new ArrayList();
        
        Database db = Database.getInstance();
        
        String sql = "select ref_renting, ref_item, dt_due, dt_return " +
                     " from video_renting_items where ref_renting = " + r.getId();
        
        ResultSet rs = db.executeQuery( sql );
        
        while ( rs.next() )
        {
            RentingItem ri = new RentingItem();
            
            ri.setRef_renting( rs.getInt( "ref_renting" ) );
            ri.setRef_item( rs.getInt( "ref_item" ) );
            ri.setDt_due( rs.getDate( "dt_due" ) );
            ri.setDt_return( rs.getDate( "dt_return" ) );
            
            rentingItems.add( ri );
        }
        
        return rentingItems;
    }
    
    public void addRentingItems( List<RentingItem> rentingItems, Renting r ) throws Exception
    {
        Database db = Database.getInstance();
        
        for ( RentingItem ri : rentingItems )
        {
            String sql = "insert into video_renting_items " +
                         "( " +
                            "ref_renting," +
                            "ref_item," +
                            "dt_due," +
                            "dt_return" +
                         ") " +
                         "values " +
                         "( " +
                            ( r != null ? r.getId() : "(select max(id) from video_rentings)" ) + "," +
                            ri.getRef_item() + "," +
                            db.quote( ri.getDt_due() ) + "," +
                            db.quote( ri.getDt_return() ) +
                         ") ";

            db.executeCommand( sql );
            
            Item i = ItemManager.getInstance().getItem( ri.getRef_item() );
            
            i.setState( ri.getDt_return() != null ? Item.STATE_AVAILABE : Item.STATE_RENTED );
            
            ItemManager.getInstance().updateItem( i );
            
        }
    }
    
    public void deleteRenting( Renting renting ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_rentings " +
                     " where id = " + renting.getId();
        
        db.executeCommand( sql );
        
        deleteRentingItems( renting );
    }
    
    public void deleteRentingItems( Renting renting ) throws Exception
    {
        Database db = Database.getInstance();
        
        String sql = "delete from video_renting_items " +
                     " where ref_renting = " + renting.getId();
        
        db.executeCommand( sql );
        
        ItemManager.getInstance().returnItems();
    }
}
