/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.util;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 *
 * @author Galimberti
 */
public abstract class ApplicationAction
    implements EventListener<Event>
{
    private String icon;
    private String label;
    private String tooltip;
    
    public ApplicationAction( String icon, String label, String tooltip )
    {
        this.icon = icon;
        this.label = label;
        this.tooltip = tooltip;
    }

    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    /**
     * @return the label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel( String label )
    {
        this.label = label;
    }

    /**
     * @return the tooltip
     */
    public String getTooltip()
    {
        return tooltip;
    }

    /**
     * @param tooltip the tooltip to set
     */
    public void setTooltip( String tooltip )
    {
        this.tooltip = tooltip;
    }
}
