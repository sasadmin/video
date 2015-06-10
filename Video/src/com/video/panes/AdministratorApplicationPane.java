/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.panes;

import com.video.parts.AdministratorMenu;
import com.video.parts.ApplicationActionMenu;
import com.video.util.ApplicationUtilities;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Galimberti
 */
public class AdministratorApplicationPane
    extends Div
{
    public AdministratorApplicationPane()
    {
        initComponents();
    }
    
    private void setApplication( DefaultPane application )
    {
        application.setSclass( "default-application" );
        
        innerCenter.getChildren().clear();
        
        innerCenter.appendChild( application );
        
        actionsMenu.setActions( application.getActions() );
        
        application.refreshContent();
    }
    
    private void initComponents()
    {
        Div div = new Div();
        
        div.setStyle( "border: 1px solid rgb(0,50,90); position: absolute; top: 0px; left: 30px; background-color: white; z-index: 1000;" );
        div.setWidth( "80px" );
        div.setHeight( "80px" );
        
        Image img = new Image( "/img/dvd.png" );
        
        div.setAlign( "center" );
        
        div.appendChild( img );
        appendChild( div );
        
        Vbox vbox = new Vbox();
        
        vbox.setStyle( "position: absolute; top: 10px; right: 10px; background-color: transparent; z-index: 1000;" );
        
        Label lbUser = new Label();
        Label lbLogout = new Label();
        
        lbUser.setValue( ApplicationUtilities.getActiveUser().getName() );
        lbLogout.setValue( "Logout" );
        
        lbUser.setStyle( "font-weight: bold; color: white; text-shadow: black !important;" );
        lbLogout.setStyle( "font-weight: bold; cursor: pointer; color: rgb(229,231,242);" );
        
        vbox.setAlign( "right" );
        
        vbox.appendChild( lbUser );
        vbox.appendChild( lbLogout );
        
        appendChild( vbox );
        
        north.setHeight( "70px" );
        innerEast.setWidth( "250px" );
        
        north.setSclass( "north-menu-pane" );
        center.setSclass( "center-application" );
        innerBorderlayout.setSclass( "inner-application-borderlayout" );
        
        north.appendChild( menu );
        center.appendChild( innerBorderlayout );
        
        innerBorderlayout.appendChild( innerCenter );
        innerBorderlayout.appendChild( innerEast );
        
        innerEast.appendChild( actionsMenu );
        
        actionsMenu.setSclass( "actions-pane" );
        
        innerEast.setSclass( "inner-application-east" );
        innerCenter.setSclass( "inner-application-center" );
        
        borderlayout.appendChild( north );
        borderlayout.appendChild( center );
        
        appendChild( borderlayout );
        
        menu.addEventListener( AdministratorMenu.Events.ON_SELECT_APPLICATION, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                setApplication( (DefaultPane) t.getData() );
            }
        } );
        
        addEventListener( org.zkoss.zk.ui.event.Events.ON_CLIENT_INFO, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( t instanceof ClientInfoEvent )
                {
                    borderlayout.setWidth( ((ClientInfoEvent)t).getDesktopWidth() + "px" );
                    borderlayout.setHeight( ((ClientInfoEvent)t).getDesktopHeight() + "px" );
                    borderlayout.resize();
                }
            }
        } );
        
        lbLogout.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                ApplicationUtilities.logout();
            }
        } );
    }
    
    private AdministratorMenu menu = new AdministratorMenu();
    private ApplicationActionMenu actionsMenu = new ApplicationActionMenu();
    
    private Borderlayout innerBorderlayout = new Borderlayout();
    private Center innerCenter = new Center();
    private East innerEast = new East();
    
    private Borderlayout borderlayout = new Borderlayout();
    private Center center = new Center();
    private North north = new North();
}
