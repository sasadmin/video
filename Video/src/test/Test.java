/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.video.data.User;
import com.video.db.UserManager;

/**
 *
 * @author Galimberti
 */
public class Test
{
    public static void main( String[] args )
    {
        try
        {
            User u = new User();
            
            u.setLogin( "sas" );
            u.setPassword( "admin" );
            u.setName( "Administrador" );
            u.setType( User.TYPE_ADMIN );
            u.setState( User.STATE_NORMAL );
            u.setRg( "2099905016" );
            u.setDt_created( new java.sql.Date( System.currentTimeMillis() ) );
            
            UserManager.getInstance().addUser( u );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
