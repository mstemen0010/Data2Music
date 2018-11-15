/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import nl.siegmann.epublib.browsersupport.Navigator;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.TableOfContents;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author stemenm
 */
public class JBook {
    

    JBookKey myKey;
    Book myBook;
    Navigator myNavigator = null;
    HTMLDocumentFactory docFac = null;
    
    JBook(Book readEpub) {
        myBook = readEpub;
        myKey = new JBookKey(myBook);
        myNavigator = new Navigator(readEpub);
    }
   
    public Book getBook()
    {
        return this.myBook;
    }
    
    public JBookKey getJBookKey()
    {                    
        return myKey;
    }

    public String getTitle()
    {
        return this.myBook.getTitle();
    }
    

    public List<String> getDescriptions()
    {
        return this.myBook.getMetadata().getDescriptions();
    }
    
    public List<Author> getAuthors()
    {
        return this.myBook.getMetadata().getAuthors();
    }
    
    public Resource getCover()
    {
        return this.myBook.getCoverImage();
    }
    
    public TableOfContents getTOC()
    {
        return this.myBook.getTableOfContents();
    }
    public String getChapter( int index ) throws IOException
    {
        Spine spine =  this.myBook.getSpine();
        Resource res = spine.getResource(index);
        
        String chapterAsStr = null;
        StringBuilder sb = new StringBuilder();
        Reader chapterReader = res.getReader();
        String pageContent = IOUtils.toString(res.getReader());
        if( chapterReader != null )
        {
//            while( chapterReader.ready())
//            {
//                sb.append(chapterReader.read());
//            }
        }
        sb.append(pageContent);
        return sb.toString();
    }
}
