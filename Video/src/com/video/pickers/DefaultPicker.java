/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.pickers;

import org.zkoss.zul.Div;

/**
 *
 * @author Galimberti
 */
public abstract class DefaultPicker<T>
    extends Div
{
    public abstract boolean validateInput();
    public abstract T getSelectedItem();
}
