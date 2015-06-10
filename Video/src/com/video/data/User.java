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
public class User
{
    private int id;
    private String login;
    private String password;
    private int type;
    private int state;
    private Date dt_created;
    private String rg;
    private String name;
    
    public static final int TYPE_USER = 0;
    public static final int TYPE_FUNC = 1;
    public static final int TYPE_ADMIN = 2;
    
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DELETED = 1;
    
    public static final String[] TYPES =
    {
        "Cliente",
        "Funcionário",
        "Administrador"
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
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword( String password )
    {
        this.password = password;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType( int type )
    {
        this.type = type;
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
     * @return the dt_created
     */
    public Date getDt_created()
    {
        return dt_created;
    }

    /**
     * @param dt_created the dt_created to set
     */
    public void setDt_created( Date dt_created )
    {
        this.dt_created = dt_created;
    }

    /**
     * @return the rg
     */
    public String getRg()
    {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg( String rg )
    {
        this.rg = rg;
    }

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

    @Override
    public boolean equals( Object obj )
    {
        return ((User)obj).getId() == id;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
