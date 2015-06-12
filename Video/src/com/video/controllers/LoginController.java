/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.controllers;

import com.video.data.User;
import com.video.db.UserManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class LoginController extends SelectorComposer<Component>
{

    @Wire
    private Component component;
    @Wire
    private Textbox userName;
    @Wire
    private Textbox passWord;
    @Wire
    private Button login;

    @Listen( "onClick=#login" )
    public void doLogin()
    {
        if ( validate() )
        {
            try
            {
                User u = UserManager.getInstance().getUserByLogin( userName.getValue() );
//                User u = new User();
//                u.setName("Admin");
//                u.setType(User.TYPE_ADMIN);
                
                Sessions.getCurrent( true ).setAttribute( "activeLogin", u );
                Executions.sendRedirect( "apps/AdministratorApplication.jsp" );
            }
            
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    private boolean validate()
    {
        if ( isNull( userName.getValue() ) )
        {
            Messagebox.show( "Informe o usuário!" );
            userName.setFocus( true );
        }

        else if ( isNull( passWord.getValue() ) )
        {
            Messagebox.show( "Informe a senha!" );
            passWord.setFocus( true );
        }

        else
        {
            User u = null;
            
            try
            {
                u = UserManager.getInstance().getUserByLogin( userName.getValue() );
            }
            
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            
            if ( u == null || !u.getPassword().equals( passWord.getValue() ) )
            {
                Messagebox.show( "Usuário e/ou senha incorretos!" );
                userName.setValue( "" );
                passWord.setValue( "" );
                userName.setFocus( true );
            }
            
            else
            {
                if ( u.getType() == User.TYPE_USER )
                {
                    Messagebox.show( "Usuário sem permissão para acessar esta aplicação!" );
                    userName.setValue( "" );
                    passWord.setValue( "" );
                    userName.setFocus( true );
                }
                
                else
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isNull( String value )
    {
        boolean result = true;

        if ( value != null && value.trim().length() > 0
                && !value.trim().equalsIgnoreCase( "null" ) )
        {
            result = false;
        }

        return result;
    }
}
