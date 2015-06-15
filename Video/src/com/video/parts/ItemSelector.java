/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.parts;

import com.video.pickers.ItemPicker;
import com.video.pickers.PickerWindow;
import com.video.util.PickerSelectionCallback;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Galimberti
 */
public class ItemSelector<T>
    extends Div
{
    public static class Events
    {
        public static String ON_SELECT_ITEM = "onSelectItem";
    }
    
    private T selectedItem = null;
    private List<T> availableItems = new ArrayList();
    
    public ItemSelector()
    {
        initComponents();
    }
    
    public void setItems( List<T> availableItems )
    {
        this.availableItems = availableItems;
    }
    
    public T getSelectedItem()
    {
        return selectedItem;
    }
    
    public void setSelectedItem( T item )
    {
        selectedItem = item;
        
        if ( selectedItem != null )
        {
            textbox.setValue( selectedItem.toString() );
        }
        
        else
        {
            textbox.setValue( "" );
        }
        
        Executions.getCurrent().postEvent( new Event( Events.ON_SELECT_ITEM, this ) );
    }
    
    private void pickItem()
    {
        PickerWindow.openPicker( this, new ItemPicker( availableItems ), new PickerSelectionCallback<T>()
        {
            @Override
            public void selectItem( T item )
            {
                setSelectedItem( item );
            }
        } );
    }
    
    private void clearItem()
    {
        setSelectedItem( null );
    }
    
    private void initComponents()
    {
        Hbox hbox = new Hbox();
        
        hbox.setHflex( "true" );
        hbox.setHeight( "24px" );
        
        hbox.appendChild( textbox );
        hbox.appendChild( tbPick );
        hbox.appendChild( tbClear );
        
        textbox.setHflex( "true" );
        textbox.setReadonly( true );
        tbPick.setImage( "/img/tb_folder.png" );
        tbClear.setImage( "/img/tb_clear.png" );
        
        appendChild( hbox );
        
        tbPick.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                pickItem();
            }
        } );
        
        tbClear.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                clearItem();
            }
        } );
    }
    
    private Textbox textbox = new Textbox();
    private Toolbarbutton tbPick = new Toolbarbutton();
    private Toolbarbutton tbClear = new Toolbarbutton();
}
