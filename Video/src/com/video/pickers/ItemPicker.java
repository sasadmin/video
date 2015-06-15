/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.pickers;

import com.video.parts.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Galimberti
 */
public class ItemPicker<T>
    extends DefaultPicker<T>
{
    private List<T> availableItems = new ArrayList();
    private List<T> listItems = new ArrayList();
    
    public ItemPicker( List<T> availableItems )
    {
        this.availableItems = availableItems;
        
        initComponents();
        
        filterItems();
    }
    
    @Override
    public boolean validateInput()
    {
        return listbox.getSelectedIndex() != -1;
    }

    @Override
    public T getSelectedItem()
    {
        if ( listbox.getSelectedIndex() != -1 )
        {
            return listItems.get( listbox.getSelectedIndex() );
        }
        
        return null;
    }
    
    private void filterItems()
    {
        listItems.clear();
        
        if ( !searchField.getValue().isEmpty() )
        {
            Stream<T> filtered = availableItems.stream().filter(p -> p.toString().toLowerCase().contains( searchField.getValue().trim().toLowerCase() ));
            
            listItems = filtered.collect( Collectors.toList() );
        }
        
        else
        {
            listItems.addAll( availableItems );
        }
        
        listbox.setModel( new SimpleListModel( listItems ) );
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        filterTable.setWidths( "80px" );
        
        searchField.setHflex( "true" );
        
        filterTable.createRow( "Filtrar:", searchField );
        
        listbox.setWidth( "100%" );
        listbox.setVflex( true );
        
        filterTable.setDynamicProperty( "width", "100%" );
        
        appendChild( filterTable );
        appendChild( listbox );
        
        searchField.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                filterItems();
            }
        } );
    }
    
    private Table filterTable = new Table();
    
    private Textbox searchField = new Textbox();
    private Listbox listbox = new Listbox();
}
