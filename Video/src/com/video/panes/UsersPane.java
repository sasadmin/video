package com.video.panes;

import com.video.data.User;
import com.video.db.UserManager;
import com.video.editors.EditorWindow;
import com.video.editors.UserEditor;
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

public class UsersPane
    extends DefaultPane
{
    private List<User> users = new ArrayList<User>();
    
    public UsersPane()
    {
        initComponents();
    }

    @Override
    public List<ApplicationAction> getActions()
    {
        List<ApplicationAction> actions = new ArrayList<ApplicationAction>();
        
        ApplicationAction addAction = new ApplicationAction( "/img/default_action.png", "Adicionar", "Adicionar Usuário" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EditorWindow.openEditor( UsersPane.this, new UserEditor(), new EditorCompletionCallback<User>( new User() )
                {
                    @Override
                    public void performOk()
                    {
                        try
                        {
                            UserManager.getInstance().addUser( getSource() );
                            
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
        
        ApplicationAction editAction = new ApplicationAction( "/img/default_action.png", "Editar", "Editar Usuário" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( getSelectedUser() != null )
                {
                    if ( ApplicationUtilities.getActiveUser().getType() != User.TYPE_ADMIN )
                    {
                        if ( getSelectedUser().getType() != User.TYPE_USER )
                        {
                            Messagebox.showMessage( "Sem permissão para editar o item!" );
                            
                            return;
                        }
                    }
                    
                    EditorWindow.openEditor( UsersPane.this, new UserEditor(), new EditorCompletionCallback<User>( getSelectedUser() )
                    {
                        @Override
                        public void performOk()
                        {
                            try
                            {
                                UserManager.getInstance().updateUser( getSource() );

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
        
        ApplicationAction deleteAction = new ApplicationAction( "/img/default_action.png", "Excluir", "Excluir Usuário" )
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                if ( ApplicationUtilities.getActiveUser().getType() == User.TYPE_ADMIN )
                {
                    try
                    {
                        if ( getSelectedUser() != null )
                        {
                            UserManager.getInstance().deleteUser( getSelectedUser() );

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
            table.setModel( new SimpleListModel( users = UserManager.getInstance().getUsers() ) );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    public User getSelectedUser()
    {
        if ( table.getSelectedIndex() != -1 )
        {
            return users.get( table.getSelectedIndex() );
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
        
        setApplicationName( "Usuários" );
        
        table.setHflex( "true" );
        table.setVflex( true );
        
        appendChild( table );
    }
    
    private UsersTable table = new UsersTable();
    
    private class UsersTable
        extends Listbox
    {
        private String[] columns = 
        {
            "Nome",
            "Tipo",
            "Data Criação"
        };
        
        public UsersTable()
        {
            setItemRenderer( new Renderer() );
            
            Listhead listhead = new Listhead();
            
            Listheader nameHeader = new Listheader( columns[0] );
            nameHeader.setHflex( "true" );
            listhead.appendChild( nameHeader );
            
            Listheader typeHeader = new Listheader( columns[1] );
            typeHeader.setWidth( "180px" );
            listhead.appendChild( typeHeader );
            
            Listheader dateHeader = new Listheader( columns[2] );
            dateHeader.setWidth( "180px" );
            listhead.appendChild( dateHeader );
            
            appendChild( listhead );
        }
        
        private class Renderer
            implements ListitemRenderer<User>
        {
            @Override
            public void render( Listitem lstm, User u, int i ) throws Exception
            {
                new Listcell( u.getName() ).setParent( lstm );
                new Listcell( User.TYPES[u.getType()] ).setParent( lstm );
                new Listcell( new SimpleDateFormat( "dd/MM/yyyy" ).format( u.getDt_created() ) ).setParent( lstm );
                
                lstm.setValue( u );
            }
        }
    }
}
