/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.Renting;
import com.video.data.RentingItem;
import com.video.data.User;
import com.video.db.UserManager;
import com.video.parts.RentingItemsTable;
import com.video.parts.Table;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Galimberti
 */
public class RentingEditor
    extends DefaultEditor<Renting>
{
    private List<RentingItem> items = new ArrayList();
    
    public RentingEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( items.isEmpty() )
        {
            Messagebox.show( "É preciso ter ao menos um item na lista!" );
            
            return false;
        }
        
        else if ( userbox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar o cliente!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( Renting source )
    {
        source.setCost( costbox.getValue() );
        source.setRef_user( ((User)userbox.getModel().getElementAt( userbox.getSelectedIndex() ) ).getId() );
        source.setRentingItems( items );
        source.setRef_operator( ApplicationUtilities.getActiveUser().getId() );
    }

    @Override
    public void setSource( Renting source )
    {
        List<User> users = null;
        
        try
        {
            userbox.setModel( new SimpleListModel( users = UserManager.getInstance().getNormalUsers() ) );
        
            if ( source.getId() > 0 )
            {
                costbox.setValue( source.getCost() );
                userbox.setSelectedIndex( users.indexOf( UserManager.getInstance().getUser( source.getRef_user() ) ) );
                
                rentingTable.setModel( new SimpleListModel( items = new ArrayList( source.getRentingItems() ) ) );
            }
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void addItem()
    {
        EditorWindow.openEditor( RentingEditor.this, new RentingItemEditor(), new EditorCompletionCallback<RentingItem>( new RentingItem() )
        {
            @Override
            public void performOk()
            {
                try
                {
                    items.add( getSource() );
                    
                    rentingTable.setModel( new SimpleListModel( items ) );
                }

                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void performCancel()
            {
            }
        });
    }
    
    private void deleteItem()
    {
        if ( rentingTable.getSelectedIndex() != -1 )
        {
            items.remove( rentingTable.getSelectedIndex() );
            
            rentingTable.setModel( new SimpleListModel( items ) );
        }
        
        else
        {
            Messagebox.show( "É preciso selecionar um item na lista!" );
        }
    }
    
    private void returnItem()
    {
        if ( rentingTable.getSelectedIndex() != -1 )
        {
            items.get( rentingTable.getSelectedIndex() ).setDt_return( new Date( System.currentTimeMillis() ) );
            
            rentingTable.setModel( new SimpleListModel( items ) );
        }
        
        else
        {
            Messagebox.show( "É preciso selecionar um item na lista!" );
        }
    }
    
    private void initComponents()
    {
        Vbox vbox = new Vbox();
        
        vbox.setWidth( "80px" );
        vbox.setVflex( "true" );
        
        vbox.appendChild( addButton );
        vbox.appendChild( deleteButton );
        vbox.appendChild( returnButton );
        
        Hbox hbox = new Hbox();
        
        hbox.setVflex( "true" );
        hbox.setHflex( "true" );
        
        hbox.appendChild( rentingTable );
        hbox.appendChild( vbox );
        
        rentingTable.setHeight( "320px" );
        rentingTable.setWidth( "480px" );
        
        userbox.setMold( "select" );
        userbox.setHflex( "true" );
        costbox.setHflex( "true" );
        
        table.setWidths( "80px" );
        
        table.createRow( "Cliente:", userbox );
        table.createRow( "Valor Total:", costbox );
        
        appendChild( table );
        appendChild( hbox );
        
        addButton.setLabel( "Adicionar" );
        deleteButton.setLabel( "Remover" );
        returnButton.setLabel( "Devolver" );
        
        addButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                addItem();
            }
        } );
        
        deleteButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                deleteItem();
            }
        } );
        
        returnButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                returnItem();
            }
        } );
    }
    
    private Table table = new Table();
    
    private Button addButton = new Button();
    private Button deleteButton = new Button();
    private Button returnButton = new Button();
    
    private Listbox userbox = new Listbox();
    private Doublebox costbox = new Doublebox();
    
    private RentingItemsTable rentingTable = new RentingItemsTable();
}
