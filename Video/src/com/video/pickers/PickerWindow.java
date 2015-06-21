/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.pickers;

import com.video.util.PickerSelectionCallback;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.South;
import org.zkoss.zul.Window;

/**
 *
 * @author Galimberti
 */
public class PickerWindow
    extends Window
{
    public static void openPicker( Component owner, DefaultPicker picker, PickerSelectionCallback callback )
    {
        PickerWindow window = new PickerWindow( picker, callback );
        
        window.setParent( owner );
        
        window.doModal();
    }
    
    private DefaultPicker picker;
    private PickerSelectionCallback callback;
    
    public PickerWindow( DefaultPicker picker, PickerSelectionCallback callback )
    {
        this.picker = picker;
        this.callback = callback;
        
        picker.setSclass( "default-picker" );
        
        initComponents();
        
        center.appendChild( picker );
    }
    
    private void selectItem()
    {
        if ( picker.validateInput() )
        {
            callback.selectItem( picker.getSelectedItem() );
            
            detach();
        }
    }
    
    private void performCancel()
    {
        detach();
    }
    
    private void initComponents()
    {
        setWidth( "580px" );
        setHeight( "420px" );
        
        setStyle( "border: solid 5px rgb(0,88,132)" );
        
        okButton.setLabel( "Ok" );
        cancelButton.setLabel( "Cancelar" );
        
        okButton.setWidth( "80px" );
        cancelButton.setWidth( "80px" );
        
        Hbox hbox = new Hbox();
        
        Div divFlex = new Div();
        
        divFlex.setHflex( "true" );
        hbox.setHflex( "true" );
        
        hbox.appendChild( divFlex );
        hbox.appendChild( okButton );
        hbox.appendChild( cancelButton );
        
        south.appendChild( hbox );
        
        borderlayout.setWidth( "100%" );
        borderlayout.setHeight( "100%" );
        
        borderlayout.setStyle( "border: none;" );
        center.setStyle( "border: none;" );
        south.setStyle( "border: none;" );
        
        borderlayout.appendChild( center );
        borderlayout.appendChild( south );
        
        appendChild( borderlayout );
        
        okButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                selectItem();
            }
        } );
        
        cancelButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                performCancel();
            }
        } );
    }
    
    private Borderlayout borderlayout = new Borderlayout();
    
    private Center center = new Center();
    private South south = new South();
    
    private Button okButton = new Button();
    private Button cancelButton = new Button();
}
