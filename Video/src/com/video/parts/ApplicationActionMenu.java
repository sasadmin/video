/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.parts;

import com.video.util.ApplicationAction;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Galimberti
 */
public class ApplicationActionMenu
    extends Vbox
{
    public ApplicationActionMenu()
    {
        setHflex( "true" );
        setVflex( "true" );
    }
    
    public void setActions( List<ApplicationAction> actions )
    {
        getChildren().clear();
        
        for ( ApplicationAction aa : actions )
        {
            Toolbarbutton bt = new Toolbarbutton();
            
            bt.setHeight( "40px" );
            bt.setWidth( "100%" );
            bt.setLabel( aa.getLabel() );
            bt.setImage( aa.getIcon() );
            bt.setTooltiptext( aa.getTooltip() );
            bt.setSclass( "action-button" );
            
            bt.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent( Event t ) throws Exception
                {
                    aa.onEvent( t );
                }
            } );
            
            appendChild( bt );
        }
    }
}
