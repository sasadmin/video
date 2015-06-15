package com.video.panes;

import com.video.data.Title;
import com.video.data.User;
import com.video.db.CategoryManager;
import com.video.db.TitleManager;
import com.video.editors.EditorWindow;
import com.video.editors.TitleEditor;
import com.video.parts.Messagebox;
import com.video.util.ApplicationAction;
import com.video.util.ApplicationUtilities;
import com.video.util.EditorCompletionCallback;
import java.text.SimpleDateFormat;
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
        
        ApplicationAction addAction = new ApplicationAction( "/img/default_action.png", "Adicionar", "Adicionar Título" )
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
        
        ApplicationAction editAction = new ApplicationAction( "/img/default_action.png", "Editar", "Editar Título" )
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
        
        ApplicationAction deleteAction = new ApplicationAction( "/img/default_action.png", "Excluir", "Excluir Título" )
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
                            TitleManager.getInstance().deleteTitle( getSelectedTitle() );

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
        
        actions.add( addAction );
        actions.add( editAction );
        actions.add( deleteAction );
        
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
