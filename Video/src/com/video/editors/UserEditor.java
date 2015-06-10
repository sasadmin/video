/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.editors;

import com.video.data.User;
import com.video.parts.Table;
import com.video.util.ApplicationUtilities;
import java.util.Arrays;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Galimberti
 */
public class UserEditor
    extends DefaultEditor<User>
{
    public UserEditor()
    {
        initComponents();
    }

    @Override
    public void getSource( User source )
    {
        source.setName( nameField.getValue() );
        source.setLogin( loginField.getValue() );
        source.setPassword( passwordField.getValue() );
        source.setRg( rgField.getValue() );
        source.setType( typebox.getSelectedIndex() );
    }

    @Override
    public void setSource( User source )
    {
        nameField.setValue( source.getName() );
        loginField.setValue( source.getLogin() );
        passwordField.setValue( source.getPassword());
        rgField.setValue( source.getRg() );
        typebox.setSelectedIndex( source.getType() );
    }

    @Override
    public boolean validateInput()
    {
        if ( nameField.getValue().isEmpty() )
        {
            Messagebox.show( "É preciso informar o nome!" );
            
            return false;
        }
        
        else if ( loginField.getValue().isEmpty() )
        {
            Messagebox.show( "É preciso informar o login!" );
            
            return false;
        }
        
        else if ( passwordField.getValue().isEmpty() )
        {
            Messagebox.show( "É preciso informar a senha!" );
            
            return false;
        }
        
        else if ( rgField.getValue().isEmpty() )
        {
            Messagebox.show( "É preciso informar o RG!" );
            
            return false;
        }
        
        return true;
    }
    
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        if ( ApplicationUtilities.getActiveUser().getType() == User.TYPE_ADMIN )
        {
            typebox.setModel( new SimpleListModel( User.TYPES ) );
        }
        
        else
        {
            typebox.setModel( new SimpleListModel( Arrays.asList( User.TYPES ).subList( 0, 1 ).toArray( new String[]{} ) ) );
            typebox.setSelectedIndex( User.TYPE_USER );
            typebox.setDisabled( true );
        }
        
        typebox.setHflex( "true" );
        typebox.setHflex( "true" );
        
        typebox.setMold( "select" );
        
        table.setWidths( "80px" );
        
        nameField.setHflex( "true" );
        loginField.setHflex( "true" );
        passwordField.setHflex( "true" );
        rgField.setHflex( "true" );
        
        passwordField.setType( "password" );
        
        table.createRow( "Nome:", nameField );
        table.createRow( "Login:", loginField );
        table.createRow( "Senha:", passwordField );
        table.createRow( "RG:", rgField );
        table.createRow( "Tipo:", typebox );
        
        appendChild( table );
    }
    
    private Table table = new Table();
    
    private Textbox loginField = new Textbox();
    private Textbox passwordField = new Textbox();
    private Textbox nameField = new Textbox();
    private Textbox rgField = new Textbox();
    private Listbox typebox = new Listbox();
}
