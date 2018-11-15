/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author 173510
 */
public class ParaStack {

    // use a scanner to build pararaph stack 
    final String paraDelim = "</P>";
    private final Stack<String> paraStack = new Stack<>();
    private final Set<String> wordSet = new HashSet<>();
    private final TreeMap<String, Integer> wordsByCount = new TreeMap<>();

    public ParaStack(String stringToParse) {
        build(stringToParse);

    }

    private void build(String strToUse) {
        // Element content = doc.getElementById("someid");
        Document doc = Jsoup.parse(strToUse);
        Elements p = doc.getElementsByTag("p");

        for (Element x : p) {
            paraStack.push(x.text());
            updateWordStack(x.text());
        }
    }
    
    public String getWords() {
        return String.join(" ", wordSet);
    }

    public void updateWordStack(String words) {
        List<String> wordList = split(words);
        for( String s:wordList ) {
            this.wordSet.add(s);
            if( ! this.wordsByCount.containsKey(s)) {
                wordsByCount.put(s, new Integer(1));
            }
            else { 
                int c = wordsByCount.get(s).intValue();
                wordsByCount.put( s, ++c);
            }
        }
        System.out.println(wordsByCount);
    }
    
    public Iterator<String> getWordList() {
        return this.wordSet.iterator();
    }

    public String[] toStringArray(int paraNumber ) {
        
         String target = this.paraStack.get(paraNumber);
         List<String> la = Stream.of(target.split(" ")).map (elem -> new String(elem)).collect(Collectors.toList());         
         return StreamSupport.stream(la.spliterator(), false).toArray(String[]::new);
    }
    
    private static List<String> split(String str) {
        return Stream.of(str.split(" "))
            .map(elem -> new String(elem))
            .collect(Collectors.toList());
    }
    
   

}
