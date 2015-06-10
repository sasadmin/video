/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.panes;

import com.video.util.ApplicationAction;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Galimberti
 */
public class DefaultPane
    extends Div
{
    private String applicationName = "ApplicationName";
    
    public DefaultPane()
    {
        setVflex( "true" );
        setHflex( "true" );
    }
    
    public void refreshContent()
    {
    }
    
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction defaultAction = new ApplicationAction( "/img/default_action.png", "label", "tooltip" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Messagebox.show( "Not implemented yet!" );
            }
        };
        
        actions.add( defaultAction );
        actions.add( defaultAction );
        actions.add( defaultAction );
        
        return actions;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * @param applicationName the applicationName to set
     */
    public void setApplicationName( String applicationName )
    {
        this.applicationName = applicationName;
    }
}
