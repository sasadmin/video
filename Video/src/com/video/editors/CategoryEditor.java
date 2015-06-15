package com.video.editors;

import com.video.data.Category;
import com.video.parts.Messagebox;
import com.video.parts.Table;
import org.zkoss.zul.Textbox;

public class CategoryEditor
    extends DefaultEditor<Category>
{
    public CategoryEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( textbox.getText() == null || textbox.getText().isEmpty() )
        {
            Messagebox.showMessage( "É preciso informar o nome!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void setSource( Category source )
    {
        textbox.setValue( source.getName() );
    }
    
    @Override
    public void getSource( Category source )
    {
        source.setName( textbox.getValue() );
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        textbox.setHflex( "true" );
        
        table.createRow( "Nome:", textbox );
        
        appendChild( table );
    }
    
    private Table table = new Table();
    
    private Textbox textbox = new Textbox();
}
