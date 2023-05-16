package wms.code2music;

public class MusicKey extends MusicScale {
    public Note cvtCharToNote( char charToCvt ) {

        Note noteToReturn = null;

        // how many notes are in this key?
        int numNotesInKey = this.size();
        // to determine what octave this note is in within this key

        // determine whether char is alphanumeric
        if( Character.isAlphabetic(charToCvt) ) {
            // get the ascii value
            int ascii = (int)  charToCvt;
            if( ascii > 97 && ascii < 122 ) {

            }
            int noteValue = (ascii - 97) + 1;



            switch( noteValue ) {

            }

        }

        return noteToReturn;

    }
}
