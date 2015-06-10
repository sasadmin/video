/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.Category;
import com.video.parts.Table;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Galimberti
 */
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
            Messagebox.show( "É preciso informar o nome!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void setSource( Category source )
    {
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
