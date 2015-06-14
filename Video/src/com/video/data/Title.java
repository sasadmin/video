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
public class Title
{
    private int id;
    private String name;
    private String original_title;
    private Date dtReleased;
    private int category;

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return the dtReleased
     */
    public Date getDtReleased()
    {
        return dtReleased;
    }

    /**
     * @param dtReleased the dtReleased to set
     */
    public void setDtReleased( Date dtReleased )
    {
        this.dtReleased = dtReleased;
    }

    /**
     * @return the category
     */
    public int getCategory()
    {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory( int category )
    {
        this.category = category;
    }

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

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals( Object obj )
    {
        return ((Title)obj).getId() == id;
    }

    /**
     * @return the original_title
     */
    public String getOriginal_title()
    {
        return original_title;
    }

    /**
     * @param original_title the original_title to set
     */
    public void setOriginal_title( String original_title )
    {
        this.original_title = original_title;
    }
}
