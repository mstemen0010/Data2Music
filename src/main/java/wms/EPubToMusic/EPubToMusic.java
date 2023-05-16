/*

* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.siegmann.epublib.domain.Book;
import wms.code2music.MusicScale;
import wms.code2music.MusicalPhrase;
import wms.code2music.Note;
import wms.passwdcracktomusic.MidiFile;

/**
 *
 * @author 173510
 */
public class EPubToMusic {

    static MusicScale scale = null;
    // FileInputStream fs = null;
    private final String libraryPathName = "C:\\dev\\\\ebooks\\";
    private final String bookName = "TheKingInYellow.epub";
    private Path currentPath;
    private File ebookFile;
    private int maxPages = 0;
    private int pageNumber = 1;
    private Librarian myLibrarian = null;

    public EPubToMusic(String filePath) {

        scale = new MusicScale();
        scale.add(new Note("C", 1));
        scale.add(new Note("Eb", 1));
        scale.add(new Note("F", 1));
        scale.add(new Note("Gb", 1));
        scale.add(new Note("G", 1));
        scale.add(new Note("Bb", 1));
        scale.add(new Note("C", 1));
        InputStream epubStream = null;
        ebookFile = new File(libraryPathName + bookName);
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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String pathName = "C:\\dev\\ebooks\\";
        String bookName = "TheKingInYellow.epub";

        MusicalPhrase myPhrase = new MusicalPhrase();
        myPhrase.printStack();
        System.out.println("Musical Phrase contains: " + myPhrase.size() + " notes");
        MidiFile newMidiFile = new MidiFile();
        Iterator<Note> notes = myPhrase.iterator();
        Book bookForMusic = null;

        EPubToMusic myEpub = new EPubToMusic(pathName + bookName);
        bookForMusic = myEpub.getLibrarian().getBook(new File(pathName + bookName));
        Librarian libr = myEpub.getLibrarian();
        VBook vbookForMusic = new VBook(libr, libr.getPageStack());
        // List<Resource> resources = bookForMusic.getContents();
        System.out.println("test");
    }

}
