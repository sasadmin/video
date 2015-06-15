package com.video.controllers;

import com.video.data.User;
import com.video.db.UserManager;
import com.video.parts.Messagebox;
import com.video.parts.Table;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;

public class LoginController extends SelectorComposer<Component>
{
    private final Textbox userName = new Textbox();
    private final Textbox passWord = new Textbox();
    private final Button loginButton = new Button();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        loginButton.setLabel( "Login" );
        loginButton.setZclass( " " );
        loginButton.setSclass( "login-button" );
        
        userName.setZclass( " " );
        userName.setSclass( "input-login" );
        userName.setPlaceholder( "Informe o usuário" );
        
        passWord.setZclass( " " );
        passWord.setSclass( "input-login" );
        passWord.setPlaceholder( "Informe a senha" );
        
        Image userImg = new Image( "/img/user.png" );
        userImg.setStyle( "padding-right: 10px;" );
        Image passImg = new Image( "/img/lock.png" );
        passImg.setStyle( "padding-right: 10px;" );
        
        Div loginPane = new Div();
        loginPane.appendChild( userImg );
        loginPane.appendChild( userName );
        
        Div passPane = new Div();
        passPane.appendChild( passImg );
        passPane.appendChild( passWord );
        
        Table table = new Table();
        
        table.createRow( null, loginPane );
        table.createRow( null, passPane );
        table.createRow( null, new Div() );
        table.createRow( null, loginButton );
        
        table.setColspan( 2, 0, 3 );
        
        comp.appendChild( table );
        
        loginButton.addEventListener( Events.ON_CLICK, new EventListener<Event>() 
        {
            @Override
            public void onEvent(Event event) throws Exception 
            {
                doLogin();
            }
        });
    }

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
            Messagebox.showMessage( "Informe o usuário!" );
            userName.setFocus( true );
        }

        else if ( isNull( passWord.getValue() ) )
        {
            Messagebox.showMessage( "Informe a senha!" );
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
                Messagebox.showMessage( "Usuário e/ou senha incorretos!" );
                userName.setValue( "" );
                passWord.setValue( "" );
                userName.setFocus( true );
            }
            
            else
            {
                if ( u.getType() == User.TYPE_USER )
                {
                    Messagebox.showMessage( "Usuário sem permissão para acessar esta aplicação!" );
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
