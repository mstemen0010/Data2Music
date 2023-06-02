/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import nl.siegmann.epublib.domain.Book;


/**
 *
 * @author stemenm
 */
public class JBookKey {
    
    enum JBookKeyType
    {
        JBookId,
        JBookAuthor,
        JBookSubject,
        JBookTitle;
    }
    
    String [] keyValues = new String[ JBookKeyType.values().length ];
    
    public JBookKey( Book bookToUse )
    {
        String id;
        String title;
        String author;
        String Subject;
        
        if( bookToUse != null )
        {
            title = bookToUse.getTitle();
            if ( title != null && title.length() > 0 )
            {
                keyValues[JBookKeyType.JBookTitle.ordinal()] = title;
            }
        }
    }
    
    public boolean hasTitle( )
    {
        return ( keyValues[JBookKeyType.JBookTitle.ordinal()] != null && ! keyValues[JBookKeyType.JBookTitle.ordinal()].isEmpty() );
    }
    
}
