package com.video.editors;

import org.zkoss.zul.Div;

public abstract class DefaultEditor<T>
    extends Div
{
    public abstract boolean validateInput();
    public abstract void getSource( T source );
    public abstract void setSource( T source );
}
