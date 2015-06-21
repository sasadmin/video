package com.video.panes;

import com.video.data.Title;
import com.video.data.User;
import com.video.db.CategoryManager;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import com.video.editors.EditorWindow;
import com.video.editors.TitleEditor;
import com.video.parts.Messagebox;
import com.video.reports.csv.TitlesReport;
import com.video.util.ApplicationAction;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.SimpleListModel;

public class TitlesPane
    extends DefaultPane
{
    private List<Title> titles = new ArrayList<Title>();
    
    public TitlesPane()
    {
        initComponents();
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction refreshAction = new ApplicationAction( "/img/tb_refresh.png", "Atualizar", "Atualizar Itens" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                refreshContent();
            }
        };
        
        ApplicationAction addAction = new ApplicationAction( "/img/tb_add.png", "Adicionar", "Adicionar Título" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EditorWindow.openEditor( TitlesPane.this, new TitleEditor(), new EditorCompletionCallback<Title>( new Title() )
                {
                    @Override
                    public void performOk()
                    {
                        try
                        {
                            TitleManager.getInstance().addTitle( getSource() );
                            
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
        
        ApplicationAction editAction = new ApplicationAction( "/img/tb_edit.png", "Editar", "Editar Título" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Title title = getSelectedTitle();
                
                if ( title != null )
                {
                    EditorWindow.openEditor( TitlesPane.this, new TitleEditor(), new EditorCompletionCallback<Title>( title )
                    {
                        @Override
                        public void performOk()
                        {
                            try
                            {
                                TitleManager.getInstance().updateTitle( getSource() );

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
        
        ApplicationAction deleteAction = new ApplicationAction( "/img/tb_delete.png", "Excluir", "Excluir Título" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( ApplicationUtilities.getActiveUser().getType() == User.TYPE_ADMIN )
                {
                    try
                    {
                        if ( getSelectedTitle() != null )
                        {
                            if ( ItemManager.getInstance().getItemsForTitle( getSelectedTitle().getId(), -1 ).isEmpty() )
                            {
                                TitleManager.getInstance().deleteTitle( getSelectedTitle() );

                                refreshContent();
                    
                            }
                            
                            else
                            {
                                Messagebox.showMessage( "Título com vínculos não pode ser excluído!" );
                            }
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
        
        ApplicationAction exportAction = new ApplicationAction( "/img/tb_csv.png", "Exportar", "Exportar para csv" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Executions.getCurrent().sendRedirect( "/download/" + TitlesReport.generateReport().getName(), "_blank" );
            }
        };
        
        ApplicationAction reportAction = new ApplicationAction( "/img/tb_report.png", "Relatório", "Imprimir Relatório" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Clients.evalJavaScript( "redirectReport('" + com.video.reports.pdf.TitlesReport.getJSONReport() + "')" );
            }
        };
        
        actions.add( refreshAction );
        actions.add( addAction );
        actions.add( editAction );
        actions.add( deleteAction );
        actions.add( exportAction );
        actions.add( reportAction );
        
        return actions;
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            table.setModel( new SimpleListModel( titles = TitleManager.getInstance().getTitles() ) );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    public Title getSelectedTitle()
    {
        if ( table.getSelectedIndex() != -1 )
        {
            return titles.get( table.getSelectedIndex() );
        }
        
        else
        {
            Messagebox.showMessage( "É preciso selecionar um item na lista!" );
        }
        
        return null;
    }
    
    private void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        setApplicationName( "Títulos" );
        
        table.setHflex( "true" );
        table.setVflex( true );
        
        appendChild( table );
    }
    
    private TitlesTable table = new TitlesTable();
    
    private class TitlesTable
        extends Listbox
    {
        private String[] columns = 
        {
            "Título",
            "Gênero",
            "Data Lançamento"
        };
        
        public TitlesTable()
        {
            setItemRenderer( new Renderer() );
            
            Listhead listhead = new Listhead();
            
            Listheader titleHeader = new Listheader( columns[0] );
            titleHeader.setHflex( "true" );
            listhead.appendChild( titleHeader );
            
            Listheader categoryHeader = new Listheader( columns[1] );
            categoryHeader.setWidth( "180px" );
            listhead.appendChild( categoryHeader );
            
            Listheader dateHeader = new Listheader( columns[2] );
            dateHeader.setWidth( "180px" );
            listhead.appendChild( dateHeader );
            
            appendChild( listhead );
        }
        
        private class Renderer
            implements ListitemRenderer<Title>
        {
            @Override
            public void render( Listitem lstm, Title t, int i ) throws Exception
            {
                new Listcell( t.getName() ).setParent( lstm );
                new Listcell( CategoryManager.getInstance().getCategory( t.getCategory() ).getName() ).setParent( lstm );
                new Listcell( new SimpleDateFormat( "dd/MM/yyyy" ).format( t.getDtReleased() ) ).setParent( lstm );
                
                lstm.setValue( t );
            }
        }
    }
}
