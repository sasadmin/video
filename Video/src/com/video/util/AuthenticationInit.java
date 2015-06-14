/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.util;

import com.video.data.User;
import com.video.db.UserManager;
import java.util.Map;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

/**
 *
 * @author Galimberti
 */
public class AuthenticationInit implements Initiator
{
    @Override
    public void doInit( Page page, Map<String, Object> args ) throws Exception
    {
        //debug
        User u = UserManager.getInstance().getUserByLogin( "admin" );
        Sessions.getCurrent( true ).setAttribute( "activeLogin", u );
        //---------
        
        
        Object cre = Sessions.getCurrent().getAttribute( "activeLogin" );
        
        if ( cre == null )
        {
            Executions.sendRedirect( "/Login.jsp" );
        }
    }
}
