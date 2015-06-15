package com.video.util;

import com.video.data.User;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

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
