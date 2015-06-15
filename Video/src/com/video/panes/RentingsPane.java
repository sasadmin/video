package com.video.panes;

import com.video.data.Renting;
import com.video.data.User;
import com.video.db.RentingManager;
import com.video.db.UserManager;
import com.video.editors.EditorWindow;
import com.video.editors.RentingEditor;
import com.video.parts.Messagebox;
import com.video.parts.ItemSelector;
import com.video.parts.Table;
import com.video.reports.csv.RentingsReport;
import com.video.util.ApplicationAction;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.SimpleListModel;

public class RentingsPane
    extends DefaultPane
{
    private List<Renting> rentings = new ArrayList<Renting>();
    
    public RentingsPane()
    {
        initComponents();
        
        try
        {
            clientSelector.setItems( UserManager.getInstance().getNormalUsers() );
        }
        
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction refreshAction = new ApplicationAction( "/img/default_action.png", "Atualizar", "Atualizar Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        };
        
        ApplicationAction addAction = new ApplicationAction( "/img/default_action.png", "Adicionar", "Adicionar Locação" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EditorWindow.openEditor( RentingsPane.this, new RentingEditor(), new EditorCompletionCallback<Renting>( new Renting() )
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
        
        ApplicationAction editAction = new ApplicationAction( "/img/default_action.png", "Editar", "Editar Locação" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( getSelectedRenting() != null )
                {
                    EditorWindow.openEditor( RentingsPane.this, new RentingEditor(), new EditorCompletionCallback<Renting>( getSelectedRenting() )
                    {
                        @Override
                        public void performOk()
                        {
                            try
                            {
                                RentingManager.getInstance().updateRenting( getSource() );

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
            }
        };
        
        ApplicationAction deleteAction = new ApplicationAction( "/img/default_action.png", "Excluir", "Excluir Locação" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( ApplicationUtilities.getActiveUser().getType() == User.TYPE_ADMIN )
                {
                    try
                    {
                        if ( getSelectedRenting() != null )
                        {
                            RentingManager.getInstance().deleteRenting( getSelectedRenting() );

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
                    Messagebox.showMessage( "Sem permissão para deletar item!" );
                }
            }
        };
        
        ApplicationAction reportAction = new ApplicationAction( "/img/default_action.png", "Relatório", "Relatório" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Executions.getCurrent().sendRedirect( "/download/" + RentingsReport.generateReport( clientSelector.getSelectedItem() ).getName(), "_blank" );
            }
        };
        
        actions.add( refreshAction );
        actions.add( addAction );
        actions.add( editAction );
        actions.add( deleteAction );
        actions.add( reportAction );
        
        return actions;
    }
    
    public Renting getSelectedRenting()
    {
        if ( table.getSelectedIndex() != -1 )
        {
            return rentings.get( table.getSelectedIndex() );
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
            if ( clientSelector.getSelectedItem() != null )
            {
                table.setModel( new SimpleListModel( rentings = RentingManager.getInstance().getRentings( clientSelector.getSelectedItem().getId() ) ) );
            }
            
            else
            {
                table.setModel( new SimpleListModel( rentings = RentingManager.getInstance().getRentings() ) );
            }
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
        
        setApplicationName( "Locações" );
        
        table.setWidth( "100%" );
        table.setVflex( true );
        
        filterTable.setWidths( "80px" );
        
        clientSelector.setHflex( "true" );
        
        filterTable.createRow( "Cliente:", clientSelector );
        
        appendChild( filterTable );
        appendChild( table );
        
        clientSelector.addEventListener( ItemSelector.Events.ON_SELECT_ITEM, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        } );
    }
    
    private Table filterTable = new Table();
    
    private ItemSelector<User> clientSelector = new ItemSelector();
    
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
