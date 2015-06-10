/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.util;

/**
 *
 * @author Galimberti
 */
public abstract class EditorCompletionCallback<T>
{
    private T source;
    
    public EditorCompletionCallback( T source )
    {
        this.source = source;
    }
    
    public T getSource()
    {
        return source;
    }
    
    public abstract void performOk();
    public abstract void performCancel();
}
