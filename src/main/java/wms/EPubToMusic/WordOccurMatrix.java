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
    
    WordOccurMatrix( int chapter, int page, int paragraph) {
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
        return wordOccurMatrix.size();
    }

    @Override
    public boolean isEmpty() {
        return wordOccurMatrix.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return wordOccurMatrix.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return wordOccurMatrix.containsValue(value);
    }

    @Override
    public Integer get(Object key) {
        return wordOccurMatrix.get(key);
    }

    @Override
    public Integer put(String key, Integer value) {
        return wordOccurMatrix.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return wordOccurMatrix.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> m) {
        wordOccurMatrix.putAll(m);
    }

    @Override
    public void clear() {
        wordOccurMatrix.clear();

    }

    @Override
    public Set<String> keySet() {
        return wordOccurMatrix.keySet();
    }

    @Override
    public Collection<Integer> values() {
        return wordOccurMatrix.values();
    }

    @Override
    public Set<Entry<String, Integer>> entrySet() {
        return wordOccurMatrix.entrySet();
    }
}
