/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.data;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Galimberti
 */
public class Renting
{
    private int id;
    private int ref_user;
    private int state;
    private double cost;
    private Date dt_rent;
    private double current_payment;
    private int ref_operator;
    
    private List<RentingItem> rentingItems;
    
    public static final int STATE_WITHIN = 0;
    public static final int STATE_FINISHED = 1;
    public static final int STATE_DELAYED = 2;
    public static final int STATE_QUARANTINE = 3;
    
    public static final String[] STATES =
    {
        "Aguardando",
        "Devolvida",
        "Atrasada",
        "Incompleta"
    };

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( int id )
    {
        this.id = id;
    }

    /**
     * @return the ref_user
     */
    public int getRef_user()
    {
        return ref_user;
    }

    /**
     * @param ref_user the ref_user to set
     */
    public void setRef_user( int ref_user )
    {
        this.ref_user = ref_user;
    }

    /**
     * @return the state
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState( int state )
    {
        this.state = state;
    }

    /**
     * @return the cost
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost( double cost )
    {
        this.cost = cost;
    }

    /**
     * @return the dt_rent
     */
    public Date getDt_rent()
    {
        return dt_rent;
    }

    /**
     * @param dt_rent the dt_rent to set
     */
    public void setDt_rent( Date dt_rent )
    {
        this.dt_rent = dt_rent;
    }

    /**
     * @return the current_payment
     */
    public double getCurrent_payment()
    {
        return current_payment;
    }

    /**
     * @param current_payment the current_payment to set
     */
    public void setCurrent_payment( double current_payment )
    {
        this.current_payment = current_payment;
    }

    /**
     * @return the ref_operator
     */
    public int getRef_operator()
    {
        return ref_operator;
    }

    /**
     * @param ref_operator the ref_operator to set
     */
    public void setRef_operator( int ref_operator )
    {
        this.ref_operator = ref_operator;
    }

    /**
     * @return the rentingItems
     */
    public List<RentingItem> getRentingItems()
    {
        return rentingItems;
    }

    /**
     * @param rentingItems the rentingItems to set
     */
    public void setRentingItems( List<RentingItem> titles )
    {
        this.rentingItems = titles;
    }
}
