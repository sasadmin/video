package com.video.data;

import java.util.HashMap;
import java.util.Map;

public class ImdbData
{
    private Map<Object, Object> info = new HashMap<>();

    public ImdbData()
    {
    }

    public Object getPoster()
    {
        return info.get( "Poster" );
    }

    public Object getTitle()
    {
        return info.get( "Title" );
    }

    public Object getImdbRating()
    {
        return info.get( "imdbRating" );
    }

    public void addInfo( Object name, Object value )
    {
        info.put( name, value );
    }

    public Map<Object, Object> getMapInfo()
    {
        return info;
    }
}
