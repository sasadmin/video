/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.panes;

import com.video.data.Item;
import com.video.data.Renting;
import com.video.db.ItemManager;
import com.video.db.RentingManager;
import com.video.db.TitleManager;
import com.video.editors.EditorWindow;
import com.video.editors.RentingEditor;
import com.video.parts.Table;
import com.video.util.ApplicationAction;
import com.video.util.EditorCompletionCallback;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Galimberti
 */
public class HomePane
    extends DefaultPane
{
    private List<Item> items = new ArrayList<Item>();
    
    public HomePane()
    {
        initComponents();
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction searchAction = new ApplicationAction( "/img/default_action.png", "Filtrar", "Filtrar Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        };
        
        ApplicationAction addAction = new ApplicationAction( "/img/default_action.png", "Locação", "Locar Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EditorWindow.openEditor( HomePane.this, new RentingEditor(), new EditorCompletionCallback<Renting>( new Renting() )
                {
                    @Override
                    public void performOk()
                    {
                        try
                        {
                            RentingManager.getInstance().addRenting( getSource() );
                            
                            refreshContent();
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
        };
        
        ApplicationAction editAction = new ApplicationAction( "/img/default_action.png", "Devolução", "Devolver Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
            }
        };
        
        actions.add( searchAction );
        actions.add( addAction );
        actions.add( editAction );
        
        return actions;
    }
    
    public Item getSelectedItem()
    {
        if ( table.getSelectedIndex() != -1 )
        {
            return items.get( table.getSelectedIndex() );
        }
        
        else
        {
            Messagebox.show( "É preciso selecionar um item na lista!" );
        }
        
        return null;
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            table.setModel( new SimpleListModel( items = ItemManager.getInstance().getItems( titlebox.getValue(), -1 ) ) );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        setApplicationName( "Home" );
        
        table.setWidth( "100%" );
        table.setVflex( true );
        
        filterTable.setWidths( "80px" );
        
        titlebox.setHflex( "true" );
        
        filterTable.createRow( "Título:", titlebox );
        
        appendChild( filterTable );
        appendChild( table );
        
        titlebox.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        } );
    }
    
    private Table filterTable = new Table();
    
    private Textbox titlebox = new Textbox();
    
    private ItemsTable table = new ItemsTable();
    
    private class ItemsTable
        extends Listbox
    {
        private String[] columns = 
        {
            "Código",
            "Título",
            "Mídia",
            "Situação"
        };
        
        public ItemsTable()
        {
            setItemRenderer( new Renderer() );
            
            Listhead listhead = new Listhead();
            
            Listheader codeHeader = new Listheader( columns[0] );
            codeHeader.setWidth( "80px" );
            listhead.appendChild( codeHeader );
            
            Listheader titleHeader = new Listheader( columns[1] );
            titleHeader.setHflex( "true" );
            listhead.appendChild( titleHeader );
            
            Listheader midiaHeader = new Listheader( columns[2] );
            midiaHeader.setWidth( "120px" );
            listhead.appendChild( midiaHeader );
            
            Listheader stateHeader = new Listheader( columns[3] );
            stateHeader.setWidth( "180px" );
            listhead.appendChild( stateHeader );
            
            appendChild( listhead );
        }
        
        private class Renderer
            implements ListitemRenderer<Item>
        {
            @Override
            public void render( Listitem lstm, Item item, int i ) throws Exception
            {
                new Listcell( "" + item.getId() ).setParent( lstm );
                new Listcell( TitleManager.getInstance().getTitle( item.getRef_title() ).getName() ).setParent( lstm );
                new Listcell( Item.MIDIAS[item.getMidia()] ).setParent( lstm );
                Listcell stateCell = new Listcell( Item.STATES[item.getState()] );
                stateCell.setParent( lstm );
                
                lstm.setValue( item );
            }
        }
    }
}
