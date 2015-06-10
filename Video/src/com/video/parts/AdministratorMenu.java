/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.parts;

import com.video.panes.DefaultPane;
import com.video.panes.HomePane;
import com.video.panes.ItemsPane;
import com.video.panes.RentingsPane;
import com.video.panes.TitlesPane;
import com.video.panes.UsersPane;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Galimberti
 */
public class AdministratorMenu
    extends Toolbar
{
    public static class Events
    {
        public static final String ON_SELECT_APPLICATION = "onSelectApplication";
    }
    
    public AdministratorMenu()
    {
        initComponents();
    }
    
    private void setApplication( DefaultPane pane )
    {
        Executions.getCurrent().postEvent( new Event( Events.ON_SELECT_APPLICATION, this, pane ) );
    }
    
    private void unselectOthers( AdministratorMenuButton selected )
    {
        for ( Component c : getChildren() )
        {
            if ( c instanceof AdministratorMenuButton && c != selected )
            {
                ((AdministratorMenuButton)c).setSclass( "application-button" );
            }
        }
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        setSclass( "administrator-menu" );
        
        AdministratorMenuButton homeButton = new AdministratorMenuButton( new HomePane() );
        AdministratorMenuButton titlesButton = new AdministratorMenuButton( new TitlesPane() );
        AdministratorMenuButton itemsButton = new AdministratorMenuButton( new ItemsPane() );
        AdministratorMenuButton rentingsButton = new AdministratorMenuButton( new RentingsPane() );
        AdministratorMenuButton usersButton = new AdministratorMenuButton( new UsersPane() );
        
        appendChild( homeButton );
        appendChild( new Separator( "vertical" ) );
        appendChild( titlesButton );
        appendChild( new Separator( "vertical" ) );
        appendChild( itemsButton );
        appendChild( new Separator( "vertical" ) );
        appendChild( rentingsButton );
        appendChild( new Separator( "vertical" ) );
        appendChild( usersButton );
        appendChild( new Separator( "vertical" ) );
        
        Executions.getCurrent().postEvent( new Event( org.zkoss.zk.ui.event.Events.ON_CLICK, homeButton ) );
    }
    
    private class AdministratorMenuButton
        extends Toolbarbutton
    {
        private DefaultPane application;

        public AdministratorMenuButton( DefaultPane application )
        {
            this.application = application;

            setWidth( "70px" );
//            setHeight( "60px" );
            
            setLabel( application.getApplicationName() );

            setSclass( "application-button" );
            
            addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent( Event t ) throws Exception
                {
                    AdministratorMenuButton.this.setSclass( "application-button-selected" );
                    setApplication( application );
                    unselectOthers( AdministratorMenuButton.this );
                }
            } );
        }
    }
}