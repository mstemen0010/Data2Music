/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 173510
 */
public class WordStack extends Stack<String> {

    enum WorkStackType {
        Undefined,
        Common, 
        Male,
        Female,
        Place,
        outOfRange;
    }

    Stack stack = null;
  //   final static String[] commonWords = {"the", "I", "and", "a", "of", "to", "that", "was", "had", "in", "my", "her", "she", "with", "but", "for", "me", "as", "on", "you", "it", "at", "not", "could", "from", "into", "which", "The", "have", "all", "an", "Then", "up", "is", "or", "She", "so", "like", "be", "no", "before", "looked", "by", "what", "would", "when", "did", "knew", "were", "about", "me", "one", "very", "through", "how", "we", "upon", "out"};
    final static Stack<String> commonWordsStack = new Stack<String>();

    public WordStack(WorkStackType type, File wordFile) {
        addCommonWords(wordFile);
    }

    public Stack<String> showCommonWords() {
        return this;
    }

    protected void addCommonWords(File wordFile) {
        stack = new Stack();
       
       //File wordFile = new File("stack.txt");

        if (!wordFile.exists()) {
            try {
                wordFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(WordStack.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("File is done");
        }
        FileReader r = null;
        try {
            r = new FileReader(wordFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader reader = new BufferedReader(r);

        String line = null;

        try {
            // Skip headline
            // reader.readLine();
            while ((line = reader.readLine()) != null) {
                this.push(line);
            }
          

        } catch (IOException ex) {
            Logger.getLogger(WordStack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getCommonWordsSize() {
        return this.size();
    }

    public static int compareToStack(Stack<String> comparStack) {
        int count = comparStack.size();
        Stack<String> test = comparStack;
        test.removeAll(commonWordsStack);
        return count - test.size();
    }

    public static Stack<String> getDiffStack(Stack<String> candidateStack) {
        candidateStack.removeAll(commonWordsStack);
        return candidateStack;
    }

}
