package wms.EPubToMusic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordOccurMatrix implements Map<String, Integer> { PageStack assocPageStack;
    int chapter;
    int page;
    int paragraph;
    
    HashMap<String, Integer> wordOccurMatrix;
    
    public WordOccurMatrix( int chapter, int page, int paragraph) {
        this.chapter = chapter;
        this.page = page;
        this.paragraph = paragraph;
        
        this.wordOccurMatrix = new HashMap<>();
    }

    public void addWordToMatrix( String wordToAdd ) {
        if ( wordOccurMatrix.containsKey(wordToAdd)  ) {
            int currentCount = wordOccurMatrix.get(wordToAdd);
            wordOccurMatrix.put(wordToAdd, currentCount + 1);
        }
        else {
            wordOccurMatrix.put(wordToAdd, 1);
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Integer get(Object key) {
        return null;
    }

    @Override
    public Integer put(String key, Integer value) {
        return null;
    }

    @Override
    public Integer remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Integer> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Integer>> entrySet() {
        return null;
    }
}
