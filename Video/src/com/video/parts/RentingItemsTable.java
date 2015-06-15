/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.parts;

import com.video.data.Item;
import com.video.data.RentingItem;
import com.video.data.Title;
import com.video.db.ItemManager;
import com.video.db.TitleManager;
import java.text.SimpleDateFormat;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author Galimberti
 */
public class RentingItemsTable
    extends Listbox
{
    private String[] columns = 
    {
        "Código",
        "Título",
        "Limite Entrega",
        "Entrega Efetiva"
    };

    public RentingItemsTable()
    {
        setItemRenderer( new Renderer() );

        Listhead listhead = new Listhead();

        Listheader codeHeader = new Listheader( columns[0] );
        codeHeader.setWidth( "80px" );
        listhead.appendChild( codeHeader );

        Listheader titleHeader = new Listheader( columns[1] );
        titleHeader.setHflex( "true" );
        listhead.appendChild( titleHeader );

        Listheader dueHeader = new Listheader( columns[2] );
        dueHeader.setWidth( "120px" );
        listhead.appendChild( dueHeader );

        Listheader returnHeader = new Listheader( columns[3] );
        returnHeader.setWidth( "120px" );
        listhead.appendChild( returnHeader );

        appendChild( listhead );
    }

    private class Renderer
        implements ListitemRenderer<RentingItem>
    {
        @Override
        public void render( Listitem lstm, RentingItem rentingItem, int i ) throws Exception
        {
            Item item = ItemManager.getInstance().getItem( rentingItem.getRef_item() );
            
            Title t = TitleManager.getInstance().getTitle( item.getRef_title() );
            
            new Listcell( "" + item.getId() ).setParent( lstm );
            new Listcell( t.getName() ).setParent( lstm );
            new Listcell( rentingItem.getDt_due() == null ? "n/d" : new SimpleDateFormat( "dd/MM/yyyy" ).format( rentingItem.getDt_due() ) ).setParent( lstm );
            new Listcell( rentingItem.getDt_return() == null ? "n/d" : new SimpleDateFormat( "dd/MM/yyyy" ).format( rentingItem.getDt_return() ) ).setParent( lstm );

            lstm.setValue( item );
        }
    }
}
