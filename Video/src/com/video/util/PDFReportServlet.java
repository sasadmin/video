/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

/**
 *
 * @author Galimberti
 */
public class PDFReportServlet
        extends HttpServlet
{
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        request.setCharacterEncoding( "UTF-8" );
        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();
        
        JSONObject json = (JSONObject)new JSONParser().parse( request.getParameter( "data" ) );
        
        int cols = Integer.parseInt( json.get( "cols" ).toString() );
        int items = Integer.parseInt( json.get( "items" ).toString() );

        out.println( "<html>" );
        out.println( "<head>" );
        out.println( "<title>Relatório</title>" );
        out.println( "</head>" );
        out.println( "<body onload=\"window.print();\">" );
        out.println( "<table width=\"100%\">" );
        
        for ( int i = 1; i <= cols; i++ )
        {
            out.println( "<th style=\"text-align: left\">" );
            out.println( json.get( "header." + i ) );
            out.println( "</th>" );
        }
        
        for ( int i = 1; i <= items; i++ )
        {
            String color = i % 2 == 1 ? "rgb(245,245,245)" : "white";
            
            out.println( "<tr style=\"background-color: " + color + "\">" );
            
            for ( int j = 1; j <= cols; j++ )
            {
                out.println( "<td>" );
                out.println( json.get( "col." + i + "." + j ) );
                out.println( "</td>" );
            }
            
            out.println( "</tr>" );
        }
        
        out.println( "</table>" );
        out.println( "</body>" );
        out.println( "</html>" );
    }
}
