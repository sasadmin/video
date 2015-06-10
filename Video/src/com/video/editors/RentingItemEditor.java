/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.Item;
import com.video.data.RentingItem;
import com.video.db.ItemManager;
import com.video.parts.Table;
import java.sql.Date;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;

/**
 *
 * @author Galimberti
 */
public class RentingItemEditor
    extends DefaultEditor<RentingItem>
{
    public RentingItemEditor()
    {
        initComponents();
    }
    
    @Override
    public boolean validateInput()
    {
        if ( itemList.getSelectedIndex() == -1 )
        {
            Messagebox.show( "É preciso selecionar um Título!" );
            return false;
        }
        
        return true;
    }

    @Override
    public void getSource( RentingItem source )
    {
        source.setDt_due( dueBox.getValue() != null ? new Date( dueBox.getValue().getTime() ) : null );
        source.setDt_return( returnBox.getValue() != null ? new Date( returnBox.getValue().getTime() ) : null );
        source.setRef_item( ((Item)itemList.getModel().getElementAt( itemList.getSelectedIndex() ) ).getId() );
    }

    @Override
    public void setSource( RentingItem source )
    {
        try
        {
            itemList.setModel( new SimpleListModel( ItemManager.getInstance().getAvailableItems() ) );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    private void initComponents()
    {
        itemList.setHflex( "true" );
        itemList.setMold( "select" );
        
        table.setWidths( "80px" );
        
        table.createRow( "Título:", itemList );
        table.createRow( "Limite Entrega:", dueBox );
        table.createRow( "Entrega Efetiva:", returnBox );
        
        appendChild( table );
    }
    
    private Table table = new Table();
    
    private Listbox itemList = new Listbox();
    private Datebox dueBox = new Datebox();
    private Datebox returnBox = new Datebox();
}
