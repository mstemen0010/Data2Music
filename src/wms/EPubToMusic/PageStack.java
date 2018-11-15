/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import java.util.stream.Stream;
import javax.swing.text.Element;

/**
 *
 * @author 173510
 */
public class PageStack implements Map<Integer, ParaStack> {

    private HashMap<Integer, ParaStack> map = new HashMap<>();
    private Map<String, Long> freq = null;
    
    public PageStack( Librarian libr ) {
        
    }
    
    @Override
    public int size() {
       return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParaStack put(Integer key, ParaStack value) {
        updateWordTree(value);
        return this.map.put(key, value);
    }

    @Override
    public ParaStack get(Object key) {
        return this.map.get(key);
    }

    @Override
    public ParaStack remove(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends ParaStack> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<ParaStack> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Entry<Integer, ParaStack>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void updateWordTree(ParaStack paraStack ) {
        String[] words = null;
        List<String> list = new ArrayList<>();
        paraStack.getWordList().forEachRemaining(list::add);
        words = list.toArray(new String[0]);
        Map<String, Long> counts;
        counts = list.stream().collect(groupingBy(Function.identity(), counting()));
        System.out.println(counts);
        
    }
    
}
