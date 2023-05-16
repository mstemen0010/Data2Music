/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private final Stack<String> targetStack = new Stack<>();
    
    private final int targetValue = 8; // good values: 5,7,9, 10
    
    private final Librarian myLibrarian;

    public ParaStack(String stringToParse, Librarian lib ) {
        myLibrarian = lib;
        build(stringToParse);

    }

    private void build(String strToUse) {
        // Element content = doc.getElementById("someid");
        Document doc = Jsoup.parse(strToUse);
        Elements p = doc.getElementsByTag("p");
        System.out.println("Common words stack size is: " +  myLibrarian.getCommonWords().getCommonWordsSize());
                
        System.out.println("Target value is: " + targetValue );
        for (Element x : p) {
            // System.out.println(x.text());
            paraStack.push(x.text());
            updateWordStack(x.text());
        }
         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         System.out.println("**************************************************************************************************************************");
         System.out.println("##########################################################################################################################");
         System.out.println("##########################################################################################################################");
        for (String s : Arrays.asList("Common Words are: " + this.myLibrarian.getCommonWords().showCommonWords(), "##########################################################################################################################", "##########################################################################################################################", "Resulting set is: " + this.targetStack)) {
            System.out.println(s);
        }
        // System.out.println(wordsByCount);
        Stack<String> cutDownList = this.candidateStackFromTargetStack();
         System.out.println("Candidate stack is: " + cutDownList);
         int u = myLibrarian.getCommonWords().compareToStack(cutDownList);
         int d = targetStack.size();
         System.out.println("Trimed down candidate list is: " + this.myLibrarian.getCommonWords().getDiffStack(cutDownList)); 
         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         System.out.println("Size diff is: " + (d - u) );
         System.out.println("**************************************************************************************************************************");
         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    public String getWords() {
        return String.join(" ", wordSet);
    }

    public void updateWordStack(String words) {
        List<String> wordList;
        wordList = split(words);
        for( String s:wordList ) {
            if( ! isCaptalized(s))
                continue;
            if (myLibrarian.isCommonWord(s.toLowerCase(Locale.ENGLISH))) {
                continue;
            }
            boolean add = this.wordSet.add(s);
            if (!this.wordsByCount.containsKey(s)) {
                wordsByCount.put(s, 1);
            } else {
                int c = wordsByCount.get(s).intValue() + 1;
                wordsByCount.put(s, c);
                if (c == targetValue) {
                    String t = s + "=" + c;
                    this.targetStack.add(t);
                }
            }
        }
    }
    public boolean isCaptalized( String wordToTest ) {
        if(wordToTest.length() <= 1)
            return false;
        String upperTest = wordToTest.substring(0,1);
        String upperMask = upperTest.toUpperCase();
        return upperTest.equals(upperMask);
    }
    
    public Iterator<String> getTargetStack() {
        return this.targetStack.iterator();
    }
    
    public Iterator<String> getWordList() {
        return this.wordSet.iterator();
    }

    public String[] toStringArray(int paraNumber ) {
        
         String target = this.paraStack.get(paraNumber);
         List<String> la = Stream.of(target.split(" ")).map (elem -> elem).collect(Collectors.toList());
         return StreamSupport.stream(la.spliterator(), false).toArray(String[]::new);
    }
    
    private static List<String> split(String str) {
        return Stream.of(str.split(" "))
            .map(elem -> elem)
            .collect(Collectors.toList());
    }
    
    private Stack<String> candidateStackFromTargetStack() {
        Stack<String> candStack = new Stack<>();
        for (String s: targetStack) {            
            String cs = s.split("=")[0];            
            candStack.add(cs.replaceAll("\"", ""));
        }
        
        return candStack;
    }
    
   

}
