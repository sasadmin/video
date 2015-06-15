package com.video.editors;

import com.video.util.EditorCompletionCallback;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.South;
import org.zkoss.zul.Window;

public class EditorWindow
    extends Window
{
    public static void openEditor( Component owner, DefaultEditor editor, EditorCompletionCallback callback )
    {
        EditorWindow window = new EditorWindow( editor, callback );
        
        window.setParent( owner );
        
        window.doModal();
    }
    
    private DefaultEditor editor;
    private EditorCompletionCallback callback;
    
    public EditorWindow( DefaultEditor editor, EditorCompletionCallback callback )
    {
        this.editor = editor;
        this.callback = callback;
        
        editor.setSclass( "default-editor" );
        
        editor.setSource( callback.getSource() );
        
        initComponents();
        
        center.appendChild( editor );
    }
    
    private void performOk()
    {
        if ( editor.validateInput() )
        {
            Object source = callback.getSource();
            editor.getSource( source );
            callback.performOk();
            
            detach();
        }
    }
    
    private void performCancel()
    {
        callback.performCancel();
        
        detach();
    }
    
    private void initComponents()
    {
        setWidth( "640px" );
        setHeight( "480px" );
        
        setStyle( "border: solid 5px rgb(0,88,132)" );
        
        okButton.setLabel( "Ok" );
        cancelButton.setLabel( "Cancelar" );
        
        okButton.setWidth( "80px" );
        cancelButton.setWidth( "80px" );
        
        Hbox hbox = new Hbox();
        
        Div divFlex = new Div();
        
        divFlex.setHflex( "true" );
        hbox.setHflex( "true" );
        
        hbox.appendChild( divFlex );
        hbox.appendChild( okButton );
        hbox.appendChild( cancelButton );
        
        south.appendChild( hbox );
        
        borderlayout.setWidth( "100%" );
        borderlayout.setHeight( "100%" );
        
        borderlayout.setStyle( "border: none;" );
        center.setStyle( "border: none;" );
        south.setStyle( "border: none;" );
        
        borderlayout.appendChild( center );
        borderlayout.appendChild( south );
        
        appendChild( borderlayout );
        
        okButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                performOk();
            }
        } );
        
        cancelButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                performCancel();
            }
        } );
    }
    
    private Borderlayout borderlayout = new Borderlayout();
    
    private Center center = new Center();
    private South south = new South();
    
    private Button okButton = new Button();
    private Button cancelButton = new Button();
}
