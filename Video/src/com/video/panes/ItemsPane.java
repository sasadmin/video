package com.video.panes;

import com.video.data.Item;
import com.video.data.User;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import com.video.editors.EditorWindow;
import com.video.editors.ItemEditor;
import com.video.parts.Messagebox;
import com.video.parts.Table;
import com.video.util.ApplicationAction;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

public class ItemsPane
    extends DefaultPane
{
    private List<Item> items = new ArrayList<Item>();
    
    public ItemsPane()
    {
        initComponents();
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction filterAction = new ApplicationAction( "/img/default_action.png", "Filtrar", "Filtrar Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        };
        
        ApplicationAction addAction = new ApplicationAction( "/img/default_action.png", "Adicionar", "Adicionar Item" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EditorWindow.openEditor( ItemsPane.this, new ItemEditor(), new EditorCompletionCallback<Item>( new Item() )
                {
                    @Override
                    public void performOk()
                    {
                        try
                        {
                            ItemManager.getInstance().addItem( getSource() );
                            
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
        
        ApplicationAction deleteAction = new ApplicationAction( "/img/default_action.png", "Excluir", "Excluir Item" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( ApplicationUtilities.getActiveUser().getType() == User.TYPE_ADMIN )
                {
                    try
                    {
                        if ( getSelectedItem() != null )
                        {
                            ItemManager.getInstance().deleteItem( getSelectedItem() );

                            refreshContent();
                        }
                    }

                    catch ( Exception e )
                    {
                        e.printStackTrace();
                    }
                }
                
                else
                {
                    Messagebox.showMessage("Sem permissão para deletar item!" );
                }
            }
        };
        
        actions.add( filterAction );
        actions.add( addAction );
        actions.add( deleteAction );
        
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
            Messagebox.showMessage( "É preciso selecionar um item na lista!" );
        }
        
        return null;
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            table.setModel( new SimpleListModel( items = ItemManager.getInstance().getItems( textbox.getValue(), statebox.getSelectedIndex() ) ) );
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
        
        setApplicationName( "Items" );
        
        table.setWidth( "100%" );
        table.setVflex( true );
        
        filterTable.setWidths( "80px" );
        
        statebox.setModel( new SimpleListModel( Item.STATES ) );
        
        statebox.setMold( "select" );
        statebox.setHflex( "true" );
        textbox.setHflex( "true" );
        
        filterTable.createRow( "Título:", textbox );
        filterTable.createRow( "Situação:", statebox );
        
        appendChild( filterTable );
        appendChild( table );
    }
    
    private Table filterTable = new Table();
    
    private Listbox statebox = new Listbox();
    private Textbox textbox = new Textbox();
    
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
                new Listcell( Item.STATES[item.getState()] ).setParent( lstm );
                
                lstm.setValue( item );
            }
        }
    }
}
