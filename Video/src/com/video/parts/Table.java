/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.parts;

import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

/**
 *
 * @author Galimberti
 */
public class Table
    extends org.zkoss.zhtml.Table
{
    private String[] widths;
    
    public Table()
    {
        setDynamicProperty( "width", "100%" );
    }
    
    public void setWidths( String... widths )
    {
        this.widths = widths;
    }
    
    public void setColspan( int row, int column, int colspan )
    {
        Td td = (Td)getChildren().get( row ).getChildren().get( column );
        
        td.setDynamicProperty( "colspan", colspan );
    }
    
    public void createRow( String label, Component... components )
    {
        Tr tr = new Tr();
        tr.setDynamicProperty( "width", "100%" );
        
        Td td = new Td();
        
        td.setDynamicProperty( "width", widths != null && widths.length > 0 ? widths[0] : "80px" );
        td.appendChild( new Label( label ) );
        
        tr.appendChild( td );
        
        int col = 1;
        
        for ( Component c : components )
        {
            td = new Td();
            td.setDynamicProperty( "width", widths != null && widths.length > col ? widths[col] : "" );
            td.appendChild( c );
            tr.appendChild( td );
        }
        
        appendChild( tr );
    }
}
