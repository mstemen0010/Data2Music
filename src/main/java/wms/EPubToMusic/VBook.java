/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 173510
 */
public class VBook {
    
    private PageStack ps = null;
    private Librarian libr = null; 
    static private Map<String, Long> freqMap = new HashMap<>();
    
    public VBook( Librarian libr, PageStack ps  ) {
        this.ps = ps;
        this.libr = libr;
    }
    
}
