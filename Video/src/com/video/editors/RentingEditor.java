package com.video.editors;

import com.video.controllers.ConfigurationManager;
import com.video.data.Item;
import com.video.data.Renting;
import com.video.data.RentingItem;
import com.video.data.Title;
import com.video.data.User;
import com.video.db.ItemManager;
import com.video.db.RentingManager;
import com.video.db.TitleManager;
import com.video.db.UserManager;
import com.video.parts.Messagebox;
import com.video.parts.ItemSelector;
import com.video.parts.RentingItemsTable;
import com.video.parts.Table;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Vbox;

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
        try
        {
            if ( items.isEmpty() )
            {
                Messagebox.showMessage("Ã‰ preciso ter ao menos um item na lista!" );

                return false;
            }

            else if ( userbox.getSelectedItem() == null )
            {
                Messagebox.showMessage("Ã‰ preciso informar o cliente!" );

                return false;
            }

            else if ( RentingManager.getInstance().hasPendingRentings( userbox.getSelectedItem().getId() ) )
            {
                Messagebox.showMessage( "Cliente com dÃ©bitos!" );
                
                return false;
            }
        }
        
        catch ( Exception e )
        {
            Messagebox.showMessage(e.getMessage() );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( Renting source )
    {
        //tette
        source.setCost( costbox.getValue() != null ? costbox.getValue() : 0.0 );
        source.setCurrent_payment( paymentbox.getValue() != null ? paymentbox.getValue() : 0.0 );
        source.setRef_user( userbox.getSelectedItem().getId() );
        source.setRentingItems( items );
        source.setRef_operator( ApplicationUtilities.getActiveUser().getId() );
    }

    @Override
    public void setSource( Renting source )
    {
        try
        {
            userbox.setItems( UserManager.getInstance().getNormalUsers() );
            returnButton.setVisible( source.getId() > 0 );
        
            if ( source.getId() > 0 )
            {
                costbox.setValue( source.getCost() );
                paymentbox.setValue( source.getCurrent_payment() );
                userbox.setSelectedItem( UserManager.getInstance().getUser( source.getRef_user() ) );
                
                rentingTable.setModel( new SimpleListModel( items = new ArrayList( source.getRentingItems() ) ) );
            }
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void updateCost() throws Exception
    {
        double totalCost = 0;
        
        for ( RentingItem ri : items )
        {
            Item i = ItemManager.getInstance().getItem( ri.getRef_item() );
            
            Title t = TitleManager.getInstance().getTitle( i.getRef_title() );
            
            double age = TimeUnit.DAYS.convert( System.currentTimeMillis() - t.getDtReleased().getTime(), TimeUnit.MILLISECONDS );
            
            Double cost = getCost( i.getMidia() == Item.MIDIA_BLURAY ? "bluray" : "dvd" ,(int)( age / 31 ) );
            
            totalCost += cost;
        }
        
        costbox.setValue( totalCost );
    }
    
    private Double getCost( String midia, int months )
    {
        String value = ConfigurationManager.getInstance().getProperty( midia + ".age." + months );
        
        return value != null ? Double.valueOf( value ) : getCost( midia, --months );
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
                    if ( !items.contains( getSource() ) )
                    {
                        items.add( getSource() );
                        
                        rentingTable.setModel( new SimpleListModel( items ) );

                        updateCost();
                    }
                    
                    else
                    {
                        Messagebox.showMessage( "TÃ­tulo jÃ¡ adicionado na locaÃ§Ã£o!" );
                    }
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
    
    private void deleteItem() throws Exception
    {
        if ( rentingTable.getSelectedIndex() != -1 )
        {
            items.remove( rentingTable.getSelectedIndex() );
            
            rentingTable.setModel( new SimpleListModel( items ) );
            
            updateCost();
        }
        
        else
        {
            Messagebox.showMessage( "Ã‰ preciso selecionar um item na lista!" );
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
            Messagebox.showMessage( "Ã‰ preciso selecionar um item na lista!" );
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
        
        userbox.setHflex( "true" );
        costbox.setHflex( "true" );
        paymentbox.setHflex( "true" );
        
        table.setWidths( "80px" );
        
        table.createRow( "Cliente:", userbox );
        table.createRow( "Valor Total:", costbox );
        table.createRow( "Valor Pago:", paymentbox );
        
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
    
    private ItemSelector<User> userbox = new ItemSelector();
    private Doublebox costbox = new Doublebox();
    private Doublebox paymentbox = new Doublebox();
    
    private RentingItemsTable rentingTable = new RentingItemsTable();
}
