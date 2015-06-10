/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.data;

import com.video.db.TitleManager;

/**
 *
 * @author Galimberti
 */
public class Item
{
    private int id;
    private int ref_title;
    private int midia;
    private int state;
    
    public static final int STATE_AVAILABE = 0;
    public static final int STATE_RENTED = 1;
    public static final int STATE_RESERVED = 2;
    public static final int STATE_OBSOLETE = 3;
    
    public static final int MIDIA_DVD = 0;
    public static final int MIDIA_BLURAY = 1;
    
    public static final String[] MIDIAS =
    {
        "DVD",
        "Blu-ray"
    };
    
    public static final String[] STATES =
    {
        "Disponível",
        "Alugado",
        "Reservado",
        "Indisponível"
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
     * @return the ref_title
     */
    public int getRef_title()
    {
        return ref_title;
    }

    /**
     * @param ref_title the ref_title to set
     */
    public void setRef_title( int ref_title )
    {
        this.ref_title = ref_title;
    }

    /**
     * @return the midia
     */
    public int getMidia()
    {
        return midia;
    }

    /**
     * @param midia the midia to set
     */
    public void setMidia( int midia )
    {
        this.midia = midia;
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
    
    @Override
    public String toString()
    {
        String s = "";
        
        try
        {
            Title t = TitleManager.getInstance().getTitle( ref_title );
            
            if ( t != null )
            {
                s =  id + " - " + t.getName();
            }
        }
        
        catch ( Exception e )
        {
        }
        
        return s;
    }
}
