/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.Item;
import com.video.data.Title;
import com.video.db.TitleManager;
import com.video.parts.Table;
import java.util.List;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;

/**
 *
 * @author Galimberti
 */
public class ItemEditor
    extends DefaultEditor<Item>
{
    public ItemEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( titlebox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar o título!" );
            
            return false;
        }
        
        else if ( midiabox.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso informar a mídia!" );
            
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( Item source )
    {
        source.setRef_title( ((Title)titlebox.getModel().getElementAt( titlebox.getSelectedIndex() ) ).getId() );
        source.setMidia( midiabox.getSelectedIndex() );
    }

    @Override
    public void setSource( Item source )
    {
        List<Title> titles = null;
        
        try
        {
            titlebox.setModel( new SimpleListModel( titles = TitleManager.getInstance().getTitles() ) );
            midiabox.setSelectedIndex( source.getMidia() );
            
            if ( source.getId() != 0 )
            {
                titlebox.setSelectedIndex( titles.indexOf( TitleManager.getInstance().getTitle( source.getRef_title() ) ) );
            }
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        titlebox.setMold( "select" );
        midiabox.setMold( "select" );
        
        titlebox.setHflex( "true" );
        midiabox.setHflex( "true" );
        
        midiabox.setModel( new SimpleListModel( Item.MIDIAS ) );
        
        table.createRow( "Título:", titlebox );
        table.createRow( "Mídia:", midiabox );
        
        appendChild( table );
    }
    
    private Table table = new Table();
    
    private Listbox titlebox = new Listbox();
    private Listbox midiabox = new Listbox();
}
