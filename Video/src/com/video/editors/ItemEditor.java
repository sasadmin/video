/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.controllers.ImdbController;
import com.video.data.ImdbData;
import com.video.data.Item;
import com.video.data.Title;
import com.video.db.TitleManager;
import com.video.parts.Table;
import java.util.List;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;

/**
 *
 * @author Galimberti
 */
public class ItemEditor
    extends DefaultEditor<Item>
{
    public ItemEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( titlebox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar o título!" );
            
            return false;
        }
        
        else if ( midiabox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar a mídia!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( Item source )
    {
        source.setRef_title( ((Title)titlebox.getModel().getElementAt( titlebox.getSelectedIndex() ) ).getId() );
        source.setMidia( midiabox.getSelectedIndex() );
    }

    @Override
    public void setSource( Item source )
    {
        List<Title> titles = null;
        
        try
        {
            titlebox.setModel( new SimpleListModel( titles = TitleManager.getInstance().getTitles() ) );
            midiabox.setSelectedIndex( source.getMidia() );
            
            if ( source.getId() != 0 )
            {
                titlebox.setSelectedIndex( titles.indexOf( TitleManager.getInstance().getTitle( source.getRef_title() ) ) );
                chageOriginalTitle();
            }
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void chageOriginalTitle()
    {
        try
        {
            image.getChildren().clear();
            tableInfo.getChildren().clear();

            Title selectedTitle = (Title)titlebox.getModel().getElementAt( titlebox.getSelectedIndex() );
            
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
        setHflex( "true" );
        setVflex( "true" );
        
        titlebox.setMold( "select" );
        midiabox.setMold( "select" );
        
        image.setWidth( "100px" );
        image.setHeight( "100px" );
        
        image.setStyle( "border: 1px solid gray" );
        
        titlebox.setHflex( "true" );
        midiabox.setHflex( "true" );
        
        midiabox.setModel( new SimpleListModel( Item.MIDIAS ) );
        
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

        table.setDynamicProperty( "width", "450px" );

        table.createRow( "Título:", titlebox );
        table.createRow( "Mídia:", midiabox );

        appendChild( hbox );
        appendChild( new Label( "Informações:" ) );
        appendChild( container );
        
        titlebox.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, new EventListener<Event>()
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
    
    private Listbox titlebox = new Listbox();
    private Listbox midiabox = new Listbox();
}
