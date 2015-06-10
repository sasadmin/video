/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.Category;
import com.video.data.Title;
import com.video.db.CategoryManager;
import com.video.parts.Table;
import com.video.util.EditorCompletionCallback;
import java.sql.Date;
import java.util.List;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Galimberti
 */
public class TitleEditor
    extends DefaultEditor<Title>
{
    public TitleEditor()
    {
        initComponents();
    }
    
    private void addCategory()
    {
        EditorWindow.openEditor( this, new CategoryEditor(), new EditorCompletionCallback<Category>( new Category() )
        {
            @Override
            public void performOk()
            {
                try
                {
                    CategoryManager.getInstance().addCategory( getSource() );

                    combobox.setModel( new SimpleListModel( CategoryManager.getInstance().getCategories() ) );
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
    
    @Override
    public boolean validateInput()
    {
        if ( textbox.getText() == null || textbox.getText().isEmpty() )
        {
            Messagebox.show( "É preciso informar o título!" );
            
            return false;
        }
        
        else if ( combobox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar o gênero!" );
            
            return false;
        }
        
        else if ( datebox.getValue() == null )
        {
            Messagebox.show( "É preciso informar a data de lançamento!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void setSource( Title source )
    {
        List<Category> categories = null;
        
        try
        {
            combobox.setModel( new SimpleListModel( categories = CategoryManager.getInstance().getCategories() ) );
        
            if ( source.getId() > 0 )
            {
                textbox.setText( source.getName() );
                combobox.setSelectedIndex( categories.indexOf( CategoryManager.getInstance().getCategory( source.getCategory() ) ) );
                datebox.setValue( source.getDtReleased() );
            }
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void getSource( Title source )
    {
        source.setName( textbox.getValue() );
        source.setCategory( ((Category)combobox.getModel().getElementAt( combobox.getSelectedIndex() ) ).getId() );
        source.setDtReleased( new Date( datebox.getValue().getTime() ) );
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        button.setLabel( "Novo" );
        
        imgButton.setUpload( "true" );
        
        imgButton.setWidth( "96px" );
        imgButton.setHeight( "96px" );
        
        combobox.setMold( "select" );
        
        textbox.setHflex( "true" );
        combobox.setHflex( "true" );
        
        Hbox hbox = new Hbox();
        
        hbox.setHflex( "true" );
        hbox.setSpacing( "10px" );
        
        hbox.appendChild( imgButton );
        hbox.appendChild( table );
        
        table.setWidths( "80px", "", "70px" );
        
        table.setDynamicProperty( "width", "450px" );
        
        table.createRow( "Título:", textbox );
        table.createRow( "Gênero:", combobox, button );
        table.createRow( "Lançamento:", datebox );
        
        table.setColspan( 0, 1, 2 );
        table.setColspan( 2, 1, 2 );
        
        appendChild( hbox );
        
        button.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                addCategory();
            }
        } );
        
        imgButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_UPLOAD, new EventListener<UploadEvent>()
        {
            @Override
            public void onEvent( UploadEvent t ) throws Exception
            {
                imgButton.setImageContent( new AImage( "imagem", t.getMedia().getByteData() ) );
            }
        } );
    }
    
    private Table table = new Table();
    
    private Button button = new Button();
    private Button imgButton = new Button();
    
    private Textbox textbox = new Textbox();
    private Listbox combobox = new Listbox();
    private Datebox datebox = new Datebox();
}
