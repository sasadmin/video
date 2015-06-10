/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.data;

import java.sql.Date;

/**
 *
 * @author Galimberti
 */
public class RentingItem
{
    private int ref_renting;
    private int ref_item;
    private Date dt_due;
    private Date dt_return;

    /**
     * @return the ref_renting
     */
    public int getRef_renting()
    {
        return ref_renting;
    }

    /**
     * @param ref_renting the ref_renting to set
     */
    public void setRef_renting( int ref_renting )
    {
        this.ref_renting = ref_renting;
    }

    /**
     * @return the ref_item
     */
    public int getRef_item()
    {
        return ref_item;
    }

    /**
     * @param ref_item the ref_item to set
     */
    public void setRef_item( int ref_item )
    {
        this.ref_item = ref_item;
    }

    /**
     * @return the dt_due
     */
    public Date getDt_due()
    {
        return dt_due;
    }

    /**
     * @param dt_due the dt_due to set
     */
    public void setDt_due( Date dt_due )
    {
        this.dt_due = dt_due;
    }

    /**
     * @return the dt_return
     */
    public Date getDt_return()
    {
        return dt_return;
    }

    /**
     * @param dt_return the dt_return to set
     */
    public void setDt_return( Date dt_return )
    {
        this.dt_return = dt_return;
    }

    @Override
    public boolean equals( Object obj )
    {
        RentingItem other = (RentingItem)obj;
        
        return other.ref_item == ref_item && other.ref_renting == ref_renting;
    }
}
