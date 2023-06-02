/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.EPubToMusic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author 173510
 */
public class CommonWords {
	final static String[] commonWords = {"the","I","and","a","of","to","that","was","had","in","my","her","she","with","but","for","me","as","on","you","it","at","not","could","from","into","which","The","have","all","an","Then","up","is","or","She","so","like","be","no","before","looked","by","what","would","when","did","knew","were","about","me","one","very","through","how","we","upon","out" };
        final static Stack<String> commonWordsStack = new Stack<String>();

        public CommonWords() {
            CommonWords.addCommonWords();
        }

        public static Stack<String> showCommonWords() {
            CommonWords.addCommonWords();
            return commonWordsStack;
        }

        protected static void addCommonWords () {
            commonWordsStack.addAll(new ArrayList<>(Arrays.asList(commonWords)));
        }

        public static int getCommonWordsSize() {
            return commonWords.length;
        }

        public static int compareToStack( Stack<String> comparStack ) {
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
