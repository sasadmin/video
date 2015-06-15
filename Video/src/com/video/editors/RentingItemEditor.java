/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.controllers.ImdbController;
import com.video.data.ImdbData;
import com.video.data.Item;
import com.video.data.RentingItem;
import com.video.data.Title;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import com.video.parts.ItemSelector;
import com.video.parts.Table;
import java.sql.Date;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Galimberti
 */
public class RentingItemEditor
    extends DefaultEditor<RentingItem>
{
    public RentingItemEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( itemList.getSelectedItem() == null )
        {
            Messagebox.show( "É preciso selecionar um Título!" );
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( RentingItem source )
    {
        source.setDt_due( dueBox.getValue() != null ? new Date( dueBox.getValue().getTime() ) : null );
        source.setDt_return( returnBox.getValue() != null ? new Date( returnBox.getValue().getTime() ) : null );
        source.setRef_item( itemList.getSelectedItem().getId() );
    }

    @Override
    public void setSource( RentingItem source )
    {
        try
        {
            itemList.setItems( ItemManager.getInstance().getAvailableItems() );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void chageOriginalTitle() throws Exception
    {
        try
        {
            image.getChildren().clear();
            tableInfo.getChildren().clear();

            Item selectedItem = itemList.getSelectedItem();
            
            Title selectedTitle = null;
            
            if ( selectedItem != null )
            {
                selectedTitle = TitleManager.getInstance().getTitle( selectedItem.getRef_title() );
            }
            
            if ( selectedTitle != null && !selectedTitle.getOriginal_title().isEmpty() )
            {
                ImdbData data = ImdbController.getImdbData( selectedTitle.getOriginal_title() );

                if ( data != null )
                {
                    Object poster = data.getPoster();

                    if ( poster != null && !poster.equals( "N/A" ) )
                    {
                        Image img = new Image( poster.toString() );
                        img.setHeight( "98px" );
                        img.setWidth( "98px" );

                        image.appendChild( img );
                    }
                    
                    else
                    {
                        Image img = new Image( "/img/image_not_found.png" );
                        img.setHeight( "98px" );
                        img.setWidth( "98px" );

                        image.appendChild( img );
                    }

                    for ( Object key : data.getMapInfo().keySet() )
                    {
                        Object value = data.getMapInfo().get( key );

                        Label vl = new Label( value != null ? value.toString() : "" );

                        tableInfo.createRow( key.toString(), vl );
                    }

                }
            }
        }
        catch ( WrongValueException e )
        {
            e.printStackTrace( System.err );
        }
    }
    
    private void initComponents()
    {
        itemList.setHflex( "true" );
        
        image.setWidth( "100px" );
        image.setHeight( "100px" );
        
        image.setStyle( "border: 1px solid gray" );
        
        Hbox hbox = new Hbox();

        hbox.setHflex( "true" );
        hbox.setSpacing( "10px" );

        hbox.appendChild( image );
        hbox.appendChild( table );
        
        Div container = new Div();
        container.setVflex( "true" );
        container.setWidth( "100%" );
        container.appendChild( tableInfo );
        container.setStyle( "overflow: auto; border: solid 1px gray;" );
        container.setHeight( "300px" );
        
        table.setWidths( "80px", "", "70px" );
        
        table.createRow( "Título:", itemList );
        table.createRow( "Limite Entrega:", dueBox );
        table.createRow( "Entrega Efetiva:", returnBox );
        
        appendChild( hbox );
        appendChild( new Label( "Informações:" ) );
        appendChild( container );
        
        itemList.addEventListener( ItemSelector.Events.ON_SELECT_ITEM, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                chageOriginalTitle();
            }
        } );
    }
    
    private Table table = new Table();
    
    private Table tableInfo = new Table();
    
    private Div image = new Div();
    
    private ItemSelector<Item> itemList = new ItemSelector();
    private Datebox dueBox = new Datebox();
    private Datebox returnBox = new Datebox();
}
