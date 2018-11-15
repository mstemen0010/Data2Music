package wms.code2music;


import java.util.Random;
import java.util.Stack;

public class MusicScale<Note> extends Stack<Note> {
    public Note getRandomNoteInScale()
    {
        Note noteToRet = null;

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(this.size());
        noteToRet = this.get(randomInt);

        return noteToRet;
    }

}
