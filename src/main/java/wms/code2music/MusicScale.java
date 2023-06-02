package wms.code2music;


import wms.passwdcracktomusic.PasswdCrackToMusic;

import java.util.Random;
import java.util.Stack;

import static wms.passwdcracktomusic.PasswdCrackToMusic.scale;

public class MusicScale<Note> extends Stack<Note> {
    int octaveOffSet = 1;
    public Note getRandomNoteInScale()
    {
        Note noteToRet = null;

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(this.size());
        noteToRet = this.get(randomInt);

        return noteToRet;
    }

    String charRange = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    //  D, E, F, G, A, B♭, and C. dm scale
    // C - Eb - F - Gb - G - Bb - C "blues" scale
    public String toNote(char charToConvert) {
        String retVal = "#";

        //  hexTonicScale.add(new Note("C", 1));

        PasswdCrackToMusic myCrack = new PasswdCrackToMusic();

        // figure out the note based on the scale and the octave based on how
        // many times it "loops" though the scale to get the relative note
        int charPos = charRange.indexOf(charToConvert);
        int relativeNotePos = charPos % scale.size();
        int relativeOctave = charPos / scale.size();
        if (relativeNotePos >= 7) {
            relativeNotePos = 0;
        }
        // System.out.println("Relative pos is: " + relativeNotePos );
        // System.out.println("Mod value is: " + relativeNotePos);
        wms.code2music.Note noteToSend = scale.elementAt(relativeNotePos);
        noteToSend.setOctave(relativeOctave + octaveOffSet );
        retVal = noteToSend.getValue() + ":" + noteToSend.getOctave() + "~";

        //  C E G♯and E♭ G B
        // we are using a hexatonic (8 note) scale... so mod by 8 first
        return retVal;
    }

}
