/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.panes;

import com.video.data.Renting;
import com.video.db.RentingManager;
import com.video.db.UserManager;
import com.video.editors.EditorWindow;
import com.video.editors.RentingEditor;
import com.video.parts.Table;
import com.video.util.ApplicationAction;
import com.video.util.EditorCompletionCallback;
import java.text.SimpleDateFormat;
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
    private List<Renting> rentings = new ArrayList<Renting>();
    
    public HomePane()
    {
        initComponents();
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction searchAction = new ApplicationAction( "/img/default_action.png", "Atualizar", "Atualizar Itens" )
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
    
    public Renting getSelectedItem()
    {
        if ( table.getSelectedIndex() != -1 )
        {
            return rentings.get( table.getSelectedIndex() );
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
            table.setModel( new SimpleListModel( rentings = RentingManager.getInstance().getRentings() ) );
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
    
    private RentingsTable table = new RentingsTable();
    
    private class RentingsTable
        extends Listbox
    {
        private String[] columns = 
        {
            "Usuário",
            "Situação",
            "Data"
        };
        
        public RentingsTable()
        {
            setItemRenderer( new Renderer() );
            
            Listhead listhead = new Listhead();
            
            Listheader userHeader = new Listheader( columns[0] );
            userHeader.setWidth( "180px" );
            listhead.appendChild( userHeader );
            
            Listheader stateHeader = new Listheader( columns[1] );
            stateHeader.setWidth( "180px" );
            listhead.appendChild( stateHeader );
            
            Listheader dateHeader = new Listheader( columns[2] );
            dateHeader.setWidth( "180px" );
            listhead.appendChild( dateHeader );
            
            appendChild( listhead );
        }
        
        private class Renderer
            implements ListitemRenderer<Renting>
        {
            @Override
            public void render( Listitem lstm, Renting r, int i ) throws Exception
            {
                new Listcell( UserManager.getInstance().getUser( r.getRef_user() ).getName() ).setParent( lstm );
                new Listcell( Renting.STATES[r.getState()] ).setParent( lstm );
                new Listcell( new SimpleDateFormat( "dd/MM/yyyy" ).format( r.getDt_rent() ) ).setParent( lstm );
                
                lstm.setValue( r );
            }
        }
    }
}
