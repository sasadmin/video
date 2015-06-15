package com.video.editors;

import com.video.controllers.ImdbController;
import com.video.data.Category;
import com.video.data.ImdbData;
import com.video.data.Title;
import com.video.db.CategoryManager;
import com.video.parts.Messagebox;
import com.video.parts.ItemSelector;
import com.video.parts.Table;
import com.video.util.EditorCompletionCallback;
import java.sql.Date;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

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

                    combobox.setItems( CategoryManager.getInstance().getCategories() );
                    
                    combobox.setSelectedItem( getSource() );
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
        } );
    }

    @Override
    public boolean validateInput()
    {
        if ( titleTextbox.getText() == null || titleTextbox.getText().isEmpty() )
        {
            Messagebox.showMessage( "É preciso informar o título!" );

            return false;
        }

        else if ( combobox.getSelectedItem() == null )
        {
            Messagebox.showMessage("É preciso informar o gênero!" );

            return false;
        }

        else if ( datebox.getValue() == null )
        {
            Messagebox.showMessage( "É preciso informar a data de lançamento!" );

            return false;
        }

        return true;
    }

    @Override
    public void setSource( Title source )
    {
        try
        {
            combobox.setItems( CategoryManager.getInstance().getCategories() );

            if ( source.getId() > 0 )
            {
                titleTextbox.setText( source.getName() );
                originalTitletextbox.setText( source.getOriginal_title() );
                combobox.setSelectedItem( CategoryManager.getInstance().getCategory( source.getCategory() ) );
                datebox.setValue( source.getDtReleased() );
                
                chageOriginalTitle();
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
        source.setName( titleTextbox.getValue() );
        source.setOriginal_title( originalTitletextbox.getValue() );
        source.setCategory( combobox.getSelectedItem().getId() );
        source.setDtReleased( new Date( datebox.getValue().getTime() ) );
    }

    private void chageOriginalTitle()
    {
        try
        {
            image.getChildren().clear();
            tableInfo.getChildren().clear();

            if ( !originalTitletextbox.getText().isEmpty() )
            {
                ImdbData data = ImdbController.getImdbData( originalTitletextbox.getText() );

                if ( data != null )
                {
                    Object poster = data.getPoster();

                    if ( poster != null && !poster.equals( "N/A" ) )
                    {
                        Image img = new Image( poster.toString() );
                        img.setHeight( "98px" );
                        img.setWidth( "98px" );

                        image.appendChild( img );
                        
                        if ( !originalTitletextbox.getText().equals(  data.getTitle().toString() ) )
                        {
                            originalTitletextbox.setValue( data.getTitle().toString() );
                        }
                    }
                    
                    else
                    {
                        Image img = new Image( "/img/image_not_found.png" );
                        img.setHeight( "98px" );
                        img.setWidth( "98px" );

                        image.appendChild( img );
                    }

                    for ( Object key : data.getMapInfo().keySet() )
                    {
                        Object value = data.getMapInfo().get( key );

                        Label vl = new Label( value != null ? value.toString() : "" );

                        tableInfo.createRow( key.toString(), vl );
                    }

                }
            }
        }
        catch ( WrongValueException e )
        {
            e.printStackTrace( System.err );
        }
    }

    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );

        button.setLabel( "Novo" );

        image.setWidth( "100px" );
        image.setHeight( "100px" );
        
        image.setStyle( "border: 1px solid gray" );

        titleTextbox.setHflex( "true" );
        originalTitletextbox.setHflex( "true" );
        combobox.setHflex( "true" );

        Hbox hbox = new Hbox();

        hbox.setHflex( "true" );
        hbox.setSpacing( "10px" );

        hbox.appendChild( image );
        hbox.appendChild( table );

        Div container = new Div();
        container.setVflex( "true" );
        container.setWidth( "100%" );
        container.appendChild( tableInfo );
        container.setStyle( "overflow: auto; border: solid 1px gray;" );
        container.setHeight( "300px" );

        table.setWidths( "80px", "", "70px" );

        table.setDynamicProperty( "width", "450px" );

        table.createRow( "Título:", titleTextbox );
        table.createRow( "Título original:", originalTitletextbox );
        table.createRow( "Gênero:", combobox, button );
        table.createRow( "Lançamento:", datebox );

        table.setColspan( 0, 1, 2 );
        table.setColspan( 2, 1, 2 );

        appendChild( hbox );
        appendChild( new Label( "Informações:" ) );
        appendChild( container );

        button.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                addCategory();
            }
        } );

        originalTitletextbox.addEventListener( Events.ON_BLUR, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event event ) throws Exception
            {
                chageOriginalTitle();
            }
        } );
    }

    private Table table = new Table();
    private Table tableInfo = new Table();

    private Button button = new Button();
    private Div image = new Div();

    private Textbox titleTextbox = new Textbox();
    private Textbox originalTitletextbox = new Textbox();
    private ItemSelector<Category> combobox = new ItemSelector();
    private Datebox datebox = new Datebox();
}
