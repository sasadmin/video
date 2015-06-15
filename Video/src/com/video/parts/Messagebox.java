package com.video.parts;

import org.zkoss.zk.ui.util.Clients;

public class Messagebox
{

    private Messagebox()
    {
    }

    public static void showMessage( String msg )
    {
        Clients.showNotification( msg, true );
    }
}
