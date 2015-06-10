/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.util;

import com.video.data.Category;
import com.video.data.User;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Galimberti
 */
public class ApplicationUtilities
{
    public static User getActiveUser()
    {
        if ( Sessions.getCurrent() != null )
        {
            return (User)Sessions.getCurrent().getAttribute( "activeLogin" );
        }
        
        return null;
    }
    
    public static void logout()
    {
        Sessions.getCurrent().invalidate();
        Executions.getCurrent().sendRedirect( "/Login.jsp" );
    }
}
