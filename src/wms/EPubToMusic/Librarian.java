/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

/**
 *
 * @author matthew.stemen
 */
public class Librarian {

    private static HashMap<String, ZipEntry> zipEntryMap = new HashMap<>();
    private static HashMap<Integer, String> pagesEntryMap = new HashMap<>();
    private ZipFile classZipFile = null;
    private static File currentEpub = null;
    private static ArrayList<BufferedImage> pages = new ArrayList<>();
    private static ArrayList<String> pagesAsText = new ArrayList<>();

    private static final int BUFFER_SIZE = 8192;

    private static Librarian librarian = null;
    private static PageStack pageStack = null;

    private Librarian() {
        // singleton
        pageStack = new PageStack(this);

    }

    public static Librarian getLibrarian() {
        if (librarian == null) {
            librarian = new Librarian();
        }

        return librarian;
    }

    public static Librarian getImageLibrarian(File epubFile) throws IOException {
        if (librarian == null) {
            librarian = new Librarian();
            librarian.setEpub(epubFile);
            clearPageList();
            librarian.buildImagePages(epubFile);
        }
        return librarian;
    }

    public static Librarian getWordLibrarian(File epubFile) throws IOException {
        if (librarian == null) {
            librarian = new Librarian();
            librarian.setEpub(epubFile);
            clearPageList();
            librarian.buildTextPages(epubFile);
        }
        return librarian;
    }

    public static void setEpub(File epub) {
        Librarian.currentEpub = epub;
    }

    protected static void clearPageList() {
        if (pages != null) {
            pages.clear();
            zipEntryMap.clear();
        }
        if (pageStack != null) {
            pageStack.clear();
        }
    }

    public List<BufferedImage> getPages() {
        return Librarian.pages;
    }

    public List<String> getPagesAsText() {
        return Librarian.pagesAsText;
    }
    
    public 

    Book getBook(File epubBookPath) {
        Book bookToGet = null;
        EpubReader ePubReader = new EpubReader();
        try {
            FileInputStream fis = new FileInputStream(epubBookPath.getPath());
            this.classZipFile = new ZipFile(epubBookPath.getPath());
            ZipInputStream zis = new ZipInputStream(fis);
           // bookToGet = ePubReader.readEpub(zis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookToGet;
    }

    private String getClassName(String entry) {
        String retValue = null;
        if (entry != null) {
            String[] tokens = entry.split("/");
            // we want the last token
            retValue = tokens[tokens.length - 1];

        }
        return retValue;
    }

    private void buildImagePages(File epubFILE) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(epubFILE.getPath());
        ZipInputStream zis = new ZipInputStream(fis);
        this.classZipFile = new ZipFile(epubFILE.getPath());
        ZipEntry entry;

        clearPageList();
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().toLowerCase().contains("jpg") || entry.getName().toLowerCase().contains("png") || entry.getName().toLowerCase().contains("jpeg") || entry.getName().toLowerCase().contains("jpeg")) {
                System.out.println("Got image: " + entry.getName());

            } else if (!entry.getName().contains("class")) {
                continue;
            }

            StringBuilder sb = new StringBuilder("");
            String name = this.getClassName(entry.getName());
            // sb.append( className ).append(",").append(entry.getSize());            
            zipEntryMap.put(name, entry);
            BufferedImage nbi = explodeImage(name, entry);
            // System.out.println(sb.toString());
            if (nbi != null) {
                pages.add(nbi);
            }
            // consume all the data from this entry
            while (zis.available() > 0) {
                zis.read();
            }
            // I could close the entry, but getNextEntry does it automatically
            // zis.closeEntry()           
        }

    }

    private void buildTextPages(File epubFILE) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(epubFILE.getPath());
        ZipInputStream zis = new ZipInputStream(fis);
        this.classZipFile = new ZipFile(epubFILE.getPath());
        ZipEntry entry;

        clearPageList();
        int pageCounter = 0;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().toLowerCase().contains(".html")) {
                System.out.println("Got html/chapter: " + entry.getName());
                String t = entry.toString();
                //  pagesEntryMap.put(new Integer(pageCounter), entry.toString());

                StringBuilder sb = new StringBuilder("");
                String name = this.getClassName(entry.getName());
                // sb.append( className ).append(",").append(entry.getSize());            
                // zipEntryMap.put(name, entry);
                String nbi = explodeHtml(name, entry);
                // System.out.println(sb.toString());
                if (nbi != null) {
                    ParaStack ps = new ParaStack(nbi);
                    
                    // pagesEntryMap.put(new Integer(pageCounter), nbi);
                    if( ps != null )
                        pageStack.put(pageCounter, ps);
                }
                // consume all the data from this entry
                while (zis.available() > 0) {
                    zis.read();
                }
                pageCounter++;
            }

            // I could close the entry, but getNextEntry does it automatically
            // zis.closeEntry()  
        }

    }

    private ZipEntry getEntry(String key) {
        ZipEntry foundEntry = null;
        if (this.zipEntryMap != null) {
            foundEntry = this.zipEntryMap.get(key);
        }

        return foundEntry;
    }

    public PageStack getPageStack() {
        return pageStack;
    }
    protected BufferedImage explodeImage(String className, ZipEntry entry) {
        BufferedImage explodedImage = null;

        try {
            byte[] data = new byte[300000];
            int byteRead;

            BufferedOutputStream bout = null;
            int fLen = (int) entry.getSize();
            InputStream in = classZipFile.getInputStream(entry);
            byteRead = 0;
            data = new byte[fLen];
            // now either write an image or a class...
            boolean isImage = (entry.getName().toLowerCase().contains("jpg") || entry.getName().toLowerCase().contains("png"));
            if (isImage) {
                explodedImage = ImageIO.read(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return explodedImage;
    }

    protected String explodeHtml(String className, ZipEntry entry) {
        String explodedHtml = null;

        byte[] data = new byte[300000];
        int byteRead;

        BufferedOutputStream bout = null;
        int fLen = (int) entry.getSize();
        try {
            InputStream in = classZipFile.getInputStream(entry);
            explodedHtml = convert(in, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return explodedHtml;
    }

    private String convert(InputStream inputStream, Charset charset) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

//    static void updateFreq(ParaStack paraStk) {
//
//        Scanner sc = new Scanner
//        // Creates an empty TreeMap 
//        TreeMap<String, Integer> tmap
//                = new TreeMap<String, Integer>();
//
//        // Traverse through the given array 
//        for (int i = 0; i < paraStk.size(); i++) {
//            Integer c = tmap.get(arr[i]);
//
//            // If this is first occurrence of element 
//            if (tmap.get(arr[i]) == null) {
//                tmap.put(arr[i], 1);
//            } // If elements already exists in hash map 
//            else {
//                tmap.put(arr[i], ++c);
//            }
//        }
//
//        // Print result 
//        for (Map.Entry m : tmap.entrySet()) {
//            System.out.println("Frequency of " + m.getKey()
//                    + " is " + m.getValue());
//        }
//    }
}
