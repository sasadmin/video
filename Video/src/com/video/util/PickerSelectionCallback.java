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
public abstract class PickerSelectionCallback<T>
{
    public PickerSelectionCallback()
    {
    }
    
    public abstract void selectItem( T item );
}
