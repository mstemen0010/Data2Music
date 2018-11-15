/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import wms.code2music.MusicalPhrase;
import wms.code2music.Note;
import wms.passwdcracktomusic.MidiFile;

/**
 *
 * @author 173510
 */
public class EPubToMusic {

    // FileInputStream fs = null;
    private final String libraryPathName = "C:\\alt_dev\\\\ebooks\\";
    private final String bookName = "TheKingInYellow.epub";
    private Path currentPath;
    private File ebookFile;
    private int maxPages = 0;
    private int pageNumber = 1;
    private Librarian myLibrarian = null;

    public EPubToMusic(String filePath) {

        InputStream epubStream = null;
        ebookFile = new File(libraryPathName + bookName );     
        PageStack ps = null;
       
        
        try {
            // fs = new FileInputStream(ebookFile);            
            epubStream = new FileInputStream(ebookFile);            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EPubToMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (epubStream != null) {
            // Book book = (new EpubReader()).readEpub(new FileInputStream(selectedFile));\
            
            try {
                myLibrarian = Librarian.getWordLibrarian(ebookFile);
            } catch (IOException ex) {
                Logger.getLogger(EPubToMusic.class.getName()).log(Level.SEVERE, null, ex);
            }
            // List<String> pages = myLibrarian.getPagesAsText();            
        }
    }
    
    Librarian getLibrarian() {
        return this.myLibrarian;
    }
    
    public static void main(String[] args) {
           
        String pathName = "C:\\alt_dev\\\\ebooks\\";
        String bookName = "TheKingInYellow.epub";
        
        MusicalPhrase myPhrase = new MusicalPhrase(); 
        myPhrase.printStack();
        System.out.println("Musical Phrase contains: " + myPhrase.size() + " notes");
        MidiFile newMidiFile = new MidiFile();
        Iterator<Note> notes = myPhrase.iterator();
        Book bookForMusic = null;
        
        EPubToMusic myEpub = new EPubToMusic(pathName + bookName );
        bookForMusic = myEpub.getLibrarian().getBook( new File(pathName + bookName) );
        Librarian libr = myEpub.getLibrarian();       
        VBook vbookForMusic = new VBook( libr, libr.getPageStack());
        // List<Resource> resources = bookForMusic.getContents();
        System.out.println("test");
    }
        
    
}
